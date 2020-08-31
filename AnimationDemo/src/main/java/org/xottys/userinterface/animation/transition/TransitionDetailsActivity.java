/**
 * 本例为演示Transition的目标Activity之一，xml方式transition加载方法如下：
 * TransitionInflater.from(this).inflateTransition(R.transition.my_transition);
 *
 * 此处只设置Activity和共享元素的进入动画，共享元素由上一个Activity说明
 * Activity中transition是被startActivity()和finishAfterTransition()触发的
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

import android.app.Activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Window;

import org.xottys.userinterface.animation.R;

public class TransitionDetailsActivity extends Activity {
    Transition transition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置允许条件
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        setContentView(R.layout.activity_transitiondetail);

        //获取上一个Activity传入的参数
        int flag=0;
        if (getIntent().getExtras()!=null)
            flag = getIntent().getExtras().getInt("flag");

        //根据传入的参数定义不同的transition
        switch (flag) {
            //代码方式定义
            case 0:
                transition=new Explode();
                transition.setDuration(500);
                break;
            //xml方式定义
            case 1:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.t_explode);
                break;
            case 2:
                transition=new Slide();
                ((Slide)transition).setSlideEdge(Gravity.END);
                transition.setDuration(500);
                break;
            case 3:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
                break;
            case 4:
                transition=new Fade();
                transition.setDuration(500);
            case 5:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.t_fade);
                break;
            case 6:
                //设置共享元素进入动画
                // 也可以不设置而使用缺省动画@android:transition/move
                //TransitionInflater.from(this).inflateTransition(android.R.transition.move);
                getWindow().setSharedElementEnterTransition(new ChangeBounds());
                //Activity进入动画未设置，即表示使用缺省动画Fade
                return;
        }

        //设置Activity进入动画
        getWindow().setEnterTransition(transition);


    }
}
