/**
 * 本例演示了SpringAnimation及其监听器的用法：
 * 1）三参数构造器构造SpringAnimation，同时初始化了一个SpringForce
 * 2）OnAnimationUpdateListener可以时刻跟踪动画的过程
 * 3）animateToFinalPosition(float value)参数是相对变化量，而不是绝对量
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import org.xottys.userinterface.animation.R;

import java.util.Locale;

public class ChainedSpringFragment extends Fragment {
    private float mDampingRatio = 1.0f;
    private float mStiffness = 50.0f;

    private View lead;
    private SeekBar dr;
    private SeekBar stiff;
    private TextView drTxt;
    private TextView nfTxt;

    private SpringAnimation animate1X;
    private SpringAnimation animate1Y;
    private SpringAnimation animate2X;
    private SpringAnimation animate2Y;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chained_spring, container, false);
        //主动球
        lead = root.findViewById(R.id.lead);
        //随动球
        View follow1 = root.findViewById(R.id.follow1);
        View follow2 = root.findViewById(R.id.follow2);

        dr = root.findViewById(R.id.damping_ratio);
        stiff = root.findViewById(R.id.stiffness);

        drTxt = root.findViewById(R.id.damping_ratio_txt);
        nfTxt = root.findViewById(R.id.stiffness_txt);

        //三参数构造器构造两个随动球的弹簧动画,最后一个参数仅表示同时初始化了一个SpringForce

        animate1X = new SpringAnimation(follow1, DynamicAnimation.TRANSLATION_X,0);
        animate1Y = new SpringAnimation(follow1, DynamicAnimation.TRANSLATION_Y,follow1.getTranslationY());
        animate2X = new SpringAnimation(follow2, DynamicAnimation.TRANSLATION_X,follow2.getTranslationX());
        animate2Y = new SpringAnimation(follow2, DynamicAnimation.TRANSLATION_Y,1000);

        //让随动球2跟着随动球1
        animate1X.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float value, float velocity) {
                animate2X.animateToFinalPosition(value);
            }
        });
        animate1Y.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float value, float velocity) {
                animate2Y.animateToFinalPosition(value);
            }
        });

        ((View) lead.getParent()).setOnTouchListener(new View.OnTouchListener() {
            private float firstDownX = 0;
            private float firstDownY = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    if (motionEvent.getX() < lead.getX()
                            || motionEvent.getX() > lead.getX() + lead.getWidth()
                            || motionEvent.getY() < lead.getY()
                            || motionEvent.getY() > lead.getY() + lead.getHeight()) {
                        return false;
                    }
                    //手指按下时设置SpringForce参数
                    animate1X.getSpring().setDampingRatio(mDampingRatio).setStiffness(mStiffness);
                    animate1Y.getSpring().setDampingRatio(mDampingRatio).setStiffness(mStiffness);
                    animate2X.getSpring().setDampingRatio(mDampingRatio).setStiffness(mStiffness);
                    animate2Y.getSpring().setDampingRatio(mDampingRatio).setStiffness(mStiffness);
                    //获取手指初始位置偏移量
                    firstDownX = motionEvent.getX() - lead.getTranslationX();
                    firstDownY = motionEvent.getY() - lead.getTranslationY();
                } else if (motionEvent.getActionMasked() == MotionEvent.ACTION_MOVE) {
                    float deltaX = motionEvent.getX() - firstDownX;
                    float deltaY = motionEvent.getY() - firstDownY;
                    //手指移动时变换主动球的位置到手指停止位置
                    lead.setTranslationX(deltaX);
                    lead.setTranslationY(deltaY);
                    //随动球1用动画方式移动到主动球对应位置，启动动画
                    animate1X.animateToFinalPosition(deltaX);
                    animate1Y.animateToFinalPosition(deltaY);

                }
                return true;
            }
        });

        setupSeekBars();
        return root;
    }
    //用SeekBar设置SpringForce参数
    private void setupSeekBars() {
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

        //初值
        dr.setProgress(80);
        stiff.setProgress(60);
    }
}
