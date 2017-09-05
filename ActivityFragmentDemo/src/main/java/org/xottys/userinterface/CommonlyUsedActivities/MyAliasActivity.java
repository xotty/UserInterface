/**
 * 本例演示AliasActivity的基本用法：
 * 只需在AndroidManifest中用<activity-alias>来标志，
 * 并设置目标Activity：android:targetActivity=".MyListActivity"
 * 甚至不需要有实体的MyAliasActivity文件
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.CommonlyUsedActivities;

import android.app.AliasActivity;
import android.os.Bundle;

public class MyAliasActivity extends AliasActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
