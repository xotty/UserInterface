/**
 * SingleInstance方式启动的Activity
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LifecycleActivity
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.ActivityTaskMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.xottys.userinterface.ActivityLifecycle.AppGlobal;
import org.xottys.userinterface.R;

public class SingleInstanceActivity extends Activity {
    private final String TAG = "ActivityTaskMode";
    private Button bt1, bt2;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);

        tv = (TextView) findViewById(R.id.tv);

        bt1.setBackgroundColor(0xbd292f34);
        bt1.setTextColor(0xFFFFFFFF);
        bt2.setBackgroundColor(0xbd292f34);
        bt2.setTextColor(0xFFFFFFFF);



        bt1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SingleInstanceActivity.this,
                        StandardActivity.class);
                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.i(TAG, "SingleInstance Activity Created");
    }

    @Override
    public void onResume() {
        super.onResume();
           //输出Activity栈内容
           String str= AppGlobal.getInstance().createtActivityStackInfo();
           tv.setText(str);
    }
}
