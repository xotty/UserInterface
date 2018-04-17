/**
 * AdapterViewAnimator通过Adapter方式提供View内容，在View切换时呈现动画效果，主要包括：
 * 1)StackView--上下渐次层叠显示所有View，通过手势或功能键逐个切换到前台
 * 2)AdapterViewwFlipper--前后显示所有View（后面的隐藏），通过功能键自动或手动切换显示各个View
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.adapterview_animator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import org.xottys.userinterface.animation.R;

public class AdapterViewAnimatorActivity extends FragmentActivity{
    private TextView textView;
    LayoutInflater mLayoutInflater;
    // 存放Fragment的数组
    private Class mFragmentArray[] = {StackViewFragment.class, AdapterViewFlipperFragment.class};
    // 存放Tab 标签文字的数组
    private String mTextArray[] = {"StackView", "AdapterViewwFlipper"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.activity_transitionmanager);

        mLayoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        FragmentTabHost mTabHost = findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent1);
        int count = mFragmentArray.length;

        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
                    .setIndicator(getTabItemView(i));

            // 将Tab按钮和对应的Fragment一起添加进Tab中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);

            // 设置Tab按钮的背景
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);

        }
    }
    //给每个Tab按钮设置图标和文字
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
        textView = view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);
        return view;
    }

}
