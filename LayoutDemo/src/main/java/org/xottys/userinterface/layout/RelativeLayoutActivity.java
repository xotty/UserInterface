/**相对布局
 *如果没有定义左右，那么默认在左边，如果没有定义上下，默认在上边。
 *相同位置，新定义的元素会覆盖旧的元素。例：1被2覆盖了。
 *4只定义了在父元素的下部，左右没有定义，于是默认就在左边了。
 *android:layout_below,在某元素的下部并不意味着就一定是紧随某元素，只是在下部的默认位置。例如：5是在3的下部，但是是在下部的默认左边。
 *6为下边缘对齐3，7为marginLeft=150dp。
 *8为多个属性共同定义的结果。首先是在3的右部，然后是垂直居中，然后marginLeft=100dp得到最后位置。
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

public class RelativeLayoutActivity extends Activity
{
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.relativelayout);
  }
}