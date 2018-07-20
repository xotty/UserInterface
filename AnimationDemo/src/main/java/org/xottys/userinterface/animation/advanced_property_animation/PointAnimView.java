/**
 * 自定义小球正弦移动视图，含动画演示
 * 1)ValueAnimator的用法
 *   i)用动画过程的属性值或状态(可以是多个)生成ValueAnimator对象
 *  ii)定义动画属性，如：repeatCount、repeatMode、interpolator等
 * iii)在AnimatorUpdateListener中实现动画，主要是通过animation.getAnimatedValue()改变动画对象的相关属性
 *  iv)启动动画或将其放入AnimatorSet中后启动
 * 2)Evalutaor--PointSinEvaluator
 *   用来呈现属性变化过程的，系统提供IntEvaluator、FloatEvaluator、ArgbEvaluator三个，可以自定义。
 *   animator.setEvaluator(xxx)或offObject()构造时通过参数设定
 * 3)自定义Interpolator--MyInterpolator
 *   用来呈现属性变化过程的速度，系统提供8种，可以自定义.
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

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import org.xottys.userinterface.animation.R;
public class PointAnimView extends View {

    public static final float RADIUS = 20f;

    private Point currentPoint;

    private Paint mPaint;
    private Paint linePaint;

    private AnimatorSet animSet;
    private TimeInterpolator interpolatorType;

    /**
     * 实现关于color 的属性动画
     */
    private int color;
    private float radius = RADIUS;

    public PointAnimView(Context context) {
        super(context);
        init();
    }


    public PointAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(this.color);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    //定义线宽和颜色
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.TRANSPARENT);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(5);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec,400);
    }

    //画视图
    @Override
    protected void onDraw(Canvas canvas) {
        //画小球
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
        } else {
            drawCircle(canvas);
        }
        //画坐标轴
        drawLine(canvas);
    }

    //画坐标轴
    private void drawLine(Canvas canvas) {
        canvas.drawLine(10, getY()+150, getWidth(), getY()+150, linePaint);
        canvas.drawLine(10, getY(), 10, getY()+300, linePaint);
        canvas.drawPoint(currentPoint.getX(), currentPoint.getY(), linePaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initAnimation();
    }

    public void startAnimation() {
        animSet.start();
    }

    public void stopAnimation() {
        if (animSet != null) {
            animSet.cancel();
            this.clearAnimation();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void pauseAnimation() {
        if (animSet != null) {
            animSet.pause();
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void resumeAnimation() {
        if (animSet != null) {
            animSet.resume();
        }
    }
    //用ValueAnimator和ObjectAnimator定义了三种动画，并将其组合进入一个集合animSet
    private void initAnimation() {
        //用ValueAnimator定义第一组动画：改变小球位置
        //定义动画起始状态
        Point startP = new Point(RADIUS, RADIUS);
        //定义动画结束状态
        Point endP = new Point(getWidth() - RADIUS, getY()+300 - RADIUS);
        //定义动画及其属性，使用了自定义Evaluator
        final ValueAnimator animPosition = ValueAnimator.ofObject(new PointSinEvaluator(), startP, endP);
        animPosition.setRepeatCount(-1);
        animPosition.setRepeatMode(ValueAnimator.REVERSE);
        //定义动画过程：移动小球位置
        animPosition.addUpdateListener(animation->{
          currentPoint = (Point) animation.getAnimatedValue();
          postInvalidate();
        });

        //用ObjectAnimator定义第二组动画：改变小球颜色
        Resources mResources=getResources();
        int green = mResources.getColor(R.color.cpb_green_dark,null);
        int primary = mResources.getColor(R.color.colorPrimary,null);
        int red = mResources.getColor(R.color.cpb_red_dark,null);
        int orange = mResources.getColor(R.color.orange,null);
        int accent = mResources.getColor(R.color.colorAccent,null);

        //ArgbEvaluator的有一种用法
        ObjectAnimator animColor = ObjectAnimator.ofObject(this, "color", new ArgbEvaluator(), green, primary, red, orange, accent);

        animColor.setRepeatCount(-1);
        animColor.setRepeatMode(ValueAnimator.REVERSE);

        //用ValueAnimator定义第三组动画：改变小球大小
        final ValueAnimator animScale = ValueAnimator.ofFloat(20f, 80f, 60f, 10f, 35f,55f,10f);
        animScale.setRepeatCount(-1);
        animScale.setRepeatMode(ValueAnimator.REVERSE);
        //定义动画过程：改变小球半径
        animScale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setRadius((float) animation.getAnimatedValue());
            }
        });

        //将上述三组动画组合在一起同时播放
        animSet = new AnimatorSet();
        animSet.play(animPosition).with(animColor).with(animScale);
        animSet.setDuration(5000);
        animSet.setInterpolator(interpolatorType);
    }

    //在当前位置画小球
    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, getRadius(), mPaint);
    }

    public void setInterpolatorType(int type ) {
        switch (type) {
            case 1:
                interpolatorType = new BounceInterpolator();
                break;
            case 2:
                interpolatorType = new AccelerateDecelerateInterpolator();
                break;
            case 3:
                interpolatorType = new DecelerateInterpolator();
                break;
            case 4:
                interpolatorType = new AnticipateInterpolator();
                break;
            case 5:
                interpolatorType = new LinearInterpolator();
                break;
            case 6:
                interpolatorType=new LinearOutSlowInInterpolator();
                break;
            case 7:
                interpolatorType = new OvershootInterpolator();
            case 8:
                interpolatorType = new MyInterpolator();
            default:
                interpolatorType = new LinearInterpolator();
                break;
        }
    }

    //自定义插值器
    private class MyInterpolator implements TimeInterpolator {
        //因子数值越小振动频率越高
        private float factor;
        //因子初值
        private MyInterpolator() {
            this.factor = 0.15f;
        }
        //因子变化算法
        @Override
        public float getInterpolation(float input) {
            return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
        }
    }
}
