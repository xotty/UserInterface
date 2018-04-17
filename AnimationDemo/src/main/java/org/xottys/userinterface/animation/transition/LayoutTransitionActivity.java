/**
 * 本例演示了LayoutTransition用法，当ViewGroup中的元素出现下列情况时会产生相应动画效果：
 *--LayoutTransition.APPEARING，视图出现  
 *--LayoutTransition.DISAPPEARING，视图消失
 *--LayoutTransition.CHANGE_APPEARING，视图出现，导致其它控件位置改变  
 *--LayoutTransition.CHANGE_DISAPPEARING，视图消失，导致其它控件位置改变   
 *--LayoutTransition.CHANGE，其它原因导致控件位置改变
 * 1）定义LayoutTransition对象，并将其用于ViewGroup
 *    LayoutTransition mLayoutTransition = new LayoutTransition();
 *    mViewGroup.setLayoutTransition(mLayoutTransition);
 * 或者在ViewGroup的xml中设置android:animateLayoutChanges="true"
 *    这样在ViewGroup中添加或删除视图时就会出现系统默认动画
 * 2）改变LayoutTransition动画效果
 *    先用Animator定义动画，然后加载到LayoutTransition中
 *    mLayoutTransition.setAnimator(LayoutTransition.APPEARING, currentAppearingAnim);
 *    mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, currentDisappearingAnim);
 *    mLayoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, currentChangingAppearingAnim);
 *    mLayoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, currentChangingDisappearingAnim);
 * 3)设置动画参数：duration、interpolater、启动延迟时间等(可选)
 * 4)设置/移除监听器(可选)
 * mLayoutTransition.addTransitionListener(new LayoutTransition.TransitionListener(){
 *   @Overide
 *   publlic void  startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType){
 *   }
 *   @Overide
 *   publlic void  endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType){
 *   }
 *   });
 *
 * mLayoutTransition.removeTransitionListener(listener);
 *
 * <p>
 * <br/>Copyright (C), 2017-2018, Google
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutTransitionActivity
 * <br/>Date:Mar，2018
 *
 * @author Google
 * @version 1.0
 */
package org.xottys.userinterface.animation.transition;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.RadioGroup;

import org.xottys.userinterface.animation.R;

public class LayoutTransitionActivity extends Activity {
    enum BtnHide {btn_remove, btn_gone, btn_invisible}
    BtnHide btnhide = BtnHide.btn_remove;
    GridLayout container = null;

    private Animator defaultAppearingAnim, defaultDisappearingAnim;
    private Animator defaultChangingAppearingAnim, defaultChangingDisappearingAnim;
    private Animator customAppearingAnim, customDisappearingAnim;
    private Animator customChangingAppearingAnim, customChangingDisappearingAnim;
    private Animator currentAppearingAnim, currentDisappearingAnim;
    private Animator currentChangingAppearingAnim, currentChangingDisappearingAnim;

