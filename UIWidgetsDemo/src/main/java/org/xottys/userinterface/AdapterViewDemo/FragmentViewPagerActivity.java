package org.xottys.userinterface.AdapterViewDemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.xottys.userinterface.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentViewPagerActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager1);

		ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

		List<Fragment> fragments=new ArrayList<>();

		//用不同参数模拟三个不同的Fragment
		fragments.add(ViewPagerFragment.newInstance(1));
	    fragments.add(ViewPagerFragment.newInstance(2));
     	fragments.add(ViewPagerFragment.newInstance(3));

		FragAdapter adapter = new FragAdapter(getSupportFragmentManager(),fragments);

		viewPager.setAdapter(adapter);
	}

	//Fragment专用Adapter，内容需自定义
	private class FragAdapter extends FragmentPagerAdapter {
		private List<Fragment> mFragments;

		private FragAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			mFragments=fragments;
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
