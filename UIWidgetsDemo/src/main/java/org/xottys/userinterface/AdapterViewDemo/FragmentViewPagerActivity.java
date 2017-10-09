/**
 * 本例演示了装载Fragment的ViewPager的基本用法：
 * 1)Activity必须继承FragmentActivity
 * 2)准备Fragment数组：List<Fragment>,这是每一页要显示的内容
 * 3)自定义FragmentPagerAdapter或FragmentStatePagerAdapter子类，覆写其两个基本方法，
 * 在getItem方法中，将Fragment添加到ViewPager中
 * 4)viewPager.setAdapter
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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import org.xottys.userinterface.R;

public class FragmentViewPagerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager1);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> fragments = new ArrayList<>();

        //用不同参数模拟三个不同的Fragment
        fragments.add(ViewPagerFragment.newInstance(1));
        fragments.add(ViewPagerFragment.newInstance(2));
        fragments.add(ViewPagerFragment.newInstance(3));
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
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
}
