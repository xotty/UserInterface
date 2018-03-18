/**绝对布局，
 *极力不推荐，官方已经舍弃。
 *定义两个控件左上角坐标轴，即Android:layout_x和android:layout_y来控制位置。
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


public class AbsoluteLayoutActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.absolutelayout);

    }
}