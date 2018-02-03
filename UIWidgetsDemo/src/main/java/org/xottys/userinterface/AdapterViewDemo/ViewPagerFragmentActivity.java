/**
 * 本例演示了装载Fragment的ViewPager的基本用法：
 * 1)Activity必须继承FragmentActivity
 * 2)准备Fragment数组：List<Fragment>,这是每一页要显示的内容
 * 3)自定义FragmentPagerAdapter或FragmentStatePagerAdapter子类，覆写其两个基本方法，
 * 在getItem方法中，将Fragment添加到ViewPager中
 * 4)viewPager.setAdapter
 * 额外增加了常用的圆点表示方法，在滑动事件OnPageChangeListener中处理即可
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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.xottys.userinterface.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager3);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> fragments = new ArrayList<>();

        //用不同参数模拟五个个不同的Fragment
        fragments.add(ViewPagerFragment.newInstance(1));
        fragments.add(ViewPagerFragment.newInstance(2));
        fragments.add(ViewPagerFragment.newInstance(3));
        fragments.add(ViewPagerFragment.newInstance(4));
        fragments.add(ViewPagerFragment.newInstance(5));
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(adapter);

        //设置圆点显示
        LinearLayout dots = (LinearLayout) findViewById(R.id.dots);
        viewPager.addOnPageChangeListener(new IndicatorChangeListener(this, dots, fragments.size()));
    }

    //Fragment专用Adapter，内容需自定义
    private class FragAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;
        private FragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        //返回当前要显示的fragment
        @Override
        public Fragment getItem(int arg0) {
            return mFragments.get(arg0);
        }

        //返回用于滑动的fragment总数
        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    //在页面滑动时改变圆点的显示
    private class IndicatorChangeListener implements ViewPager.OnPageChangeListener {

        private int size;
        private int img1 = R.drawable.dot_solid, img2 = R.drawable.dot_hollow;
        private int imgSize = 20;
        private List<ImageView> dotViewLists = new ArrayList<>();

        //在构造器中设置初始圆点图
        private IndicatorChangeListener(Context context, LinearLayout dotLayout, int size) {

            this.size = size;

            for (int i = 0; i < size; i++) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                //为小圆点左右添加间距
                params.leftMargin = 15;
                params.rightMargin = 15;
                //手动给小圆点一个大小
                params.height = imgSize;
                params.width = imgSize;
                if (i == 0) {
                    imageView.setBackgroundResource(img1);
                } else {
                    imageView.setBackgroundResource(img2);
                }
                //为LinearLayout添加ImageView
                dotLayout.addView(imageView, params);
                dotViewLists.add(imageView);
            }

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //当滑动完成式=时改变圆点状态
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < size; i++) {
                //选中的页面改变小圆点为选中状态，反之为未选中
                if ((position % size) == i) {
                    ( dotViewLists.get(i)).setBackgroundResource(img1);
                } else {
                    ( dotViewLists.get(i)).setBackgroundResource(img2);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
