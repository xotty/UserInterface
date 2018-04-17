/**
 * 本例为演示LayoutAnimationController的宿主程序，分别演示了：
 * 1）纯代码方式定义和加载LayoutAnimationController
 * 2）完全xml方式定义和加载LayoutAnimationController
 * 3）xml方式定义LayoutAnimationController，代码和xml中分别加载之
 * 4）设置/移除监听器(可选)
 * 5）
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutAnimationControllerActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.transition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.xottys.userinterface.animation.R;

public class LayoutAnimationControllerActivity extends Activity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutanimationcontroller);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.List_Cascade:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimationControllerActivity1.class);
                break;
            case R.id.Reverse_Order:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimationControllerActivity2.class);
                break;
            case R.id.Wave_Scale:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimationControllerActivity4.class);
                break;
            case R.id.Nested_Animation:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimationControllerActivity3.class);
                break;
            case R.id.Randomize:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimationControllerActivity5.class);
                break;
        }
        startActivity(intent);
    }
}
