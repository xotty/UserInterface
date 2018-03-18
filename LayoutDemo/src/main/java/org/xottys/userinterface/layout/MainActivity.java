/**本例演示了android所有Layout,常用的前两种，3、4用于表结构，6不推荐使用
 * 1)LinearLayout
 * 2)RelativeLayout
 * 3)TableLayout
 * 4)GridLayout
 * 5)FrameLayout
 * 6)AbsoluteLayout
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private final String TAG = "UserInterface";
    private Button bt1, bt2, bt3, bt4, bt5, bt6;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt4 = (Button) findViewById(R.id.bt4);
        bt5 = (Button) findViewById(R.id.bt5);
        bt6 = (Button) findViewById(R.id.bt6);
        bt1.setBackgroundColor(0xbd292f34);
        bt1.setTextColor(0xFFFFFFFF);
        bt2.setBackgroundColor(0xbd292f34);
        bt2.setTextColor(0xFFFFFFFF);
        bt3.setBackgroundColor(0xbd292f34);
        bt3.setTextColor(0xFFFFFFFF);
        bt4.setBackgroundColor(0xbd292f34);
        bt4.setTextColor(0xFFFFFFFF);
        bt5.setBackgroundColor(0xbd292f34);
        bt5.setTextColor(0xFFFFFFFF);
        bt6.setBackgroundColor(0xbd292f34);
        bt6.setTextColor(0xFFFFFFFF);
        tv = (TextView) findViewById(R.id.tv);

        bt1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this
                        , LinearLayoutActivity.class);
                startActivity(intent);
            }
        });

        bt2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this
                        , TableLayoutActivity.class);
                startActivity(intent);
            }
        });
        bt3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this
                        , RelativeLayoutActivity.class);
                startActivity(intent);
            }
        });
        bt4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this
                        , GridLayoutActivity.class);
                startActivity(intent);
            }
        });
        bt5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this
                        , FrameLayoutActivity.class);
                startActivity(intent);
            }
        });
        bt6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this
                        , ConstraintLayoutActivity.class);
                startActivity(intent);
            }
        });
    }
}
