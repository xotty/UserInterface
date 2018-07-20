/**
 * 自定义失败示意图，含动画演示
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
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.CycleInterpolator;


public class AlipayFailureView extends View {

    private String TAG = AlipayFailureView.class.getSimpleName();
    private static final float PADDING = 20;
    private static final int DEFAULT_RADIUS = 150;

    private Paint mCirclePanit;
    private Paint mLinePaint;
    private float mStrokeWidth = 10;
    private float factor = 0.8f;
    private float temp;
    private float mCenterX, mCenterY;
    private float mRadius = 250;
    private final RectF mRectF = new RectF();
    private int mDegree;
    private Float mLeftValue = 0f;
    private Float mRightValue = 0f;
    private AnimatorSet mAnimatorSet = new AnimatorSet();

    private ValueAnimator mCircleAnim;
    private ValueAnimator mLineLeftAnimator;
    private ValueAnimator mLineRightAnimator;

    private PathMeasure pathLeftMeasure;
    private PathMeasure pathRightMeasure;

    //左右线的终点坐标
    private float[] mLeftPos = new float[2];
    private float[] mRightPos = new float[2];

    public AlipayFailureView(Context context) {
        this(context, null);
    }

    public AlipayFailureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlipayFailureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCirclePanit = new Paint();
        mCirclePanit.setAntiAlias(true);
        mCirclePanit.setStrokeJoin(Paint.Join.ROUND);
        mCirclePanit.setStrokeWidth(mStrokeWidth);
        mCirclePanit.setColor(Color.CYAN);
        mCirclePanit.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeJoin(Paint.Join.ROUND);
        mLinePaint.setStrokeWidth(mStrokeWidth);
        mLinePaint.setColor(Color.CYAN);
        mLinePaint.setStyle(Paint.Style.STROKE);

        reset();
        reMeasure();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.left = mCenterX - mRadius;
        mRectF.top = mCenterY - mRadius;
        mRectF.right = mCenterX + mRadius;
        mRectF.bottom = mCenterY + mRadius;
        //画圆
        canvas.drawArc(mRectF, 0, mDegree, false, mCirclePanit);
        //画交叉线，条件是动画开始后
        if (mLeftPos[1] > (mCenterY - temp) && mRightPos[1] > (mCenterY - temp)) {
            canvas.drawLine(mCenterX - temp, mCenterY - temp, mLeftPos[0], mLeftPos[1], mLinePaint);
            canvas.drawLine(mCenterX + temp, mCenterY - temp, mRightPos[0], mRightPos[1], mLinePaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        reMeasure();
        setMeasuredDimension(500,500);
    }

    //画交叉线的Path，并将其装入pathLeftMeasure和pathRightMeasure
    private void reMeasure() {
        mCenterX = 250;
        mCenterY = 250;
        temp = mRadius / 2.0f * factor;
        //左线
        Path path = new Path();
        path.moveTo(mCenterX - temp, mCenterY - temp);
        path.lineTo(mCenterX + temp, mCenterY + temp);
        pathLeftMeasure = new PathMeasure(path, false);
        //右线
        path = new Path();
        path.moveTo(mCenterX + temp, mCenterY - temp);
        path.lineTo(mCenterX - temp, mCenterY + temp);
        pathRightMeasure = new PathMeasure(path, false);


    }

    public void startAnim(int mRadius) {
        mRadius = mRadius <= 0 ? DEFAULT_RADIUS : mRadius;
        this.mRadius = mRadius - PADDING;
        if (null != mAnimatorSet && mAnimatorSet.isRunning()) {
            return;
        }
        reset();
        reMeasure();
        //圆动画
        mCircleAnim = ValueAnimator.ofInt(0, 360);
        mCircleAnim.setDuration(700);
        mCircleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDegree = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });

        //左线动画
        mLineLeftAnimator = ValueAnimator.ofFloat(0, pathLeftMeasure.getLength());
        mLineLeftAnimator.setDuration(350);
        mLineLeftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //根据线长度mLeftValue获取线上点的坐标，放到mLeftPos中
                mLeftValue = (Float) valueAnimator.getAnimatedValue();
                pathLeftMeasure.getPosTan(mLeftValue, mLeftPos, null);
                invalidate();
            }
        });

        //右线动画
        mLineRightAnimator = ValueAnimator.ofFloat(0, pathRightMeasure.getLength());
        mLineRightAnimator.setDuration(350);
        mLineRightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //根据线长度mRightValue获取线上点的坐标，放到mRightPos中
                mRightValue = (Float) animation.getAnimatedValue();
                pathRightMeasure.getPosTan(mRightValue, mRightPos, null);
                invalidate();
            }
        });

        //将上述圆动画和左右线动画打包，并先后依次播放：圆->左线->右线
        mAnimatorSet.play(mCircleAnim).before(mLineLeftAnimator);
        mAnimatorSet.play(mLineRightAnimator).after(mLineLeftAnimator);
        //全部动画破房完成后继续播放新动画 failureAnim()
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                stop();
                if (mEndListner != null) {
                    mEndListner.onCircleDone();
                    failureAnim();
                }
            }
        });

        mAnimatorSet.start();
    }

    //左右晃动动画
    private void failureAnim() {
        float currentX = this.getTranslationX();
        ObjectAnimator tansXAnim = ObjectAnimator.ofFloat(this, "translationX",currentX+20);
        tansXAnim.setDuration(1000);
        //周期运动3次
        tansXAnim.setInterpolator(new CycleInterpolator(3));

        tansXAnim.start();
    }

    public void stop() {
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

    public void reset() {
        mDegree = 0;
        mLeftValue = 0f;
        mRightValue = 0f;
        pathLeftMeasure=null;
        pathRightMeasure = null;
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
