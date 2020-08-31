/**
 * 本例演示了通不过装载不同Fragment演示了ScrollView的各种用法
 * 1)标准的水平和垂直ScrollView
 * 2)各种ScrollBar的样式
 * 3)自定义ScrollView(用水平ScrollView模拟Gallery的效果）
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.ScrollViewDemo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.xottys.userinterface.R;

import java.util.ArrayList;
import java.util.List;

public class ScrollViewActivity extends FragmentActivity {
    private String[] titleArray = new String[]{"基本型", "ScrollBar", "自定义"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> fragments = new ArrayList<>();

        //用不同参数构造三个个不同的Fragment
        fragments.add(ScrollViewFragment.newInstance(1));
        fragments.add(ScrollViewFragment.newInstance(2));
        fragments.add(ScrollViewFragment.newInstance(3));
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(adapter);
    }

    //Fragment专用Adapter，内容需自定义
    private class FragAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;
        private String[] mTitles;

        private FragAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);

            mFragments = fragments;
            this.mTitles = titleArray;
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

        //填充标题内容
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

}
