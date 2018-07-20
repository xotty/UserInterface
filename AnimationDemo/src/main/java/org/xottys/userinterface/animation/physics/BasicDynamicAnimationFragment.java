/**
 * 本例演示了DynamicAnimation子类的基本用法
 * 1)FlingAnimation
 * 2)SpringAnimation
 * 具体使用步骤如下：
 * 1）用构造器构造DynamicAnimation
 * 2）设置物理动画的相关属性:如初速度、摩擦力、弹力等
 * 3）启动动画：myDynamicAnimation.start()
 * 4）必要时可设置监听器
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
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;

public class BasicDynamicAnimationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_basic_dynamicanimation, container, false);
        //动画对象
        final ImageView image = root.findViewById(R.id.robot);

        (root.findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
            float velocity = 500f;
            @Override
            public void onClick(View v) {
                FlingAnimation flingAnimation = new FlingAnimation(image, DynamicAnimation.X);
                flingAnimation.setStartVelocity(velocity);
                flingAnimation.setFriction(0.2f);
                flingAnimation.start();
                //负的速度表示反方向运动
                velocity = -velocity;
            }
        });

        (root.findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpringAnimation springAnimation = new SpringAnimation(image, DynamicAnimation.Y);
                //设置初始位置Y
                springAnimation.setStartValue(100);

                SpringForce springForce = new SpringForce();
                springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
                springForce.setStiffness(SpringForce.STIFFNESS_LOW);

                //设置最终回到原位置
                springForce.setFinalPosition(image.getPivotY());

                //默认起始速度是0
                springAnimation.setSpring(springForce);
                springAnimation.start();
            }
        });

        (root.findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //用自定义属性设置两个属性Scale.X、Scale.Y同时变化
                FloatPropertyCompat<View> myScale = new FloatPropertyCompat<View>("scale") {
                    @Override
                    public float getValue(View view) {
                        return view.getScaleY();
                    }

                    @Override
                    public void setValue(View view, float value) {
                        view.setScaleX(value);
                        view.setScaleY(value);
                    }
                };
                //使用自定义属性构建SpringAnimation
                SpringAnimation stretchAnimation = new SpringAnimation(image, myScale);
                stretchAnimation.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE);

                //设定动画结束时的最终属性值，此处为原来一半大小
                SpringForce force = new SpringForce(0.5f);

                force.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                        .setStiffness(SpringForce.STIFFNESS_VERY_LOW);

                //设置启动速度,默认启动速度是0
                stretchAnimation.setSpring(force).setStartVelocity(20f);

                stretchAnimation.start();
            }
        });
        return root;
    }

}
