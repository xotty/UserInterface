/*
overridePendingTransition 需要注意加载的时机，即Activity#onCreate之前调用   Activity#finish之后调用

在其他时机调用无效
 */
package org.xottys.userinterface.animation;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.widget.ArrayAdapter;

import org.xottys.userinterface.animation.transition.ActivityOptionsActivity;
import org.xottys.userinterface.animation.transition.LayoutAnimationControllerActivity;
import org.xottys.userinterface.animation.transition.LayoutTransitionActivity;
import org.xottys.userinterface.animation.transition.TransitionActivity;
import org.xottys.userinterface.animation.transition.TransitionManagerActivity;

public class TransitionAnimationActivity extends LauncherActivity {

    //定义要跳转的各个Activity的名称
    String[] names = {"ActivityOptions Demo", "Transition Demo", "TransitionManager Demo",
            "LayoutTransition Demo","LayoutAnimationController  Demo"};

    Class<?>[] clazzs = {ActivityOptionsActivity.class,TransitionActivity.class, TransitionManagerActivity.class,
            LayoutTransitionActivity.class,LayoutAnimationControllerActivity.class};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //进入的动画
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_bottom);
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);

    }

    @Override
    public Intent intentForPosition(int position) {

        return new Intent(TransitionAnimationActivity.this, clazzs[position]);
    }

    @Override
    public void finish() {
        super.finish();
        //退出的动画
        overridePendingTransition(0, R.anim.slide_out_bottom);
    }
}
