/**
 * 本例演示了可呈现动画的几种Drawable（不含简单的帧动画AnimationDrawable）的用法：
 * 一、TransitionDrawable
 * 1）在xml中用<transition>标签定义TransitionDrawable
 * 2）在xml中ImageView的src引用定义的TransitionDrawable
 * 3）加载TransitionDrawable  transitionDrawable = (TransitionDrawable) img.getDrawable()
 * 4）启动动画：transitionDrawable.startTransition(2000)
 *
 * 二、AnimatedVectorDrawable
 * 1）用标签<animated-vector>在xml中定义(三个独立的或一个完整的xml)
 * 2）作为Imageview的src
 * 3）加载：
 *    AnimatedVectorDrawable  mDrawable = (AnimatedVectorDrawable)imageview.getDrawable()
 * 4）启动动画：mDrawable.start()
 *
 * 三、AnimatedStateListDrawable
 * 1）用标签<animated-selector>在xml中定义控件各种状态下的样式和这些状态演化过程中的呈现的关键帧
 * 2）作为Imageview的src
 *
 * 四、StateListAnimator
 * 1）用标签<selector>在xml中定义控件各种状态下的属性动画样式
 * 2）加载动画：
 *    StateListAnimator mStateListAnimator=AnimatorInflater.loadStateListAnimator(this,R.drawable.mselector);
 * 3）将加载的动画运用到控件上
 *    mView.setStateListAnimator(mStateListAnimator);
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class AnimDrawableActivity extends Activity {

    private ImageView iv1,iv2,iv3,iv4,iv5;
    TransitionDrawable transitionDrawable;

    private boolean isSearchBoxChecked = false;

    private boolean isTwitterChecked = false;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animdrawable);
        //关闭软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //AnimatedVectorDrawable演示
        iv1 = findViewById(R.id.iv_1);
        //AnimatedStateListDrawable演示
        iv2 = findViewById(R.id.iv_2);
        iv3 = findViewById(R.id.iv_3);
        //StateListAnimator演示
        iv4 = findViewById(R.id.iv_4);
        //加载StateListAnimator动画
        StateListAnimator stateListAnimator=AnimatorInflater.loadStateListAnimator(this,R.drawable.svg_heart_selector);
        //将加载的动画运用到控件上
        iv4.setStateListAnimator(stateListAnimator);

        iv5 = findViewById(R.id.iv_5);
        transitionDrawable = (TransitionDrawable) iv5.getDrawable();
        transitionDrawable.startTransition(3000);
    }
    public void onTransClick(View view) {
        transitionDrawable.startTransition(2000);
    }
    public void onArrowClick(View view) {
        AnimatedVectorDrawable  drawable = (AnimatedVectorDrawable)iv1.getDrawable();
        drawable.start();
        /*等价替代方法
        Drawable drawable = iv1.getDrawable();
        ((Animatable) drawable).start();*/
    }

    public void onSearchBoxClick(View view) {
        isSearchBoxChecked = !isSearchBoxChecked;
        final int[] stateSet = {android.R.attr.state_checked * (isSearchBoxChecked ? 1 : -1)};
        iv2.setImageState(stateSet, true);
    }

    public void onTwitterClick(View view) {
        isTwitterChecked = !isTwitterChecked;
        final int[] stateSet = {android.R.attr.state_checked * (isTwitterChecked ? 1 : -1)};
        iv3.setImageState(stateSet, true);
    }
}
