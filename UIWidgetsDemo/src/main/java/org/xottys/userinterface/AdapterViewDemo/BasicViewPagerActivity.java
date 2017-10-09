/**
 * 本例演示了ViewPager的基本用法：
 * 1)准备视图数组：List<View>,这是每一页要显示的内容
 * 2)自定义PagerAdapter子类，覆写其相关方法，在instantiateItem方法中，将View添加到ViewPager中
 * 3)viewPager.setAdapter
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 * @author xottys@163.com
 * @version 1.0
 */
 package org.xottys.userinterface.AdapterViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import org.xottys.userinterface.R;

public class BasicViewPagerActivity extends Activity {
	private static final String TAG = "BasicViewPagerActivity";
	private List<View> viewList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager1);

		ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewList = new ArrayList<>();

		// 定义一个访问图片的数组
		int[] images = new int[]{
				R.drawable.lijiang,
				R.drawable.qiao,
				R.drawable.shuangta,
				R.drawable.shui,
				R.drawable.xiangbi,
		};
		// 定义一个访问ImageView数组，将i️mages数组中的内容分别放入其中
		ImageView[] imageViews = new ImageView[images.length];
		for (int i = 0; i < imageViews.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			imageView.setImageResource(images[i]);
			imageViews[i] = imageView;
			// 将要分页显示的View(这里是ImageView)装入数组中
			viewList.add(imageViews[i]);
		}

		//Viewpager专用Adapter，内容需自定义
		PagerAdapter pagerAdapter = new PagerAdapter() {
			//判断instantiateItem(ViewGroup, int)函数所返回来的Key与一个页面视图是否是代表的同一个视图
			// 即它俩是否是对应的，对应的表示同一个View
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
				/*也可以根据传来的key，找到view,判断与传来的参数View arg0是不是同一个视图
				return arg0 == viewList.get((int)Integer.parseInt(arg1.toString()));*/
			}

			//返回要滑动的VIew的个数
			@Override
			public int getCount() {
				return viewList.size();
			}

			//从当前ViewPager中删除指定位置（position）的View;
			@Override
			public void destroyItem(ViewGroup container, int position,
									Object object) {
				container.removeView(viewList.get(position));
			}

			//第一：将当前视图添加到ViewPager中，第二：返回当前视图或其Key
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewList.get(position));

				return viewList.get(position);

				/*也可以把当前新增视图的位置（position）作为Key传过去
				  return position; */
			}
		};
		viewPager.setAdapter(pagerAdapter);

		//用代码设置滚动页面
		viewPager.setCurrentItem(1, true);

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			//页面滚动时调用此方法
			@Override
			public void onPageScrolled ( int position, float positionOffset,
			int positionOffsetPixels){
				Log.i(TAG, "onPageScrolled: "+position+"～～～"+positionOffset);
			}
			//页面跳转完后调用此方法
			@Override
			public void onPageSelected ( int position){
				Log.i(TAG, "onPageSelected: "+position);
			}

			//页面状态改变时调用此方法，状态如下：
  			//state：0:SCROLL_STATE_IDLE(什么都没做)、 1:SCROLL_STATE_DRAGGING(正在滑动)、
			//2:SCROLL_STATE_SETTLING(滑动完毕)
			@Override
			public void onPageScrollStateChanged ( int state){
				Log.i(TAG, "onPageScrollStateChanged: "+state);
			}
		});
	}

}
