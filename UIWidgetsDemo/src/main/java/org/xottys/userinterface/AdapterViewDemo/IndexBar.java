/**
 * 本例为自定义视图：侧边栏字母索引
 * 1)View：每个字母为一个TextView，将所有字母TextView垂直排列在一个LinearLayout中
 * 2)Event：通过LinearLayout的OnTouch事件，根据y坐标计算得到点击的字母，通过自定义接口传递出去
 * 3)TypedArray：通过它获取Indexbar视图的相关属性
 * <p>
 * <br/>Copyright (C), 2017-2018, wcy
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author wcy
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class IndexBar extends LinearLayout implements View.OnTouchListener {
    private static final String[] INDEXES = new String[]{"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final int TOUCHED_BACKGROUND_COLOR = 0x40000000;
    private OnIndexChangedListener mListener;

    public void setOnIndexChangedListener(OnIndexChangedListener listener) {
        mListener = listener;
    }

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }
    //视图定义
    private void init(AttributeSet attrs) {
        //获取视图的颜色和尺寸
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.IndexBar);
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        int pos= (int) (12 * fontScale + 0.5f);
        float indexTextSize = ta.getDimension(R.styleable.IndexBar_indexTextSize,pos);
        int indexTextColor = ta.getColor(R.styleable.IndexBar_indexTextColor, 0xFF616161);
        ta.recycle();
        setOnTouchListener(this);
        //每个字母为一个TextView，将所有字母TextView垂直排列在一个LinearLayout中
        setOrientation(VERTICAL);
        for (String index : INDEXES) {
            TextView text = new TextView(getContext());
            text.setText(index);
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, indexTextSize);
            text.setTextColor(indexTextColor);
            text.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            text.setLayoutParams(params);
            addView(text);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(TOUCHED_BACKGROUND_COLOR);
                handle(v, event);
                return true;
            case MotionEvent.ACTION_MOVE:
                handle(v, event);
                return true;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                handle(v, event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void handle(View v, MotionEvent event) {
        int y = (int) event.getY();
        int height = v.getHeight();
        //根据y坐标计算字母位置
        int position = INDEXES.length * y / height;
        if (position < 0) {
            position = 0;
        } else if (position >= INDEXES.length) {
            position = INDEXES.length - 1;
        }

        String index = INDEXES[position];
        boolean showIndicator = (event.getAction() != MotionEvent.ACTION_UP);
        if (mListener != null) {
            //将点击字母递出去，动作为Up时showIndicator=false，屏幕中央显示的字母会消失
            mListener.onIndexChanged(index, showIndicator);
        }
    }

    public interface OnIndexChangedListener {
        void onIndexChanged(String index, boolean showIndicator);
    }
}