    private int numButtons = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouttransition);
        container = findViewById(R.id.gridContainer);

        //定义LayoutTransition对象
        final LayoutTransition mLayoutTransition = new LayoutTransition();
        //给ViewGroup应用定义的LayoutTransition
        container.setLayoutTransition(mLayoutTransition);

        //获取缺省的Animator
        defaultAppearingAnim = mLayoutTransition.getAnimator(LayoutTransition.APPEARING);
        defaultDisappearingAnim =
                mLayoutTransition.getAnimator(LayoutTransition.DISAPPEARING);
        defaultChangingAppearingAnim =
                mLayoutTransition.getAnimator(LayoutTransition.CHANGE_APPEARING);
        defaultChangingDisappearingAnim =
                mLayoutTransition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING);

        //创建自定义的Animator
        createCustomAnimations(mLayoutTransition);

        //将当前动画赋初值为缺省动画
        currentAppearingAnim = defaultAppearingAnim;
        currentDisappearingAnim = defaultDisappearingAnim;
        currentChangingAppearingAnim = defaultChangingAppearingAnim;
        currentChangingDisappearingAnim = defaultChangingDisappearingAnim;

        //In：LayoutTransition.APPEARING
        CheckBox appearingCB = findViewById(R.id.appearingCB);
        appearingCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //设置当前动画为空
            if (!isChecked) currentAppearingAnim = null;
            //设置该转换动画为当前动画
            mLayoutTransition.setAnimator(LayoutTransition.APPEARING, currentAppearingAnim);
        });

        //Out：LayoutTransition.DISAPPEARING
        CheckBox disappearingCB = findViewById(R.id.disappearingCB);
        disappearingCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) currentDisappearingAnim = null;
            mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, currentDisappearingAnim);
        });

        //ChangingIn：LayoutTransition.CHANGE_APPEARING
        CheckBox changingAppearingCB = findViewById(R.id.changingAppearingCB);
        changingAppearingCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) currentChangingAppearingAnim = null;
            mLayoutTransition.setAnimator(LayoutTransition.CHANGE_APPEARING, currentChangingAppearingAnim);

        });

        //ChangingOut：LayoutTransition.CHANGE_DISAPPEARING
        CheckBox changingDisappearingCB = findViewById(R.id.changingDisappearingCB);
        changingDisappearingCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) currentChangingDisappearingAnim = null;
            mLayoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, currentChangingDisappearingAnim);

        });

        //设置Button隐藏的几种情况：Remove、Gone、Invisible
        RadioGroup radiogroup = findViewById(R.id.btnhide);
        radiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.remove:
                    btnhide = BtnHide.btn_remove;
                    break;
                case R.id.gone:
                    btnhide = BtnHide.btn_gone;
                    break;
                case R.id.invisible:
                    btnhide = BtnHide.btn_invisible;
                    break;
            }
        });

        //是否使用自定义动画
        CheckBox customAnimCB = findViewById(R.id.customAnimCB);
        customAnimCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //使用自定义动画
            if (isChecked) {
                currentAppearingAnim = customAppearingAnim;
                currentDisappearingAnim = customDisappearingAnim;
                currentChangingAppearingAnim = customChangingAppearingAnim;
                currentChangingDisappearingAnim = customChangingDisappearingAnim;
            }
            //使用缺省动画
            else {
                currentAppearingAnim = defaultAppearingAnim;
                currentDisappearingAnim = defaultDisappearingAnim;
                currentChangingAppearingAnim = defaultChangingAppearingAnim;
                currentChangingDisappearingAnim = defaultChangingDisappearingAnim;
            }
            //设置所有转换动画为当前动画
            setupTransition(mLayoutTransition);
        });

        Button addButton = findViewById(R.id.addNewButton);
        addButton.setOnClickListener(v -> {
            //设置ADD_BUTTON点击动作：container中添加一个新View：Button
            Button newButton = new Button(LayoutTransitionActivity.this);
            newButton.setPadding(5, 5, 5, 5);
            newButton.setText(String.valueOf(numButtons++));
            container.addView(newButton, Math.min(1, container.getChildCount()));

            //设置新添加的Button点击动作：该Button在container中Remove、Gone 或Invisible
            newButton.setOnClickListener(vv -> {
                switch (btnhide) {
                    case btn_remove:
                        container.removeView(v);
                        break;
                    case btn_gone:
                        v.setVisibility(View.GONE);
                        break;
                    case btn_invisible:
                        v.setVisibility(View.INVISIBLE);
                        break;
                }
            });
        });
    }

    //设置所有转换动画
    private void setupTransition(LayoutTransition transition) {
        transition.setAnimator(LayoutTransition.APPEARING, currentAppearingAnim);
        transition.setAnimator(LayoutTransition.DISAPPEARING, currentDisappearingAnim);
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, currentChangingAppearingAnim);
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, currentChangingDisappearingAnim);
    }

    //构造自定义动画
    private void createCustomAnimations(LayoutTransition transition) {
        // Changing while Adding
        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 1);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 1);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 1);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 1);
        PropertyValuesHolder pvhScaleX =
                PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
        PropertyValuesHolder pvhScaleY =
                PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
        customChangingAppearingAnim = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhScaleX, pvhScaleY).
                setDuration(transition.getDuration(LayoutTransition.CHANGE_APPEARING));
        customChangingAppearingAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                if (view != null) {
                    view.setScaleX(1f);
                    view.setScaleY(1f);
                }
            }
        });

        // Changing while Removing
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.9999f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation =
                PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        customChangingDisappearingAnim = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhTop, pvhRight, pvhBottom, pvhRotation).
                setDuration(transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        customChangingDisappearingAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                if (view != null) view.setRotation(0f);
            }
        });

        // Adding
        customAppearingAnim = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f).
                setDuration(transition.getDuration(LayoutTransition.APPEARING));
        customAppearingAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                if (view != null) view.setRotationY(0f);
            }
        });

        // Removing
        customDisappearingAnim = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f).
                setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));
        customDisappearingAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                if (view != null) view.setRotationX(0f);
            }
        });
    }
}
