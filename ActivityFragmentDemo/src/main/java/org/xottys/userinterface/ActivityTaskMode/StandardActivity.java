/**
 * 本例演示了Activity的四种启动模式：
 * 1) standard..........每次都会产生一个新的Activity实例
 * 2) singleTask........每次都会使用这个实例，不会去产生新的实例，其上的Activity全部被弹出
 * 3) singleTop.........如果Activity实例位于栈顶，就不产生新的实例，直接使用栈顶的实例，否则，就会产生一个新的实例
 * 4) singleInstance....在一个新的Task中，产生一个新的Activity实例，此Task中只能有这个Activity实例，不能有其他的实例
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
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xottys.userinterface.ActivityLifecycle.AppGlobal;
import org.xottys.userinterface.R;

import java.util.List;

public class StandardActivity extends Activity {
    private final String TAG = "ActivityTaskMode";
    private Button bt1, bt2, bt3;
    private TextView tv;

    ActivityManager manager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);

        tv = (TextView) findViewById(R.id.tv);

        bt1.setBackgroundColor(0xbd292f34);
        bt1.setTextColor(0xFFFFFFFF);
        bt2.setBackgroundColor(0xbd292f34);
        bt2.setTextColor(0xFFFFFFFF);
        bt3.setBackgroundColor(0xbd292f34);
        bt3.setTextColor(0xFFFFFFFF);

        manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        //singleTask模式启动
        bt1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StandardActivity.this
                        , SingleTaskActivity.class);
                startActivity(intent);
            }
        });

        //singleTop模式启动
        bt2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StandardActivity.this
                        , SingleTopActivity.class);
                startActivity(intent);
            }
        });

        //singleInstatnce模式启动
        bt3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StandardActivity.this
                        , SingleInstanceActivity.class);
                startActivity(intent);
            }
        });
    }

    //本Activity是standard模式启动，输出当前Activity栈的内容
    @Override
    public void onResume()
    {
        super.onResume();

        //获取task栈，输出top和base Activity
        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(10);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
            tv.append("-----"+runningTaskInfo.topActivity.getShortClassName()+"-----\n");
            Log.v(TAG, runningTaskInfo.id+"---"+runningTaskInfo.numActivities+"---"+runningTaskInfo.baseActivity.getShortClassName()+"---"+runningTaskInfo.topActivity.getShortClassName());
        }

        //输出Activity栈中间的内容
        String str= AppGlobal.getInstance().createtActivityStackInfo();
        tv.setText(str);
    }

}
