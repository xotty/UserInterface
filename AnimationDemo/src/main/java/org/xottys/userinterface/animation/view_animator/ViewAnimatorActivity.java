/**
 * ViewAnimator用于在View切换时呈现动画效果的简便方法，主要包括下列类：
 * 1)ViewAnimator--基类，提供视图切换时的动画效果，用addView方法加载视图
 * 2)ViewSwitcher--原始视图层叠堆放在一起，由ViewFactory负责提供下一个View
 * 3)TextSwitcher/ImageSwitcher--父类是ViewSwitcher，由ViewFactory负责提供TextView/ImageView
 * 4)ViewFlipper--自动或手动翻转(切换)视图
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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import org.xottys.userinterface.animation.R;

public class ViewAnimatorActivity extends FragmentActivity {
    LayoutInflater mLayoutInflater;
    // 存放Fragment的数组
    private Class mFragmentArray[] = {ViewAnimatorFragment.class, ViewSwitcherFragment.class, ImageTextSwitcherFragment.class, ViewFlipperFragment.class};
    // 存放Tab 标签文字的数组
    private String mTextArray[] = {"    View\nAnimator", "   View\nSwitcher", "Image/Text\n  Switcher", "  View\nFlipper"};

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
        TextView textView = view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);
        return view;
    }

}
