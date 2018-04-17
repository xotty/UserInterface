/**
 * 本例演示了Activity 转场动画的方法之一：overridePendingTransition，具体使用步骤如下：
 * 1）用xml定义动画或动画集合
 * 2）紧挨着startActivity()或者finish()函数之后调用overridePendingTransition，否则无效
 * 3) 参数含义：第一个参数是第二个activity进入时的动画，第二个参数则是第一个activity退出时的动画
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

import android.app.ActivityOptions;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xottys.userinterface.animation.R;

public class TransitionAnimationActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           String[] activity_names = {"ActivityOptions Demo", "Transition Demo", "TransitionManager Demo",
            "LayoutTransition Demo","LayoutAnimationController Demo"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, activity_names);
        ListView listView = this.getListView();
        listView.setItemsCanFocus(true);
        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent=null;
        switch (position){
            case 0:
                intent=new Intent(TransitionAnimationActivity.this,ActivityOptionsActivity.class);
                break;
            case 1:
                intent=new Intent(TransitionAnimationActivity.this,TransitionActivity.class);
                break;
            case 2:
                intent=new Intent(TransitionAnimationActivity.this,TransitionManagerActivity.class);
                break;
            case 3:
                intent=new Intent(TransitionAnimationActivity.this,LayoutTransitionActivity.class);
                break;
            case 4:
                intent=new Intent(TransitionAnimationActivity.this,LayoutAnimationControllerActivity.class);
                break;
        }
        ActivityOptions activityOptions
                = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(intent, activityOptions.toBundle());
    }

    @Override
    public void finish() {
        super.finish();
        //退出的动画
        overridePendingTransition(0, R.anim.slide_out_bottom);
    }
}
