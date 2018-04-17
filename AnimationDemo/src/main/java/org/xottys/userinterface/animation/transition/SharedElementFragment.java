/**
 * 本例为演示Fragment Transition的启动Fragment，转换到另一个Fragment时：
 * 1）在加载前设置下一个Fragment及其共享元素的进入动画，Fragment之间的共享元素通过
 * getFragmentManager().beginTransaction().addSharedElement()来说明
 * 2）setAllowEnterTransitionOverlap(true)：进入动画尽快开始
 *    setAllowEnterTransitionOverlap(false)：进入动画待结束动画完成后才开始
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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import org.xottys.userinterface.animation.R;

public class SharedElementFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sharedelement_fragment1, container, false);

        final ImageView squareBlue =  view.findViewById(R.id.square_blue);
        DrawableCompat.setTint(squareBlue.getDrawable(), Color.BLUE);

        //点击按钮加载另一个Fragment，不覆盖
        view.findViewById(R.id.sample_button1).setOnClickListener(v->
                addNextFragment(squareBlue, false));

        //点击按钮加载另一个Fragment，覆盖
        view.findViewById(R.id.sample_button2).setOnClickListener(v->
                addNextFragment( squareBlue, true));

        return view;
    }

    //加载下一个Fragment
    private void addNextFragment( ImageView squareBlue, boolean overlap) {
        SharedElementDetailsFragment sharedElementDetailsFragment = new SharedElementDetailsFragment();

        //为Fragment设置覆盖属性
        sharedElementDetailsFragment.setAllowEnterTransitionOverlap(overlap);
        sharedElementDetailsFragment.setAllowReturnTransitionOverlap(overlap);

        //定义动画
        Slide slideTransition = new Slide(Gravity.END);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        //为Fragment设置动画
        sharedElementDetailsFragment.setEnterTransition(slideTransition);
        sharedElementDetailsFragment.setSharedElementEnterTransition(changeBoundsTransition);

        //加载Fragment，Fragment转Fragment共享元素动画使用addSharedElement完成启动
        getFragmentManager().beginTransaction()
                .replace(R.id.sample_content, sharedElementDetailsFragment)
                .addToBackStack(null)
                .addSharedElement(squareBlue, getString(R.string.square_blue_name))
                .commit();
    }
}
