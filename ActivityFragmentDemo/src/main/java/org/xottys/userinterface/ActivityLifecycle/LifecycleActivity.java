/**
 * 本例演示了除Activity的基本用法和生命周期(Activity在4~5之间活动在前台；2～6之间保持在屏幕上,可能不可操作或不可见)：
 * 1) onCreate()........在创建时执行
 * 2) onRestart().......仅在Activity被stop后再次恢复用户可见时执行
 * 3) onStart().........在启动后即将在屏幕上呈现时执行
 * 4) onResume()........在Activity返回到前台可见时执行
 * 5) onPause().........在界面被部分覆盖或透明覆盖时执行,失去焦点,数据会保持,和Windows Manager保持连接
 * 6) onStop()..........在安全不可见时执行，数据会保持，没有和Windows Manager相连接
 * 7) onDestroy().......在销毁时会执行，应在其中释放所有资源
 * 同时演示了用代码定义和添加控件的方法
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LifecycleActivity
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.ActivityLifecycle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class LifecycleActivity extends Activity {
    private final String TAG = "LifecycleActivity";
   
    Button addBT, startBT1,startBT2;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitylifecycle);

        startBT1 = (Button) findViewById(R.id.bt1);
        startBT2 = (Button) findViewById(R.id.bt2);
        addBT = (Button) findViewById(R.id.bt3);

        tv = (TextView) findViewById(R.id.tv);

        //用代码自定义一个LinearLayout并设置相关参数
        final LinearLayout myLayout = new LinearLayout(this); // 线性布局方式
        myLayout.setOrientation(LinearLayout.VERTICAL); //
        myLayout.setBackgroundColor(Color.LTGRAY);
        myLayout.setGravity(Gravity.CENTER);        //将其中的myButton放在正中间
        final LinearLayout.LayoutParams LP_MM = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        myLayout.setLayoutParams(LP_MM);

        //用代码自定义一个Button
        final Button myButton = new Button(this);
        myButton.setText("退出");
        myButton.setBackgroundColor(Color.GRAY);
        myButton.setTextColor(Color.WHITE);
        myButton.setTextSize(24);
        myButton.setGravity(Gravity.CENTER);
        //为这个自定义的Button准备和设置Layout参数
        final LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        myButton.setLayoutParams(LP_WW);
        
        //从xml布局文件中获取LinearLayout
       final LinearLayout rootLinearLayout = (LinearLayout) findViewById(R.id.rootview);
        /*另一种获取方法
        LayoutInflater inflater = LayoutInflater.from(this);
        final LinearLayout rootLinearLayout = (LinearLayout) inflater.inflate(R.layout.activity_activitylifecycle, null).findViewById(R.id.rootview);
*/
        //启动LifecycleSecondActivity
        startBT1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                Intent intent = new Intent(LifecycleActivity.this
                        , LifecycleSecondActivity.class);
                startActivity(intent);
            }
        });

        //启动LifecycleThirdActivity
        startBT2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                Intent intent = new Intent(LifecycleActivity.this
                        , LifecycleThirdActivity.class);
                startActivity(intent);
            }
        });

        //在activity中添加和删除自定义的myLayout和myButton
        addBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                if (addBT.getText().equals(getResources().getString(R.string.addtxt))) {
                    addBT.setText(getResources().getString(R.string.deltxt));
                    
                    //将自定义Button添加到自定义myLayout中
                     myLayout.addView(myButton,LP_WW);

                    //将自定义myLayout动态添加到当前布局中
                    rootLinearLayout.addView(myLayout);

                    //动态变更Acivity控件后刷新显示
                    setContentView(rootLinearLayout);

                    //向动态添加的MyButton添加点击响应
                    myButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //结束该Activity
                            LifecycleActivity.this.finish();
                        }
                    });
                    Log.i(TAG, "---add myButton---");
                } else {
                    addBT.setText(getResources().getString(R.string.addtxt));
                    tv.setText("");
                    //将动态添加的控件删除
                    myLayout.removeAllViews();
                    rootLinearLayout.removeViewInLayout(myLayout);

                    Log.i(TAG, "---remove myButton---");
                }
            }
        });
        tv.setText("-------onCreate------\n");
        Log.d(TAG, "-------onCreate------");
    }

    //Activity被Stop后再启动时回调
    @Override
    public void onRestart() {
        super.onRestart();
        tv.append("-------onReStart------\n");
        Log.d(TAG, "-------onRestart------\n");
    }

    //Activity启动或再启动时回调
    @Override
    public void onStart() {
        super.onStart();
        tv.append("-------onStart------\n");
        Log.d(TAG, "-------onStart------");
    }

    //onStart()完成后调用
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tv.append("-------onPostCreate------\n");
        Log.d(TAG, "-------onPostCreate------");
    }

    //Activity恢复可见时回调
    @Override
    public void onResume() {
        super.onResume();
        tv.append("-------onResume------\n");
        Log.d(TAG, "-------onResume------");
    }

    //onResume()完成后调用
    @Override
    public void onPostResume() {
        super.onPostResume();
        tv.append("-------onPostResume------\n");
        Log.d(TAG, "-------onPostResume------");
    }
    //Activity失去焦点时回调
    @Override
    public void onPause() {
        super.onPause();
        tv.append("-------onPause------\n");
        Log.d(TAG, "-------onPause------");

    }

    //Activity完全不可见时回调
    @Override
    public void onStop() {
        super.onStop();
        tv.append("-------onStop------\n");
        Log.d(TAG, "-------onStop------");
    }

    //Activity销毁时回调
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "-------onDestroy------");
        tv.append("-------onDestroy------\n");
    }

}