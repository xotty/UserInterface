/**
 * 本例演示了LayoutAnimationController的基本用法：
 * 1）定义Animation或AnimationSet
 * 2）用上述Animation定义LayoutAnimationController
 * 3）(可选)设置LayoutAnimationController各项参数：order、interpolater、delay等
 * 4）将LayoutAnimationController加载到ViewGroup：listView.setLayoutAnimation(controller)；
 * 5）
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LayoutAnimationControllerActivity1 extends ListActivity {
    private String[] mStrings = {
            "Bordeaux",
            "Lyon",
            "Marseille",
            "Nancy",
            "Paris",
            "Toulouse",
            "Strasbourg"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mStrings));

        //定义AnimationSet及其内容
        AnimationSet set = new AnimationSet(true);
        Animation animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(50);
        set.addAnimation(animation1);
        Animation animation2 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation2.setDuration(200);
        set.addAnimation(animation2);

        //定义LayoutAnimationController，将上述AnimationSet加载其中
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

        //controller.setOrder(LayoutAnimationController.ORDER_REVERSE);

        ListView listView = getListView();

        //将LayoutAnimationController加载到ViewGroup上
        listView.setLayoutAnimation(controller);
    }
}
