/**
 * 本例为演示Transition的目标Activity之一，通过加载Fragment呈现视图，
 * 在加载前设置Fragment本身及其共享元素的动画，方法与Activity完全一样
 * 共享元素由上一个Activity说明，此处只设置本Fragment的退出/返回进入和共享元素进入动画
 *
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.transition;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;

import org.xottys.userinterface.animation.R;

public class SharedElementActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedelement);

        //定义动画
        Slide slideTransition = new Slide(Gravity.START);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));

        //为Fragment设置transition，均在加载之前定义好
        SharedElementFragment sharedElementFragment = new SharedElementFragment();
        //Fragment返回进入动画
        sharedElementFragment.setReenterTransition(slideTransition);
        //Fragment退出动画
        sharedElementFragment.setExitTransition(slideTransition);

        //Fragment共享元素进入动画
        sharedElementFragment.setSharedElementEnterTransition(new ChangeBounds());

        //加载Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sample_content, sharedElementFragment)
                .commit();
    }
}
