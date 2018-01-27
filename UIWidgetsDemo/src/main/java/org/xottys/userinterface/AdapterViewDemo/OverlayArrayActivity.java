/**
 * 本例演示了ListView的下列要点：
 * 1)在ListView上通过WindowManager.addView添加文本显示(首字母)
 * 2)onScroll中取首字母，并设置大小、位置和显示时间的长短
 * <p>
 * <br/>Copyright (C), 2017-2018, Google
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author Google Inc.
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xottys.userinterface.R;
/**
 * Another variation of the list of cheeses. In this case, we use
 * {@link AbsListView#setOnScrollListener(AbsListView.OnScrollListener) 
 * AbsListView#setOnItemScrollListener(AbsListView.OnItemScrollListener)} to display the
 * first letter of the visible range of cheeses.
 */
public class OverlayArrayActivity extends ListActivity implements ListView.OnScrollListener {

    Handler mHandler = new Handler();
    private RemoveWindow mRemoveWindow = new RemoveWindow();
    private WindowManager mWindowManager;
    private TextView mDialogText;
    private boolean mShowing;
    private boolean mReady;
    private char mPrevLetter = Character.MIN_VALUE;
    private String[] mStrings = Cheeses.sCheeseStrings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

        // Use an existing ListAdapter that will map an array of strings to TextViews
        setListAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mStrings));

        getListView().setOnScrollListener(this);

        LayoutInflater inflate = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mDialogText = (TextView) inflate.inflate(R.layout.list_position, null);
        mDialogText.setVisibility(View.INVISIBLE);

        mHandler.post(new Runnable() {

            public void run() {
                mReady = true;
                LayoutParams lp = new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.TYPE_APPLICATION,
                        LayoutParams.FLAG_NOT_TOUCHABLE | LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
                mWindowManager.addView(mDialogText, lp);
            }});
    }


    @Override
    protected void onResume() {
        super.onResume();
        mReady = true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        removeWindow();
        mReady = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWindowManager.removeView(mDialogText);
        mReady = false;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        if (mReady) {
            //取当前显示第一行的首字母
            char firstLetter = mStrings[firstVisibleItem].charAt(0);
            //view设为可显示，条件是：取得的首字母与以前不同且未显示
            if (!mShowing && firstLetter != mPrevLetter) {
                mShowing = true;
                mDialogText.setVisibility(View.VISIBLE);
            }
            //将首字母赋值给显示框
            mDialogText.setText(((Character)firstLetter).toString());
            //延迟三秒后取消显示
            mHandler.removeCallbacks(mRemoveWindow);
            mHandler.postDelayed(mRemoveWindow, 3000);

            mPrevLetter = firstLetter;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
    
    private void removeWindow() {
        if (mShowing) {
            mShowing = false;
            mDialogText.setVisibility(View.INVISIBLE);
        }
    }

    private final class RemoveWindow implements Runnable {
        public void run() {
            removeWindow();
        }
    }
}
