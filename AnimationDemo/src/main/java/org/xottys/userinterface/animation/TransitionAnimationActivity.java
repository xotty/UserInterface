/*
overridePendingTransition 需要注意加载的时机，即Activity#onCreate之前调用   Activity#finish之后调用

在其他时机调用无效
 */
package org.xottys.userinterface.animation;

import android.app.ActivityOptions;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xottys.userinterface.animation.transition.ActivityOptionsActivity;
import org.xottys.userinterface.animation.transition.LayoutAnimationControllerActivity;
import org.xottys.userinterface.animation.transition.LayoutTransitionActivity;
import org.xottys.userinterface.animation.transition.TransitionActivity;
import org.xottys.userinterface.animation.transition.TransitionManagerActivity;

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
