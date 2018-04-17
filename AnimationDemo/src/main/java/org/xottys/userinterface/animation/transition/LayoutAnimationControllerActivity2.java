/**
 * 本例演示了在ViewGroup的xml中直接启用LayoutAnimationController的用法：
 * 在ViewGroup的xml定义中用属性android:layoutAnimation关联LayoutAnimation动画即可
 * 该动画在xml中用标签<layoutAnimation>定义，相关动画属性均可在其中定义。
 * <p>
 * <br/>Copyright (C), 2017-2018, Google
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutAnimationControllerActivity
 * <br/>Date:Mar，2018
 *
 * @author Google
 * @version 1.0
 */
package org.xottys.userinterface.animation.transition;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.xottys.userinterface.animation.R;

public class LayoutAnimationControllerActivity2 extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutanimationcontroller2);
        setListAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mStrings));
    }

    private String[] mStrings = {
        "Bordeaux",
        "Lyon",
        "Marseille",
        "Nancy",
        "Paris",
        "Toulouse",
        "Strasbourg"
    };
}
