/**
 * 第二个用于演示Activity生命周期的Activity
 * 没有xml布局文件，直接显示一个TextView控件
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.ActivityLifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LifecycleSecondActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("对话框风格的LifecycleSecondActivity");
		setContentView(tv);
	}
}
