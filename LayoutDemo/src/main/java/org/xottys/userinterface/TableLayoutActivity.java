/**��񲼾�
 * ÿ��<TableRow>�򵥶��ؼ���Ϊ����һ��
 * <TableRow>�����ÿ���ؼ�Ϊһ�У�
 * android:layout_column ����ֱ�����ÿؼ��ڵڼ���
 * android:layout_span ��������һ���ؼ��ܿ������
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Aug��2017
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