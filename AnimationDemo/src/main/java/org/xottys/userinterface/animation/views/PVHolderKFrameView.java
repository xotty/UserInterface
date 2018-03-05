package org.xottys.userinterface.animation.views;

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
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import org.xottys.userinterface.animation.R;

import java.util.ArrayList;

public class PVHolderKFrameView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final float BALL_SIZE = 100f;
    private static final int DURATION = 1500;

    public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
    Animator animation = null;
    AnimatorSet animatorSet1, animatorSet2;

    public PVHolderKFrameView(Context context) {
        super(context);
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
            ball = balls.get(0);
            ObjectAnimator yBouncer = ObjectAnimator.ofFloat(ball, "y",
                    ball.getY(), getHeight() - BALL_SIZE).setDuration(DURATION);
            yBouncer.setInterpolator(new BounceInterpolator());
            yBouncer.addUpdateListener(this);

            ball = balls.get(1);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", ball.getY(),
                    getHeight() - BALL_SIZE);
            PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);
            ObjectAnimator yAlphaBouncer = ObjectAnimator.ofPropertyValuesHolder(ball,
                    pvhY, pvhAlpha).setDuration(DURATION / 2);
            yAlphaBouncer.setInterpolator(new AccelerateInterpolator());
            yAlphaBouncer.setRepeatCount(1);
            yAlphaBouncer.setRepeatMode(ValueAnimator.REVERSE);


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

            ball = balls.get(3);
            pvhY = PropertyValuesHolder.ofFloat("y", ball.getY(), getHeight() - BALL_SIZE);
            float ballX = ball.getX();
            Keyframe kf0 = Keyframe.ofFloat(0f, ballX);
            Keyframe kf1 = Keyframe.ofFloat(.5f, ballX + 100f);
            Keyframe kf2 = Keyframe.ofFloat(1f, ballX + 50f);
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofKeyframe("x", kf0, kf1, kf2);
            ObjectAnimator yxBouncer = ObjectAnimator.ofPropertyValuesHolder(ball, pvhY,
                    pvhX).setDuration(DURATION / 2);
            yxBouncer.setRepeatCount(1);
            yxBouncer.setRepeatMode(ValueAnimator.REVERSE);

            final ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.object_animator);
            anim.addUpdateListener(this);
            anim.setTarget(balls.get(4));

            final ValueAnimator fader = (ValueAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.animator);
            fader.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    balls.get(5).setAlpha((Float) animation.getAnimatedValue());
                }
            });

            final AnimatorSet seq =
                    (AnimatorSet) AnimatorInflater.loadAnimator(appContext,
                            R.animator.animator_set);
            seq.setTarget(balls.get(6));

            final ObjectAnimator colorizer = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.color_animator);
            colorizer.setTarget(balls.get(7));

            final ObjectAnimator animPvh = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.object_animator_pvh);
            animPvh.setTarget(balls.get(8));


            final ObjectAnimator animPvhKf = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.object_animator_pvh_kf);
            animPvhKf.setTarget(balls.get(9));

            final ValueAnimator faderKf = (ValueAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.value_animator_pvh_kf);
            faderKf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    balls.get(10).setAlpha((Float) animation.getAnimatedValue());
                }
            });

            // This animation has an accelerate interpolator applied on each
            // keyframe interval. In comparison, the animation defined in
            // R.anim.object_animator_pvh_kf uses the default linear interpolator
            // throughout the animation. As these two animations use the
            // exact same path, the effect of the per-keyframe interpolator
            // has been made obvious.
            final ObjectAnimator animPvhKfInterpolated = (ObjectAnimator) AnimatorInflater.
                    loadAnimator(appContext, R.animator.object_animator_pvh_kf_interpolated);
            animPvhKfInterpolated.setTarget(balls.get(11));
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

    private ShapeHolder createBall(float x, float y) {
        OvalShape circle = new OvalShape();
        circle.resize(BALL_SIZE, BALL_SIZE);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        ShapeHolder shapeHolder = new ShapeHolder(drawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);
        return shapeHolder;
    }

    private void addBall(float x, float y, int color) {
        ShapeHolder shapeHolder = createBall(x, y);
        shapeHolder.setColor(color);
        balls.add(shapeHolder);
    }

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

    public void onAnimationUpdate(ValueAnimator animation) {

        invalidate();
    }
}

