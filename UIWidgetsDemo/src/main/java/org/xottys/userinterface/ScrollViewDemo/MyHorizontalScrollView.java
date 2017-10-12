/**
 * 本例为自定义ScrollView：
 * 1)构造器，获取屏幕宽度
 * 2)onMeasure，取得ScrollView中的唯一控件LinearLayout
 * 3)onTouchEvent，判断滑动的方向，并据此滑动和显示图片
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.ScrollViewDemo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

public class MyHorizontalScrollView extends HorizontalScrollView implements OnClickListener {
    private static final String TAG = "MyHorizontalScrollView";
    private OnImageChangeListener mOnImageChangeListener;
    private OnItemClickListener mOnClickListener;
    private float downX;    //按下时 的X坐标
    private float downY;    //按下时 的Y坐标
    // HorizontalScrollView中的子View：LinearLayout
    private LinearLayout mContainer;
    //子元素的宽度
    private int mChildWidth;
    //子元素的高度
    private int mChildHeight;
    //画廊容器中最后一张图片的下标，0～images.length-1
    private int mCurrentIndex;
    //当前第一张图片的下标,0～images.length-1
    private int mFristIndex;
    //数据适配器
    private MyHorizontalScrollViewAdapter mAdapter;
    //每屏幕最多显示的图片个数
    private int mCountOneScreen;
    //屏幕的宽度
    private int mScreenWitdh;
    //保存View与位置的键值对
    private Map<View, Integer> mViewPos = new HashMap<>();

    //已经滑动设为true，滑动结束设为false，避免一次操作产生连续滑动的负面效果
    private boolean scrolledFlag=false;

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获得屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWitdh = outMetrics.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //取得ScrollView中的唯一子View
        mContainer = (LinearLayout) getChildAt(0);
    }

/*  另外一种用onTouchListener判断滑动方向的方法
    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouch: DOWN");
                //将按下时的坐标存储
                downX = x;
                downY = y;
                Log.i(TAG, "按下时X：" + x + "按下时Y：" + y);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouch:MOVE");
                Log.i(TAG, "抬起时X：" + x + "抬起时Y：" + y);
                //获取到距离差
                float dx = x - downX;
                float dy = y - downY;
                Log.i(TAG, "dx：" + dx + "dy：" + dy);
                //防止简单误操作判断为滑动
                if (Math.abs(dx) > 5) {
                    //通过距离差判断方向
                    int orientation = getOrientation(dx, dy);
                    Log.i(TAG, "orientation: " + (char) orientation);
                    switch (orientation) {
                        case 'r':
                            //action = "右";
                            scrollToRight();
                            break;
                        case 'l':
                            //action = "左";
                            scrollToLeft();
                            break;
                        case 't':
                            //action = "上";
                            break;
                        case 'b':
                            //action = "下";
                            break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "抬起时X：" + x + "抬起时Y：" + y);
                //获取到距离差
                 dx = x - downX;
                 dy = y - downY;
                Log.i(TAG, "dx：" + dx + "dy：" + dy);
                //防止简单误操作判断为滑动
                if (Math.abs(dx) <= 5)
                {
                    if (mOnClickListener != null) {
                        //ScrollView点击时将mContainer所含的所有view背景设为白色
                        for (int i = 0; i < mContainer.getChildCount(); i++) {
                            mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
                        }
                        //从view反向获取其position
                        mOnClickListener.onClick(v, mViewPos.get(v));
                    }
                }
                    Log.i(TAG, "onTouch: UP");
                break;
        }
        return true;
    }*/
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_UP:
                  scrolledFlag=false;
            case MotionEvent.ACTION_MOVE:
                if (!scrolledFlag) {
                    Log.e(TAG, "getScrollX():"+getScrollX() );
                    scrolledFlag=true;
                    int scrollX = getScrollX();
                    if (scrollX >= 5) {
                        scrollToLeft();
                    }
                    else if (scrollX == 0) {
                        scrollToRight();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /*
     * 根据距离差判断 滑动方向
     *
     * @param dx X轴的距离差
     * @param dy Y轴的距离差
     * @return 滑动的方向
     */
    private int getOrientation(float dx, float dy) {
            if (Math.abs(dx) > Math.abs(dy)) {
                //X轴移动
                return dx > 0 ? 'r' : 'l';
            } else {
                //Y轴移动
                return dy > 0 ? 'b' : 't';
            }
    }

    //OnClickListener接口的实现
    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick:");

        if (mOnClickListener != null) {
            //ScrollView点击时将mContainer所含的所有view背景设为白色
            for (int i = 0; i < mContainer.getChildCount(); i++) {
                mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
            }
            //从view反向获取其position
            mOnClickListener.onClick(v, mViewPos.get(v));
        }
    }

    //向右滑动，先加载前一张图片，然后ScrollView向右移动一张图片的宽度
    public void scrollToRight() {
        loadPreImg();
        scrollBy(mChildWidth * -1, 0);
    }
    //向左滑动，先加载后一张图片，然后ScrollView向左移动一张图片的宽度
    public void scrollToLeft() {
        loadNextImg();
        scrollBy(mChildWidth, 0);
    }

    //加载后一张图片
    protected void loadNextImg() {
        //
        if (mCurrentIndex != mAdapter.getCount() - 1) {
            //移除第一张图片
            mViewPos.remove(mContainer.getChildAt(0));
            mContainer.removeViewAt(0);

            //获取下一张图片，将其加入容器中，也将其放入map中
            mCurrentIndex++;
            View view = mAdapter.getView(mCurrentIndex, null, mContainer);
            mContainer.addView(view);
            mViewPos.put(view, mCurrentIndex);

            view.setOnClickListener(this);
            //view.setOnTouchListener(this);

            //当前第一张图片下标+1
            mFristIndex++;

            //如果设置了滚动监听则触发
            if (mOnImageChangeListener != null) {
                notifyCurrentImgChanged();
            }
            Log.i(TAG, "loadNextImg: " + mCurrentIndex + "~~" + mFristIndex);
        }
    }

    // 加载前一张图片
    protected void loadPreImg() {
        //如果当前已经是第一张，则返回
        if (mFristIndex == 0)
            return;

        else {
            //获得当前应该显示为第一张图片的下标
            int index = mFristIndex - 1;
            mContainer = (LinearLayout) getChildAt(0);
            //移除最后一张
            int oldViewPos = mCurrentIndex;
            mViewPos.remove(mContainer.getChildAt(oldViewPos));
            //mCountOneScreen - 1 是容器中最后一张的下标
            mContainer.removeViewAt(mCountOneScreen - 1);

            //将要显示的View放入第一个位置
            View view = mAdapter.getView(index, null, mContainer);
            mViewPos.put(view, index);
            mContainer.addView(view, 0);

            view.setOnClickListener(this);
            //view.setOnTouchListener(this);

            //当前位置-1，当前第一个显示的下标-1
            mCurrentIndex--;
            mFristIndex--;
            //如果设置了滚动监听则触发
            if (mOnImageChangeListener != null) {
                notifyCurrentImgChanged();
            }

            Log.i(TAG, "loadPreImg: " + mCurrentIndex + "~~" + mFristIndex);
        }
    }

    //滚动监听触发方法
    public void notifyCurrentImgChanged() {
        //先清除所有的背景色（设为白色），点击时会设置为蓝色
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        //将当前第一张图片及其位置传递出去
        mOnImageChangeListener.onCurrentImgChanged(mFristIndex, mContainer.getChildAt(0));

    }


    // 初始化数据，设置数据适配器
    public void setAdapter(MyHorizontalScrollViewAdapter mAdapter) {
        this.mAdapter = mAdapter;
        // 获得适配器中第一个View
        final View view = mAdapter.getView(0, null, null);
        // 强制计算当前View的宽和高
        if (mChildWidth == 0 && mChildHeight == 0) {
            int w = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
            view.measure(w, h);
            mChildHeight = view.getMeasuredHeight();
            mChildWidth = view.getMeasuredWidth();
            Log.i(TAG, view.getMeasuredWidth() + "," + view.getMeasuredHeight());
            // 计算每次加载多少张View
            mCountOneScreen = mScreenWitdh / mChildWidth + 1;
            if (mCountOneScreen > mAdapter.getCount())
                mCountOneScreen = mAdapter.getCount();

            Log.i(TAG, "mCountOneScreen = " + mCountOneScreen
                    + " ,mScreenWitdh = " + mScreenWitdh + " ,mChildWidth = " + mChildWidth + "~~~" + mScreenWitdh / mChildWidth);
        }
        //初始化第一屏幕的元素
        initFirstScreenChildren(mCountOneScreen);
    }

    /**
     * 加载第一屏的View
     *
     * @param mCountOneScreen 一屏上显示的图片数量
     */
    public void initFirstScreenChildren(int mCountOneScreen) {
        mContainer = (LinearLayout) getChildAt(0);
        mContainer.removeAllViews();
        mViewPos.clear();
        mFristIndex = 0;
        //将一屏的图片全部加载到容器中，mCurrentIndex指向容器最后一张
        for (int i = 0; i < mCountOneScreen; i++) {
            View view = mAdapter.getView(i, null, mContainer);
            view.setOnClickListener(this);
            //view.setOnTouchListener(this);
            mContainer.addView(view);
            mViewPos.put(view, i);
        }
        mCurrentIndex = mCountOneScreen - 1;

        if (mOnImageChangeListener != null) {
            notifyCurrentImgChanged();
        }
    }
    //设置图片点击时的回调接口
    public void setOnItemClickListener(OnItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    //设置图片滚动时的回调接口
    public void setOnImageChangeListener(
            OnImageChangeListener mOnImageChangeListener) {
        this.mOnImageChangeListener = mOnImageChangeListener;
    }


    //图片滚动时的回调接口
    public interface OnImageChangeListener {
        void onCurrentImgChanged(int position, View viewIndicator);
    }

    // 图片点击时的回调接口
    public interface OnItemClickListener {
        void onClick(View view, int pos);
    }

}
