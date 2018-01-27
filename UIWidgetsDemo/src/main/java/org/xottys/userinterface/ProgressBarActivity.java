/**本例演示了ProgressBar及其子类的基本用法
 * 1)ProgressBar，分环形和水平两种，前者只能展示为一个循环的动画，后者才能显示任务执行的具体进度值
 * 2)SeekBar
 * 3)RatingBar
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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class ProgressBarActivity extends Activity {
    private static final String TAG = "ProgressBarActivity";
    int hasData = 0;

    // 记录ProgressBar的完成进度
    int status = 0;

    ProgressBar bar1, bar2;
    TextView tv1;

    // 负责更新的进度的Handler
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 表明消息是由该程序发送的
            if (msg.what == 0x111) {
                tv1.setText("任务完成的进度:" + status + "%");
                bar1.setProgress(status);
                bar2.setProgress(status);
            }
        }
    };

    SeekBar mSeekBar1, mSeekBar2;
    TextView mProgressText, mTrackingText;
    //拖动条滑块拖动是调用本回调方法
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
            mProgressText.setText(progress + "     " +
                    getString(R.string.seekbar_from_touch) + "=" + fromTouch);
            mSeekBar2.setProgress(progress);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            mTrackingText.setText(getString(R.string.seekbar_tracking_on));
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            mTrackingText.setText(getString(R.string.seekbar_tracking_off));
        }
    };

    RatingBar mSmallRatingBar;
    RatingBar mIndicatorRatingBar;
    TextView mRatingText;
    //评分条拖动时调用本回调方法
    RatingBar.OnRatingBarChangeListener onRatingBarChangeListener = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            final int numStars = ratingBar.getNumStars();
            mRatingText.setText(
                    getString(R.string.ratingbar_rating) + " " + rating + "/" + numStars);

            // Since this rating bar is updated to reflect any of the other rating
            // bars, we should update it to the current values.
            if (mIndicatorRatingBar.getNumStars() != numStars) {
                mIndicatorRatingBar.setNumStars(numStars);
                mSmallRatingBar.setNumStars(numStars);
            }
            if (mIndicatorRatingBar.getRating() != rating) {
                mIndicatorRatingBar.setRating(rating);
                mSmallRatingBar.setRating(rating);
            }
            final float ratingBarStepSize = ratingBar.getStepSize();
            if (mIndicatorRatingBar.getStepSize() != ratingBarStepSize) {
                mIndicatorRatingBar.setStepSize(ratingBarStepSize);
                mSmallRatingBar.setStepSize(ratingBarStepSize);
            }
        }

    };

    // 该程序模拟填充长度为100的数组
    private int[] data = new int[100];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        tv1 = (TextView) findViewById(R.id.tv1);

        //第一行的环形ProgressBar从左到右依次为Large、Normal、Small、SmallInverse、Inverse和LargeInverse
        //第二行为水平标准进度条，第三行为自定义样式水平进度条
        bar1= (ProgressBar) findViewById(R.id.bar1);
        bar2 = (ProgressBar) findViewById(R.id.bar2);

        // 启动线程来执行任务，每循环一次status加1
        new Thread() {
            public void run() {
                while (status < 100) {
                    // 获取耗时操作的完成百分比
                    status = doWork();
                    // 发送消息
                    mHandler.sendEmptyMessage(0x111);
                }
            }
        }.start();

        //第一行搜索条是标准模式，第二行是自定义样式
        mSeekBar1 = (SeekBar) findViewById(R.id.seek1);
        mSeekBar1.setOnSeekBarChangeListener(seekBarChangeListener);
        mSeekBar2 = (SeekBar) findViewById(R.id.seek2);

        mProgressText = (TextView) findViewById(R.id.progress);
        mTrackingText = (TextView) findViewById(R.id.tracking);
        mRatingText = (TextView) findViewById(R.id.rating);

        //这两个评分条是只能显示的，不能与用户交互的
        mSmallRatingBar = (RatingBar) findViewById(R.id.small_ratingbar);
        mIndicatorRatingBar = (RatingBar) findViewById(R.id.indicator_ratingbar);

        //这两个评分条是可以与用户交互的，前两行为标准样式，第三行为自定义样式
        ((RatingBar) findViewById(R.id.ratingbar1)).setOnRatingBarChangeListener(onRatingBarChangeListener);
        ((RatingBar) findViewById(R.id.ratingbar2)).setOnRatingBarChangeListener(onRatingBarChangeListener);
    }

    // 模拟一个耗时的操作
    public int doWork() {
        // 为数组元素赋值，hasdata加1
        int pos = hasData + 1;
        data[pos] = (int) (Math.random() * 100);
        Log.i(TAG, "" + data[pos]);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hasData;
    }
}
