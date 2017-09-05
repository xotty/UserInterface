/**
 * 本例演示自定义Prefernce的基本用法：
 * 1）在构造器中关联自定义的布局文件setLayoutResource()
 * 2）onBindView中可以给自定义控件赋值
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Sep，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.CommonlyUsedActivities;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import org.xottys.userinterface.R;

public class MyPreference extends Preference {
    static Context ctx;
    public MyPreference(Context context, AttributeSet attrs) {
         this(context, attrs, 0);
    }

    public MyPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(R.layout.preference_custom);
        ctx=context;
    }

    @Override
    public void onBindView(View view) {
        super.onBindView(view);
        ImageView mImageView = (ImageView)view.findViewById(R.id.icon2);
        mImageView.setImageDrawable(ctx.getResources().getDrawable(android.R.drawable.ic_menu_info_details));
    }
}

