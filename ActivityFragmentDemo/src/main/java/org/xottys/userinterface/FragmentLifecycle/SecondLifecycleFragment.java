/**
 * 第二个Fragment，大部分应用程序都应该至少为每个fragment实现这三个方法
 * 1）onCreate()
 * 2）onCreateView()
 * 3）onPause()
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.FragmentLifecycle;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.ContentValues.TAG;


public class SecondLifecycleFragment extends Fragment
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "----Fragment_2  onCreate----");
	}

	@Override
	public View onCreateView(LayoutInflater inflater
		, ViewGroup container, Bundle data)
	{
		TextView tv = new TextView(getActivity());
		tv.setText("2");
		tv.setTextColor(Color.WHITE);
		tv.setTextSize(90);
		tv.setGravity(Gravity.CENTER);
		return tv;
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "----Fragment_2  onPause----");
	}
}
