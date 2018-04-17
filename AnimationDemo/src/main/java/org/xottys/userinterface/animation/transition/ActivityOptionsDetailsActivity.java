/**
 * 本例为演示ActivityOptions的目标Activity，主要是根据上一个Activity传入图片名称正确显示图片即可
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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;

public class ActivityOptionsDetailsActivity extends Activity {

    private static final String KEY_ID = "ViewTransitionValues:id";

    private int mImageResourceId = R.drawable.ducky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置随机背景色
        getWindow().setBackgroundDrawable(new ColorDrawable(ActivityOptionsActivity.randomColor()));
        setContentView(R.layout.optionsdetails);

        //设置titleImage
        ImageView titleImage = findViewById(R.id.titleImage);
        titleImage.setImageDrawable(getHeroDrawable());
    }

    //根据上一个Activity传入的参数（图片名称）获得图片
    private Drawable getHeroDrawable() {
        //获取上一个Activity传入的图片名称
        String name = getIntent().getStringExtra(KEY_ID);
        //根据图片名称获取图片id
        if (name != null) {
            mImageResourceId = ActivityOptionsActivity.getDrawableIdForKey(name);
        }
        //返回图片id所代表的图片资源
        return getResources().getDrawable(mImageResourceId, null);
    }

    //点击图片后返回
    public void clicked(View v) {
        finishAfterTransition();
    }
}
