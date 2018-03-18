/**线性布局
 *一个竖向的大LinearLayout里摆放了几个控件和一个小的横向LinearLayout
 *横向LinearLayout又摆放了介个控件
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.layout;

import android.app.Activity;
import android.os.Bundle;

public class LinearLayoutActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linearlayout);
	}
}