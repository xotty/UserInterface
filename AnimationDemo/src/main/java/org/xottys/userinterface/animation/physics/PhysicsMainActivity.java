/**
 * 本例为DynamicAnimation Demo的启动类
 * 1)Basic:Fling和Spring的基本用法
 * 2)BallSpring：DampingRatio和Stiffness属性的用法
 * 3)ChainedSpring：OnAnimationUpdateListener的用法
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:PhysicsMainActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.physics;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import org.xottys.userinterface.animation.R;

public class PhysicsMainActivity extends FragmentActivity {
    LayoutInflater mLayoutInflater;
    // 存放Fragment的数组
    private Class mFragmentArray[] = {BasicDynamicAnimationFragment.class, BallSpringFragment.class, ChainedSpringFragment.class};
    // 存放Tab 标签文字的数组
    private String mTextArray[] = {"Basic", " Ball Spring", "Chained Spring"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewanimator);
        mLayoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        FragmentTabHost mTabHost = findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent1);
        int count = mFragmentArray.length;

        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
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
        TextView textView = view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);
        return view;
    }

}
