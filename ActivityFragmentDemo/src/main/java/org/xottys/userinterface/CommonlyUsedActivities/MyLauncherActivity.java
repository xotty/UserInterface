/**
 * 本例演示LauncherActivity(ListActivity的子类)的基本用法
 * 1)定义要跳转的各个Activity的名称
 * 2)定义要跳转的各个Activity对应的实现类数组
 * 3)onCreate中将各个Activity名称构造Adapter
 * 4)intentForPosition中用Activity对应的class构造Intent
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.CommonlyUsedActivities;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;


public class MyLauncherActivity extends LauncherActivity {
    //定义要跳转的各个Activity的名称
    String[] names = {"AliasActivity", "PreferenceActivity", "ExpandableListActivity", "AccountAuthenticatorActivity"};

    //定义各个Activity对应的实现类
    Class<?>[] clazzs = {MyAliasActivity.class, MyPreferenceActivity.class
            , MyExpandableListActivity.class, MyAuthenticateActivity.class};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将names数组的内容装入Adapter,以便显示在listview中
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);
    }

    //将clazzs数组直接放入，系统将按顺序对应listview上的每一行，行点击后将跳转相应Intent的Activity
    @Override
    public Intent intentForPosition(int position) {
        return new Intent(MyLauncherActivity.this, clazzs[position]);
    }
}