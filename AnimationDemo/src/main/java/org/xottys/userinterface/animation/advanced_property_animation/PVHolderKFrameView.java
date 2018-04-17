/**本例演示了PropertyValuesHolder和KeyFrame的用法
 * 一、PropertyValuesHolder
 * 1)构造PropertyValuesHolder对象，每一个是一个属性动画
 * 2)用静态方法ofPropertyValuesHolder()将上述动画组合在一起，构建ValueAnimator或ObjectAnimator
 * 3)设置属性并播放动画
 * 二、KeyFrame
 * 1)用Keyframe.ofInt、Keyframe.ofFloat或Keyframe.ofObject构造至少两个KeyFrame对象
 * 2)用PropertyValuesHolder.ofKeyframe生成PropertyValuesHolder对象
 * 三、xml方式
 * <objectAnimator>或<animator>
 *     <propertyValuesHolder>
 *         <keyframe/>
 *         <keyframe/>
 *     </propertyValuesHolder>
 * </objectAnimator>或</animator>
 *  AnimatorInflater.
 *用loadAnimator(mContext, R.animator.xml_animator)加载后生成ValueAnimator或ObjectAnimator对象
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:PhysicsAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.advanced_property_animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import java.util.ArrayList;

import org.xottys.userinterface.animation.R;

public class PVHolderKFrameView extends View implements ValueAnimator.AnimatorUpdateListener {
    private static final float BALL_SIZE = 100f;
    private static final int DURATION = 1500;

    public final ArrayList<ShapeHolder> balls = new ArrayList<>();
    Animator animation = null;

    public PVHolderKFrameView(Context context) {
        super(context);
        //添加12个球到balls数组：0～11
        addBall(50, 50);
        addBall(150, 50);
        addBall(250, 50);
        addBall(350, 50);

        addBall(300 + 350, 50);
        addBall(400 + 350, 50);
        addBall(500 + 350, 50);
        addBall(600 + 350, 50, Color.GREEN);
        addBall(700 + 350, 50);
        addBall(800 + 350, 50);
        addBall(900 + 350, 50);
        addBall(800 + 350, 50, Color.YELLOW);
    }

    private void createAnimation(Context appContext) {
        if (animation == null) {
            ShapeHolder ball;
            //简单ObjectAnimator动画
            ball = balls.get(0);
            ObjectAnimator yBouncer = ObjectAnimator.ofFloat(ball, "y",
                    ball.getY(), getHeight() - BALL_SIZE).setDuration(DURATION);
            yBouncer.setInterpolator(new BounceInterpolator());
            yBouncer.addUpdateListener(this);

            //PropertyValuesHolder代码生成动画，ObjectAnimator.ofPropertyValuesHolder将其组装
            ball = balls.get(1);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", ball.getY(),
                    getHeight() - BALL_SIZE);
            PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);
            ObjectAnimator yAlphaBouncer = ObjectAnimator.ofPropertyValuesHolder(ball,
                    pvhY, pvhAlpha).setDuration(DURATION / 2);
            yAlphaBouncer.setInterpolator(new AccelerateInterpolator());
            yAlphaBouncer.setRepeatCount(1);
            yAlphaBouncer.setRepeatMode(ValueAnimator.REVERSE);

            //PropertyValuesHolder代码生成动画，ObjectAnimator.ofPropertyValuesHolder将其组装
            ball = balls.get(2);
            PropertyValuesHolder pvhW = PropertyValuesHolder.ofFloat("width", ball.getWidth(),
                    ball.getWidth() * 2);
            PropertyValuesHolder pvhH = PropertyValuesHolder.ofFloat("height", ball.getHeight(),
                    ball.getHeight() * 2);
            PropertyValuesHolder pvTX = PropertyValuesHolder.ofFloat("x", ball.getX(),
                    ball.getX() - BALL_SIZE / 2f);
            PropertyValuesHolder pvTY = PropertyValuesHolder.ofFloat("y", ball.getY(),
                    ball.getY() - BALL_SIZE / 2f);
            ObjectAnimator whxyBouncer = ObjectAnimator.ofPropertyValuesHolder(ball, pvhW, pvhH,
                    pvTX, pvTY).setDuration(DURATION / 2);
            whxyBouncer.setRepeatCount(1);
            whxyBouncer.setRepeatMode(ValueAnimator.REVERSE);

            //用Keyframe构建PropertyValuesHolder
            ball = balls.get(3);
            pvhY = PropertyValuesHolder.ofFloat("y", ball.getY(), getHeight() - BALL_SIZE);
            //构建Keyframe，0f->0.5f->1f表示事件演进
            float ballX = ball.getX();
            Keyframe kf0 = Keyframe.ofFloat(0f, ballX);
            Keyframe kf1 = Keyframe.ofFloat(.5f, ballX + 100f);
            Keyframe kf2 = Keyframe.ofFloat(1f, ballX + 50f);
            //用Keyframe构建PropertyValuesHolder
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("x", kf0, kf1, kf2);
            //用PropertyValuesHolder构建ObjectAnimator
            ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(ball, pvhY,
                    pvhX).setDuration(DURATION / 2);
            yxBouncer.setRepeatCount(1);
            yxBouncer.setRepeatMode(ValueAnimator.REVERSE);

            //xml方式ObjectAnimator的使用,标签为<objectAnimator>
            final ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.object_animator);
            anim.addUpdateListener(this);
            anim.setTarget(balls.get(4));

            //xml方式ValueAnimator的使用,标签为<animator>
            final ValueAnimator fader = (ValueAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.animator);
            fader.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    balls.get(5).setAlpha((Float) animation.getAnimatedValue());
                }
            });

            //xml方式ObjectAnimator的使用,标签为<set>
            final AnimatorSet seq =
                    (AnimatorSet) AnimatorInflater.loadAnimator(appContext,
                            R.animator.animator_set);
            seq.setTarget(balls.get(6));

            //用xml方式ObjectAnimator完成颜色动画
            final ObjectAnimator colorizer = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.color_animator);
            colorizer.setTarget(balls.get(7));

            //xml方式PropertyValuesHolder的使用，标签为<propertyValuesHolder>
            final ObjectAnimator animPvh = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.object_animator_pvh);
            animPvh.setTarget(balls.get(8));

            //xml方式Keyframe的使用，标签为<keyframe>
            final ObjectAnimator animPvhKf = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.object_animator_pvh_kf);
            animPvhKf.setTarget(balls.get(9));

            //xml方式Keyframe、PropertyValuesHolder，包装成ValueAnimator
            final ValueAnimator faderKf = (ValueAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.value_animator_pvh_kf);
            faderKf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    balls.get(10).setAlpha((Float) animation.getAnimatedValue());
                }
            });

            //带interpolator的Keyframe、PropertyValuesHolder，包装成ObjectAnimator
            final ObjectAnimator animPvhKfInterpolated = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.object_animator_pvh_kf_interpolated);
            animPvhKfInterpolated.setTarget(balls.get(11));

            //将上述12个动画封装到AnimatorSet中，with的(包括play的)一起先播，before的一起后播
            animation = new AnimatorSet();
            ((AnimatorSet) animation).play(yBouncer).with(yAlphaBouncer).with(whxyBouncer).with(
                    yxBouncer).before(anim).before(fader).before(seq).before(colorizer).before(animPvh).before(
                    animPvhKf).before(faderKf).before(animPvhKfInterpolated);
        }
    }

    public void startAnimation(Context ctx) {
        createAnimation(ctx);
        animation.start();
    }

    public void stopAnimation() {

        animation.cancel();
    }

    //小球的产生
    private ShapeHolder createBall(float x, float y) {
        OvalShape circle = new OvalShape();
        circle.resize(BALL_SIZE, BALL_SIZE);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        ShapeHolder shapeHolder = new ShapeHolder(drawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);
        return shapeHolder;
    }

    //小球的添加方法1
    private void addBall(float x, float y, int color) {
        ShapeHolder shapeHolder = createBall(x, y);
        shapeHolder.setColor(color);
        balls.add(shapeHolder);
    }

    //小球的添加方法2
    private void addBall(float x, float y) {
        ShapeHolder shapeHolder = createBall(x, y);
        int red = (int) (100 + Math.random() * 155);
        int green = (int) (100 + Math.random() * 155);
        int blue = (int) (100 + Math.random() * 155);
        int color = 0xff000000 | red << 16 | green << 8 | blue;
        Paint paint = shapeHolder.getShape().getPaint();
        int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
        RadialGradient gradient = new RadialGradient(37.5f, 12.5f,
                50f, color, darkColor, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        balls.add(shapeHolder);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (ShapeHolder ball : balls) {
            canvas.translate(ball.getX(), ball.getY());
            ball.getShape().draw(canvas);
            canvas.translate(-ball.getX(), -ball.getY());
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        invalidate();
    }
}

