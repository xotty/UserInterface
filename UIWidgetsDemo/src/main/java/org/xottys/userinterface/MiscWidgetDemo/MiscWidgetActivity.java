/**
 * 本例演示了SlidingPanelLayout、FragmentTabHost、Space、ViewStub的用法
 * 一、SlidingPanelLayout
 * 1）在xml中以<android.support.v4.widget.SlidingPaneLayout>标签定义布局文件，其中含两个子控件，第一个是左侧可推拉
 * 的视图，第二个是主内容视图。
 * 2）定义相关属性，如推拉渐变色等
 * 3）定义Sliding监听器，setPanelSlideListener
 * 4）定义推拉视图上的点击事件
 * 二、FragmentTabHost
 * 1)在xml中以<android.support.v4.app.FragmentTabHost> 标签定义布局文件,设置 android:id="@android:id/tabhost"
 * 其内有一个android:id="@android:id/tabcontent"的FrameLayout，其外要有一个放置其真实内容的FrameLayout，该部分
 * 内容在上，则Tab标签在下面，该部分内容在下面，则则Tab标签在上面
 * 2）将真实内容视图关联到TabHost上：mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
 * 3）用TabSpec给每个Tab标签按钮设置图标、文字和内容
 * 4）mTabHost.addTab将上述Tab标签按钮和对应的Fragment一起添加进Tab中
 * 三、Space
 * 在xml中以<Space>标签定义，通过width和height属性的设置将其它控件之间隔开若干空行/列
 * 四、ViewStub
 * 一个轻量级的View，不可见，没有尺寸，不绘制任何东西，因此加载或者移除时更省时。常用于延时加载以提升性能，
 * 具体用法详见Fragment1的注释
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Jan，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.MiscWidgetDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import org.xottys.userinterface.R;


public class MiscWidgetActivity extends FragmentActivity implements SlidingPaneLayout.PanelSlideListener {
    private static final String TAG = "MiscWidgetActivity";

    private FragmentTabHost mTabHost;


    private LayoutInflater mLayoutInflater;

    // 存放Fragment的数组
    private Class mFragmentArray[] = {Fragment1.class, Fragment2.class, Fragment3.class, Fragment4.class, Fragment5.class,};

    // 存放Tab 标签图片的数组
    private int mImageArray[] = {R.drawable.tab_home_btn,
            R.drawable.tab_message_btn, R.drawable.tab_selfinfo_btn,
            R.drawable.tab_square_btn, R.drawable.tab_more_btn};

    // 存放Tab 标签文字的数组
    private String mTextArray[] = {"首页", "消息", "好友", "搜索", "更多"};


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscwidget);
        SlidingPaneLayout slp = (SlidingPaneLayout) findViewById(R.id.spl_root);
        //定义推拉视图相关属性
        slp.setSliderFadeColor(Color.LTGRAY);
        slp.setCoveredFadeColor(Color.YELLOW);
        slp.setParallaxDistance(200);

        //定义Sliding监听器
        slp.setPanelSlideListener(this);

        TextView left_tv_item = (TextView) findViewById(R.id.tv_left1);

        //定义推拉视图项目上的点击监听器
        left_tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MiscWidgetActivity.this, "点击了'左侧菜单一'", Toast.LENGTH_SHORT).show();
            }
        });

        initView();
    }


    private void initView() {
        mLayoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
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
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(mTextArray[index]);

        return view;
    }

    //Sliding监听器的相关覆写方法
    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        Log.i(TAG, "onPanelSlide: ");
    }

    @Override
    public void onPanelOpened(View panel) {
        Log.i(TAG, "onPanelOpened: ");

    }

    @Override
    public void onPanelClosed(View panel) {
        Log.i(TAG, "onPanelClosed: ");
    }

}
