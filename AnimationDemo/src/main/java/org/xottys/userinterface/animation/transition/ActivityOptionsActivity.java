/**
 * 本例演示了Activity转场动画的方法之二：ActivityOptions，具体使用步骤如下：
 * 1）xml中指定允许:windowActivityTransitions和windowContentTransitions的Theme
 * <style name="AppTheme" parent="Theme.AppCompat">
 * <item name="android:windowActivityTransitions">true</item>
 * <item name="android:windowContentTransitions">true</item>
 * </style>
 * 或者在setContentView之前调用
 * getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
 * getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
 * <p>
 * 通常还需要在style文件中指定窗口和共享元素的进入/退出动画。
 * <p>
 * 2）定义ActivityOptions对象
 * 根据要求的动画效果采用ActivityOptions.makeXXX等八大静态方法之一来生成对象，其中
 * makeBasic()：无特殊动画
 * makeTaskLaunchBehind()：启动到后台，无动画效果
 * makeCustomAnimation：用Animation指定进出动画
 * makeSceneTransitionAnimation：用Transition指定进出动画
 * 其它：用View的变化指定进出动画
 * <p>
 * 3）用ActivityOptions启动新的Activity
 * startActivity(intent, activityOptions.toBundle());
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.transition;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xottys.userinterface.animation.R;

public class ActivityOptionsActivity extends Activity implements View.OnClickListener {

    public static final int[] DRAWABLES = {
            R.drawable.ball,
            R.drawable.ducky,
            R.drawable.mug,
            R.drawable.scissors,
    };
    public static final int[] IDS = {
            R.id.ball,
            R.id.ducky,
            R.id.mug,
            R.id.scissors,
    };
    public static final String[] NAMES = {
            "ball",
            "ducky",
            "mug",
            "scissors",
    };
    private static final String TAG = "ActivityTransition";
    private static final String KEY_ID = "ViewTransitionValues:id";
    ActivityOptions opts;
    Intent intent;
    private ImageView duck;

    //用图片名称获取图片所在的Imageview
    public static int getIdForKey(String id) {
        return IDS[getIndexForKey(id)];
    }

    //用图片名称获取图片id
    public static int getDrawableIdForKey(String id) {
        return DRAWABLES[getIndexForKey(id)];
    }

    //用图片名称获取图片序号
    public static int getIndexForKey(String id) {
        for (int i = 0; i < NAMES.length; i++) {
            String name = NAMES[i];
            if (name.equals(id)) {
                return i;
            }
        }
        return 0;
    }

    //为背景颜色随机作准备
    public static @ColorInt
    int randomColor() {
        int red = (int) (Math.random() * 128);
        int green = (int) (Math.random() * 128);
        int blue = (int) (Math.random() * 128);
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }

    //为说明文字颜色设为互补色作准备
    private int getComplementaryColor(@ColorInt int mColor) {
        int red = 255 - Color.red(mColor);
        int green = 255 - Color.green(mColor);
        int blue = 255 - Color.blue(mColor);
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置背景色
        int color = randomColor();
        getWindow().setBackgroundDrawable(new ColorDrawable(color));

        setContentView(R.layout.optionsactivity);
        //为Button文本设置颜色，与背景色一致
        ((Button) findViewById(R.id.make_custom)).setTextColor(color);
        ((Button) findViewById(R.id.make_basic)).setTextColor(color);
        ((Button) findViewById(R.id.make_clip_reveal)).setTextColor(color);
        ((Button) findViewById(R.id.make_scale_up)).setTextColor(color);
        ((Button) findViewById(R.id.make_thumbnail_scaleup)).setTextColor(color);
        ((Button) findViewById(R.id.no_animation)).setTextColor(color);

        //将make_scene_transition文本设置背景的补色
        TextView tv = findViewById(R.id.make_scene_transition);
        tv.setTextColor(getComplementaryColor(color));

        duck = findViewById(R.id.ducky);

        intent = new Intent(ActivityOptionsActivity.this, ActivityOptionsDetailsActivity.class);

    }

    //点击图片
    public void clicked(View v) {
        ImageView mHero = (ImageView) v;

        //点击图片的TransitionName传入新Activity
        intent.putExtra(KEY_ID, v.getTransitionName());

        //将mHero共享元素放入activityOptions
        ActivityOptions activityOptions
                = ActivityOptions.makeSceneTransitionAnimation(this, mHero, "hero");

        //跳转到新Activity
        startActivity(intent, activityOptions.toBundle());
    }

    //点击按钮
    @Override
    public void onClick(View v) {
        //固定将ducky传入新的Activity
        intent.putExtra(KEY_ID, NAMES[1]);

        switch (v.getId()) {
            case R.id.make_custom:
                //本Activity以zoom_exit动画退出，同时下一个Activity以zoom_enter动画进入
                opts = ActivityOptions.makeCustomAnimation(ActivityOptionsActivity.this,
                        R.anim.zoom_enter, R.anim.zoom_exit);
                Log.i(TAG, "Starting makeCustomAnimation...");
                break;
            //本Activity以duck的(0,0)为起点，duck的整个大小为初始值，通过放大动画显示下一个Activity
            case R.id.make_scale_up:
                opts = ActivityOptions.makeScaleUpAnimation(
                        duck, 0, 0, duck.getMeasuredWidth(), duck.getMeasuredHeight());
                Log.i(TAG, "Starting scale-up animation(makeScaleUpAnimation)...");
                break;
            //本Activity以duck的(0,0)为起点，动画放大bm（这里也是duck）显示下一个Activity
            case R.id.make_thumbnail_scaleup:
                Log.i(TAG, "Starting thumbnail-zoom animation...");
                // Create a thumbnail animation.  We are going to build our thumbnail
                // just from the view that was pressed.  We make sure the view is
                // not selected, because by the time the animation starts we will
                // have finished with the selection of the tap.
                duck.setDrawingCacheEnabled(true);
                Bitmap bm = duck.getDrawingCache();
                opts = ActivityOptions.makeThumbnailScaleUpAnimation(
                        duck, bm, 0, 0);
                break;
            //本Activity以duck的(0,0)为起点，0为初始值，通过圆形放大动画显示下一个Activity
            case R.id.make_clip_reveal:
                Log.i(TAG, "Starting clip reveal animation(makeClipRevealAnimation)...");
                opts = ActivityOptions.makeClipRevealAnimation(duck, 0, 0, 0, 0);
                break;
            //不显示特定动画
            case R.id.make_basic:
                Log.i(TAG, "Starting basic animation(makeBasic)...");
                opts = ActivityOptions.makeBasic();
                break;
            //无任何动画效果
            case R.id.no_animation:
                Log.i(TAG, "Starting no animation transition...");
                startActivity(intent);
                overridePendingTransition(0, 0);
                return;

        }
        //跳转下一个Activity
        startActivity(intent, opts.toBundle());

        if (v.getId() == R.id.make_thumbnail_scaleup) {
            v.setDrawingCacheEnabled(false);
        }
    }


}
