/**
 * 本例为演示Fragment Transition的目标Fragment：
 * 1)所有动画在上一个Fragment中都已设置完毕，此处未再设置。其实也可以改在这里设置。
 * 2)Fragment的transition是在其被FragmentTransaction执行下列动作的时候自动发生的:
 * added, removed, attached, detached, shown, ，hidden。
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
import androidx.fragment.app.Fragment;
import androidx.core.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;

public class SharedElementDetailsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /*进入动画也可以在这里设置
        Slide slideTransition = new Slide(Gravity.END);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        setEnterTransition(slideTransition);
        setSharedElementEnterTransition(changeBoundsTransition);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sharedelement_fragment2, container, false);

        ImageView squareBlue =  view.findViewById(R.id.square_blue);
        DrawableCompat.setTint(squareBlue.getDrawable(), Color.BLUE);

        return view;
    }
}
