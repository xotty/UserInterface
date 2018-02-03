/**自定义NumberPicker，实现自定义字体大小、颜色和分割线颜色
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.DialogDemo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import org.xottys.userinterface.R;

import java.lang.reflect.Field;
public class MyNumberPicker extends NumberPicker {
    public MyNumberPicker(Context context) {
        super(context);
    }

    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    //设置滚动文本的大小和颜色
    private void updateView(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setTextColor(getResources().getColor(R.color.red, null));
            ((EditText) view).setTextSize(20);
        }
    }

    //用反射实现为滚动框分隔线设置颜色
    public void setNumberPickerDividerColor() {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(this, new ColorDrawable(ContextCompat.getColor(getContext(), R.color.blue)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
