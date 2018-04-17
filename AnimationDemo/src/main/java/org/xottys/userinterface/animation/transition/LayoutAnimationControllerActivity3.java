/**
 * 本例演示LayoutAnimationController的用法：
 * 1）用代码方式和xml方式加载在xml中定义的LayoutAnimationController
 * 2）代码方式加载的方法：
 *   LayoutAnimationController  controller  = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_animation);
 *   controller.getAnimation().setDuration(2000);  //设置动画属性
 *   mViewgroup.setLayoutAnimation(controller);
 * 3)LayoutAnimationController动画代码的优先级高于xml：父控件在代码中设置后，子控件在xml中设置的就无效了
 *
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutAnimationControllerActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */

package org.xottys.userinterface.animation.transition;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TableRow;

import org.xottys.userinterface.animation.R;

public class LayoutAnimationControllerActivity3 extends Activity {
    private static final String TAG = "LayoutAnimation3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layoutanimationcontroller3);
        ViewGroup mViewgroup=findViewById(android.R.id.content);

        //加载xml方式定义的LayoutAnimationController
        LayoutAnimationController  controller1  = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_animation_table);
        //设置LayoutAnimationController中的动画属性
        controller1.getAnimation().setDuration(2000);
        //应用LayoutAnimationController到ViewGroup
        mViewgroup.setLayoutAnimation(controller1);

        //用代码方式设置子控件的LayoutAnimationController，其它子控件为xml方式设置LayoutAnimationController
        TableRow l5=findViewById(R.id.line5);
        LayoutAnimationController  controller2  = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_animation_row_left_slide);
        controller2.getAnimation().setDuration(3000);
        l5.setLayoutAnimation(controller2);

        Log.i(TAG, "是否覆盖："+controller1.willOverlap());

    }
}
