/**表格布局
 * 每个<TableRow>或单独控件均为表格的一行
 * <TableRow>里面的每个控件为一列，
 * android:layout_column 可以直接设置控件在第几列
 * android:layout_span 可以设置一个控件能跨多少列
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface;

import android.app.Activity;
import android.os.Bundle;

public class TableLayoutActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablelayout);
	}
}