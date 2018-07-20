/**
 * 自定义成功示意图，含动画演示，要点：
 * 1）集合动画播放顺序A->B-C：mAnimatorSet.play(B).before(C).after(A）
 *    其中同为with、before或after的动画会同时播放
 * 2）延迟播放动画： mAnimatorSet.setStartDelay(2000);
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:PhysicsMainActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.advanced_property_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;


public class AlipaySuccessView extends View {

    private String TAG = "AlipaySuccessView";
    private static final float PADDING = 20;
    private static final int DEFAULT_RADIUS = 150;


    private Paint mCirclePanit;
    private Paint mLinePaint;

    private float mStrokeWidth = 10;
    private float mCenterX, mCenterY;
    private float mRadius = 150;
    private final RectF mRectF = new RectF();
    private int mDegree;
    private Float mLeftValue = 0f;
    private Float mRightValue = 0f;
    private AnimatorSet mAnimatorSet = new AnimatorSet();
    private ValueAnimator mCircleAnim;
    private ValueAnimator mLineLeftAnimator;
    private ValueAnimator mLineRightAnimator;
    private AnimatorSet set;
    private boolean stopFlag;


    public AlipaySuccessView(Context context) {
        this(context, null);
        initPaint();
    }

    public AlipaySuccessView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlipaySuccessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

    }

    private void initPaint() {
        mCirclePanit = new Paint();
        mCirclePanit.setAntiAlias(true);
        mCirclePanit.setStrokeJoin(Paint.Join.ROUND);
        mCirclePanit.setStrokeWidth(mStrokeWidth);
        mCirclePanit.setColor(Color.BLUE);
        mCirclePanit.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mLinePaint.setStrokeWidth(mStrokeWidth);
        mLinePaint.setColor(Color.BLUE);
        mLinePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.left = mCenterX - mRadius;
        mRectF.top = mCenterY - mRadius;
        mRectF.right = mCenterX + mRadius;
        mRectF.bottom = mCenterY + mRadius;
        //画圆，用角度mDegree做主要参数
        canvas.drawArc(mRectF, 0, mDegree, false, mCirclePanit);
        //画线左，用偏移量mLeftValue做主要参数
        canvas.drawLine(mCenterX - mRadius / 2, mCenterY,
                mCenterX - mRadius / 2 + mLeftValue, mCenterY + mLeftValue, mLinePaint);
        //画线右，用x偏移量mRightValue做主要参数
        canvas.drawLine(mCenterX, mCenterY + mRadius / 2,
                mCenterX + mRightValue, mCenterY + mRadius / 2 - (4f / 2f) * mRightValue, mLinePaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        reMeasure();
        setMeasuredDimension(500,500);
    }

    private void reMeasure() {
        mCenterX = 250;
        mCenterY = 250;
    }

    public void loadCircle(int mRadius) {
        mRadius = mRadius <= 0 ? DEFAULT_RADIUS : mRadius;
        this.mRadius = mRadius - PADDING;
        if (null != mAnimatorSet && mAnimatorSet.isRunning()) {
            return;
        }
        reset();
        reMeasure();
        //圆动画，角度0～360
        mCircleAnim = ValueAnimator.ofInt(0, 360);
        mCircleAnim.setDuration(700);
        mCircleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDegree = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        //左线动画，x偏移量：0～半径的1/2
        mLineLeftAnimator = ValueAnimator.ofFloat(0, this.mRadius / 2f);
        mLineLeftAnimator.setDuration(350);
        mLineLeftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mLeftValue = (Float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        //右线动画，x偏移量：0～半径的1/2
        mLineRightAnimator = ValueAnimator.ofFloat(0, this.mRadius / 2f);
        mLineRightAnimator.setDuration(350);
        mLineRightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRightValue = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });

        //将上述圆动画和左右线动画打包，并先后依次播放：圆->左线->右线
        mAnimatorSet.play(mLineLeftAnimator).before(mLineRightAnimator).after(mCircleAnim);
        /*等价播放顺序
        mAnimatorSet.play(mCircleAnim).before(mLineLeftAnimator);
        mAnimatorSet.play(mLineRightAnimator).after(mLineLeftAnimator);*/

        //全部动画破房完成后继续播放新动画 successAnim()
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mEndListner != null) {
                    mEndListner.onCircleDone();
                    successAnim();
                }
            }
        });
        stopFlag=false;
        mAnimatorSet.start();
    }

    private void successAnim() {
        //首先是放大回缩动画
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 1.1f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 1.1f, 1.0f);
        set = new AnimatorSet();
        set.setDuration(3000);
        set.setInterpolator(new BounceInterpolator());
        set.playTogether(scaleXAnim, scaleYAnim);
        set.start();

        //在放大回缩动画播放完成后重新激活原动画，开始循环播放
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!stopFlag) {
                    //延迟2秒后播放
                    mAnimatorSet.setStartDelay(2000);
                    mAnimatorSet.start();
                }
            }
        });
    }


    public void stop() {
        stopFlag=true;
        if (null != mCircleAnim) {
            mCircleAnim.end();
        }
        if (null != mLineLeftAnimator) {
            mLineLeftAnimator.end();
        }
        if (null != mLineRightAnimator) {
            mLineRightAnimator.end();
        }
        clearAnimation();

    }
    public void stopAnimation() {
        stop();
        mAnimatorSet.cancel();
        set.cancel();


    }

    public void reset() {
        mDegree = 0;
        mLeftValue = 0f;
        mRightValue = 0f;
        mCirclePanit.setColor(Color.BLUE);
        mLinePaint.setColor(Color.BLUE);
    }


    private OnCircleFinishListener mEndListner;

    public void addCircleAnimatorEndListner(OnCircleFinishListener endListenr) {
        if (null == mEndListner) {
            this.mEndListner = endListenr;
        }
    }

    public interface OnCircleFinishListener {
        void onCircleDone();
    }

    public void setPaintColor(int color) {
        mCirclePanit.setColor(color);
        mLinePaint.setColor(color);
        invalidate();
    }
}
