package org.xottys.userinterface.animation;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;


public class MainActivity extends LauncherActivity {

    //定义要跳转的各个Activity的名称
    String[] names = {"TweenedAnimation Demo", "FrameAnimation Demo", "PropertyAnimation Demo1", "PropertyAnimation Demo2","SVGAnimation Demo",
            "ViewAnimator Demo","PhysicsAnimation Demo", "TransitionAnimation Demo", "3DAnimation Demo", "MiscAnimation Demo"};

    //定义各个Activity对应的实现类
    Class<?>[] clazzs = {TweenedAnimationActivity.class, FrameAnimationActivity.class, PropertyAnimationActivity1.class, PropertyAnimationActivity2.class,CustomSVGActivity.class,
            ViewAnimatorActivity.class,PhysicsAnimationActivity.class, TransitionAnimationActivity.class, ThreeDimensionAnimationActivity.class, MiscAnimationActivity.class};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将names数组的内容装入Adapter,以便显示在listview中
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);

    }

    //将clazzs数组直接放入，系统将按顺序对应listview上的每一行，行点击后将跳转相应Intent的Activity
    @Override
    public Intent intentForPosition(int position) {
        return new Intent(MainActivity.this, clazzs[position]);
    }
}
