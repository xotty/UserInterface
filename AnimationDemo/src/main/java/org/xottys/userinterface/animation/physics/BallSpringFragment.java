/**
 * 本例形象地演示了SpringAnimation中SpringForce属性的实际效果：
 * 1）DampingRatio：该值越大，反弹次数越少，值为1时不反弹，值为0时永远弹，值大于1的时候，动画靠近原位置的时候提前减速后停止
 * 2）Stiffness：该值越小，弹性越强，弹的时间越长。
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:PhysicsMainActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.physics;

import android.os.Bundle;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.Locale;

import org.xottys.userinterface.animation.R;


public class BallSpringFragment extends Fragment {
    private View img;
    private SpringView mSpringView;
    private SeekBar dr;
    private SeekBar stiff;
    private TextView drTxt;
    private TextView nfTxt;
    private float mDampingRatio;
    private float mStiffness;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ball_spring, container, false);
        //弹簧杆
        mSpringView = root.findViewById(R.id.actual_spring);
        //弹簧球
        img = root.findViewById(R.id.imageView);

        dr = root.findViewById(R.id.damping_ratio);
        stiff = root.findViewById(R.id.stiffness);
        drTxt = root.findViewById(R.id.damping_ratio_txt);
        nfTxt = root.findViewById(R.id.stiffness_txt);

        setupSeekBars();

        final SpringAnimation anim = new SpringAnimation(img, DynamicAnimation.TRANSLATION_Y,
                0 /* final position */);

        anim.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float v, float v1) {
                //将弹簧杆高度拉伸到弹簧球的顶端，随时保持二者连接
                mSpringView.setMassHeight(img.getY());
            }
        });

        ((View) img.getParent()).setOnTouchListener(new View.OnTouchListener() {
            private float touchOffset;
            private VelocityTracker vt;
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    // check whether the touch happens inside of the img view.
                    boolean inside = motionEvent.getX() >= img.getX()
                            && motionEvent.getX() <= img.getX() + img.getWidth()
                            && motionEvent.getY() >= img.getY()
                            && motionEvent.getY() <= img.getY() + img.getHeight();

                    anim.cancel();

                    if (!inside) {
                        return false;
                    }

                    // Apply this offset to all the subsequent events
                    touchOffset = img.getTranslationY() - motionEvent.getY();
                    vt = VelocityTracker.obtain();
                    vt.clear();
                }

                vt.addMovement(motionEvent);

                if (motionEvent.getActionMasked() == MotionEvent.ACTION_MOVE) {
                    //移动弹簧球位置
                    img.setTranslationY(motionEvent.getY() + touchOffset);
                    //相应移动弹簧杆位置
                    mSpringView.setMassHeight(img.getY());
                } else if (motionEvent.getActionMasked() == MotionEvent.ACTION_CANCEL
                        || motionEvent.getActionMasked() == MotionEvent.ACTION_UP) {
                    // Compute the velocity in unit: pixel/second
                    vt.computeCurrentVelocity(1000);
                    float velocity = vt.getYVelocity();

                    //设置参数后启动弹簧动画
                    anim.getSpring().setDampingRatio(mDampingRatio).setStiffness(mStiffness);
                    anim.setStartVelocity(velocity).start();

                    vt.recycle();
                }
                return true;
            }
        });
        return root;
    }

    // Setup seek bars so damping ratio and stiffness for the spring can be modified through the UI.
    void setupSeekBars() {
        dr.setMax(130);
        dr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i < 80) {
                    mDampingRatio = i / 80.0f;
                } else if (i > 90) {
                    mDampingRatio = (float) Math.exp((i - 90) / 10.0);
                } else {
                    mDampingRatio = 1;
                }
                drTxt.setText(String.format(Locale.CHINA,"%.4f", (float) mDampingRatio));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        stiff.setMax(110);
        stiff.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float stiffness = (float) Math.exp(i / 10d);
                mStiffness = stiffness;
                nfTxt.setText(String.format(Locale.CHINA,"%.3f", (float) stiffness));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dr.setProgress(40);
        stiff.setProgress(60);
    }


}
