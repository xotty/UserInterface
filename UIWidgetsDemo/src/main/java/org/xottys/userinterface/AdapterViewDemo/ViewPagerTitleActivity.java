/**
 * 本例演示了带标题栏的ViewPager的基本用法：
 * 1)在xml布局文件中增加:<android.support.v4.view.PagerTitleStrip/>或android.support.v4.view.PagerTabStrip/>
 * 2)在自定义PagerAdapter子类中增加一个getPageTitle方法，其中填充标题文本
 * 3)PagerTitleStrip只显示，PagerTabStrip可点击；二者位置可以是Top或Bottom
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xottys.userinterface.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerTitleActivity extends Activity {

    private String[] titleArray = new String[]{"漓江", "桥", "双塔", "水", "象鼻山"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2);

        ViewPager viewPager1 = (ViewPager) findViewById(R.id.viewpager1);
        ViewPager viewPager2 = (ViewPager) findViewById(R.id.viewpager2);

        List<View> viewList1 = new ArrayList<>();
        List<View> viewList2 = new ArrayList<>();

        // 定义一个访问图片的数组
        int[] images = new int[]{
                R.drawable.lijiang,
                R.drawable.qiao,
                R.drawable.shuangta,
                R.drawable.shui,
                R.drawable.xiangbi,
        };
        // 定义一个访问ImageView数组，将i️mages数组中的内容分别放入其中
        ImageView[] imageViews1 = new ImageView[images.length];
        for (int i = 0; i < imageViews1.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setImageResource(images[i]);
            imageViews1[i] = imageView;
            // 将要分页显示的View(这里是ImageView)装入数组中
            viewList1.add(imageViews1[i]);
        }

        ImageView[] imageViews2 = new ImageView[images.length];
        for (int i = 0; i < imageViews2.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setImageResource(images[i]);
            imageViews2[i] = imageView;
            // 将要分页显示的View(这里是ImageView)装入数组中
            viewList2.add(imageViews2[i]);
        }
        viewPager1.setAdapter(new MyPagerAdapter(viewList1, titleArray));
        viewPager2.setAdapter(new MyPagerAdapter(viewList2, titleArray));
    }

    //Viewpager专用Adapter，内容需自定义
    private class MyPagerAdapter extends PagerAdapter {

        private List<View> mViewList;
        private String[] mTitles;

        //也可以重写构造器，传入Context，然后在PagerAdapter
        //中初始话Layout布局，感觉这种通用一些
        private MyPagerAdapter(List<View> mViewList, String[] mTitles) {
            this.mViewList = mViewList;
            this.mTitles = mTitles;
        }

        //判断instantiateItem(ViewGroup, int)函数所返回来的Key与一个页面视图是否是代表的同一个视图
        //即它俩是否是对应的，对应的表示同一个View
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
                /*也可以根据传来的key，找到view,判断与传来的参数View arg0是不是同一个视图
				return arg0 == viewList.get((int)Integer.parseInt(arg1.toString()));*/
        }

        //返回要滑动的VIew的个数
        @Override
        public int getCount() {
            return mViewList.size();
        }

        //从当前ViewPager中删除指定位置（position）的View;
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(mViewList.get(position));
        }

        //第一：将当前视图添加到ViewPager中，第二：返回当前视图或其Key
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        //填充标题内容
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}

