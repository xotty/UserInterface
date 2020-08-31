/**
 * 本例演示了Activity转场动画的方法之三：Transition，具体使用步骤如下：
 * 1）xml中指定允许:windowActivityTransitions和windowContentTransitions的Theme
 *<style name="AppTheme" parent="Theme.AppCompat">
 *  <item name="android:windowActivityTransitions">true</item>
 *  <item name="android:windowContentTransitions">true</item>
 *</style>
 * 或者在setContentView之前调用
 * getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
 * getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
 * 2)动画是否覆盖，缺省为false，此步骤可选
 *     getWindow().setAllowEnterTransitionOverlap(true);
 *     getWindow().setAllowReturnTransitionOverlap(true);
 * A、普通转换
 * 3）用代码或xml中用标签<transitionSet>定义动画内容，系统提供Slide/Explode/Fade三种
 * 4）通过代码调用上述动画内容为Activity设置进入、退出、返回进入、返回退出动画
 *  getWindow().setExitTransition();A->B,A退出动画，在A中设置
 *  getWindow().setEnterTransition();A->B，B进入动画，在B中设置
 *  getWindow().setReenterTransition();B-->A,A再次进入动画，在A中设置，若不设置默认和setExitTransition一样
 *  getWindow().setReturnTransition(Transition transition) B-->A,B退出的动画，在B中设置
 * 5）构造ActivityOptions：ActivityOptions.makeSceneTransitionAnimation(this)
 * B.共享转换
 * 对不同activity或fragment的共享的view元素设置统一的 android:transitionName
 * 3)用代码或xml中用标签<transitionSet>定义动画内容，系统提供ChangeBounds 、ChangeTransform、ChangeClipBounds、
 *   ChangeImageTransform、ChangeScroll等几种
 * 4）通过代码调用上述动画内容为Activity设置共享进入、退出、返回进入、返回退出动画
 *	getWindow().setSharedElementEnterTransition();A->B,B进入动画
 *  getWindow().setSharedElementExitTransition();A->B,A退出动画
 *  getWindow().setSharedElementReenterTransition();B->A，A进入动画
 *  getWindow().setSharedElementReturnTransition();B->A,B退出动画
 * 5）构造ActivityOptions：
 * --ActivityOptions.makeSceneTransitionAnimation(this，sharedView,"share")
 * --ActivityOptions.makeSceneTransitionAnimation(this ,Pair.create(sharedView1, "share1") , Pair.create(sharedView2, "share2"));
 * 其中"sharedView"为共享元素，"share"为对应的transitionName。
 *
 * 6）用ActivityOptions启动新的Activity
 *     startActivity(intent, activityOptions.toBundle());
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
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;

public class TransitionActivity extends Activity {

    private View squareBlue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置允许条件
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        //设置动画覆盖
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);

        //用代码方式设置Activity的进入、退出、返回进入、返回退出动画
        Fade enterTransition = new Fade();
        enterTransition.setDuration(500);
        getWindow().setEnterTransition(enterTransition);

        Transition returnTransition = new Slide(Gravity.BOTTOM);
        returnTransition.setDuration(500);
        getWindow().setReturnTransition(returnTransition);

        Transition exitTransition = new Explode();
        returnTransition.setDuration(500);
        getWindow().setExitTransition(exitTransition);

        Transition reenterTransition = new Slide(Gravity.TOP);
        returnTransition.setDuration(500);
        getWindow().setReenterTransition(reenterTransition);
        setContentView(R.layout.activity_transition);


        squareBlue = findViewById(R.id.imgicon);
        ((ImageView)squareBlue).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        if (getActionBar()!=null) getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, TransitionDetailsActivity.class);
        //给下一个Activity传入参数，以便呈现不同的动画效果
        switch (v.getId()) {
            case R.id.explode_code:
                intent.putExtra("flag", 0);
                break;
            case R.id.explode_xml:
                intent.putExtra("flag", 1);
                break;
            case R.id.slide_code:
                intent.putExtra("flag", 2);
                break;
            case R.id.slide_xml:
                intent.putExtra("flag", 3);
                break;
            case R.id.fade_code:
                intent.putExtra("flag", 4);
                break;
            case R.id.fade_xml:
                intent.putExtra("flag", 5);
                break;
            case R.id.share_activity:
                intent.putExtra("flag", 6);
                View cat = findViewById(R.id.img);
                View title = findViewById(R.id.title_name);
                //创建单个共享元素
                //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view, "share").toBundle());
                //创建多个共享元素，每个Pair是一个共享元素
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create(cat, "cat"),
                        Pair.create(title, "title_name"))
                        .toBundle());
                break;
            case R.id.share_fragment:
                intent = new Intent(this, SharedElementActivity.class);
                final View tname = findViewById(R.id.share_fragment);
                //共享转换时启动Activity跳转
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create(tname, "title"),
                        Pair.create(squareBlue, "square_blue"))
                        .toBundle());

                break;
            //Activity返回呈现的动画
            case R.id.exit:
                Transition returnTransition = new Slide(Gravity.TOP);
                returnTransition.setDuration(500);
                getWindow().setReturnTransition(returnTransition);

                //结束本Activity时须呈现的动画
                finishAfterTransition();

                break;
        }

        //普通转换时启动Activity跳转
       if  (v.getId()!=R.id.share_activity && v.getId()!=R.id.exit &&v.getId()!=R.id.share_fragment )
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    //点击ActionBar返回键呈现的退出动画
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {   Transition returnTransition = new Explode();
            returnTransition.setDuration(500);
            getWindow().setReturnTransition(returnTransition);
            finishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
