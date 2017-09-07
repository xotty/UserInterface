/**
 * 本例演示了除Fragment的基本用法和生命周期(Fragment在6~7之间活动在前台)：
 * 1）onAttach()
 * 2）onCreate()
 * 3）onCreateView()
 * 4）onActivityCreated()
 * 5）onStart()
 * 6）onResume()
 * 7）onPause()
 * 8) onStop()
 * 9) onDestroyView()
 * 10)onDestroy()
 * 11)onDetach()
 * Fragment的操作主要有Add、Replace、Remove、Hide、Show五种，Transaction是主要操作方式
 * 在布局文件中直接使用<Fragment>标签也可以在启动Activity时直接启动Fragment
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.FragmentLifecycle;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class FragmentLifecycleActivity extends Activity {
    private static final String TAG = "FragmentLifecycle";

    Button addFragment, replaceFragment, removeFragment, addStackFragment, popStackFragment, finish;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentlifecycle);

        addFragment = (Button) findViewById(R.id.bt1);
        replaceFragment = (Button) findViewById(R.id.bt2);
        addStackFragment = (Button) findViewById(R.id.bt3);
        popStackFragment = (Button) findViewById(R.id.bt4);
        removeFragment = (Button) findViewById(R.id.bt5);
        finish = (Button) findViewById(R.id.bt6);
        tv = (TextView) findViewById(R.id.atv);
        tv.setText("    Fragment生命周期   	 ");

        //定义三个不同的Fragment
        final FirstLifecycleFragment fragment1 = new FirstLifecycleFragment();
        final SecondLifecycleFragment fragment2 = new SecondLifecycleFragment();
        final ThirdLifecycleFragment fragment3 = new ThirdLifecycleFragment();

        //为回退栈绑定事件监听器
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                tv.append("onBackStackChanged\n");
                Log.i(TAG, "onBackStackChanged: ");
            }
        });

        //添加Fragment到布局文件的容器中，同一容器中可以添加多个Fragment，后面的会覆盖前面的
        //同一个Fragment的同一实例不能在系统中同时出现，不同实例可以同时出现
        addFragment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                tv.setText("Add Fragment生命周期\n\n");
                getFragmentManager().beginTransaction()
                        .add(R.id.container1, fragment1)
                        .add(R.id.container2, fragment2)
                        .commit();
            }
        });

        //替换容器中的的Fragment
        replaceFragment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("Replace Fragment生命周期\n\n");
                getFragmentManager().beginTransaction()
                        .replace(R.id.container1, fragment3)
                        .commit();
            }
        });

        //移除容器中的的Fragment
        removeFragment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("Remove Fragment生命周期\n\n");
                SecondLifecycleFragment fragment = new SecondLifecycleFragment();
                getFragmentManager().beginTransaction()
                        .remove(fragment1)
                        .show(fragment3)        //显示容器中的fragment3
                        .commit();
            }
        });

        //将Fragment操作放入回退栈，以便能随时取消该操作
        addStackFragment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                tv.setText("AddStack Fragment生命周期\n\n");

                getFragmentManager().beginTransaction()
                        .add(R.id.container1, fragment1)
                        .addToBackStack("aa") // 将上述操作添加到Back栈
                        .hide(fragment3)      //隐藏容器中的fragment3
                        .commit();

                FirstLifecycleFragment fragment = new FirstLifecycleFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container2, fragment)
                        .addToBackStack("bb")// 将替换前的Fragment添加到Back栈
                        .commit();
            }
        });

        //弹出特定的Fragment回退栈，取消上一步的相应操作
        popStackFragment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                tv.setText("PopStack Fragment生命周期\n\n");
                getFragmentManager().popBackStack("bb", 1);
            }
        });

        //结束Activity从而结束Fragment，这时Fragment的销毁过程只能在后台终端看到
        finish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View source) {
                tv.setText("Finsh Fragment生命周期\n\n");
                FragmentLifecycleActivity.this.finish();
            }
        });
    }

    //Fragment调用它来在屏幕上显示Fragment生命周期过程，这也是一种二者之间的通讯方式
    public void setTvText(String str) {
        tv.append(str + "\n");
    }
}