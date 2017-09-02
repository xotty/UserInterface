/**格子布局
 * 定义android:rowCount和android:columnCount参数即确定一个格子布局，其中的控件按照 android:orientation定义的方向依次排列
 * 若要指定某控件显示在固定的行或列，只需设置该子控件件的android:layout_row和android:layout_column属性即可
 * 设置某控件跨越多行或多列，只需将该子控件的android:layout_rowSpan或者layout_columnSpan属性设置为数值，再设置其layout_gravity属性为fill即可
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface;

import android.app.Activity;
import android.os.Bundle;

public class GridLayoutActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridlayout);
	}
}
