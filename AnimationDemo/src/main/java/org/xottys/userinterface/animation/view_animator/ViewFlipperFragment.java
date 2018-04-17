/**
 * 本例演示了ViewFlipper的基本用法，具体使用步骤如下：
 * 1）在xml中用标签<ViewFlipper>定义,用view.findViewById(R.id.view_flipper)来加载
 * 2）myViewFlipper.addView()或在xml中定义视图内容;
 * 3）设置进入和退出动画：
 *    myViewFlipper.setInAnimation(animation)；
 *    myViewFlipper.setOutAnimation(animation)；
 *    也可以在xml中定义
 * 4）启动切换：
 *    myViewFlipper.startFlipping()----自动循环切换
 *    或
 *    myViewFlipper.showNext()
 *    myViewFlipper.showPrevious()----手动切换
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.view_animator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import org.xottys.userinterface.animation.R;
public class ViewFlipperFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {
    private String[] mStrings = {
            "Push up", "Push left", "Cross fade","Hyperspace", "Zoom"};

    private ViewFlipper mFlipper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_viewflipper, null);
        mFlipper =  view.findViewById(R.id.flipper);
        //自动循环切换视图
        mFlipper.startFlipping();

        //选择播放动画的种类
        Spinner s =  view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, mStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
        
        return view;
    }

    //用spinner选择动画种类
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {

            case 0:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.push_up_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.push_up_out));
                break;
            case 1:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.push_left_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.push_left_out));
                break;
            case 2:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        android.R.anim.fade_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        android.R.anim.fade_out));
                break;
            case 3:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.hyperspace_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.hyperspace_out));
                break;
            default:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.zoomin));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.zoomout));
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}