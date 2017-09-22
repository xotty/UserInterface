/**
 * 本例用于启动演示各种常用对话框（Dialog）的用法，用Tab选项卡方式分别启动：
 * 1）AlertDialog演示
 * 2）ProgressDialog演示
 * 3）DatePickerDialog、TimePickerDialog和NumberPicker演示
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.DialogDemo;

import android.app.Fragment;
import android.app.TabActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;

import org.xottys.userinterface.R;

public class DialogActivity extends TabActivity implements TabHost.TabContentFactory {
    Fragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        final TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("alert")
                .setIndicator("Alert")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("progress")
                .setIndicator("Progress")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("picker")
                .setIndicator("Picker")
                .setContent(this));
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
               changeFragment(tabId);
//                Toast.makeText(DialogActivity.this, "Tab Change to " + tabId, Toast.LENGTH_LONG).show();
            }
        });
    }

    public View createTabContent(String tag) {
        changeFragment(tag);

        View v = findViewById(android.R.id.tabcontent);
        Log.i("TAG", "createTabContent: "+tag);
        return v;
    }

    private void changeFragment(String tabId)
    {
        if (tabId.equals("alert"))
            fragment = AlertDialogFragment.newInstance(tabId);
        else if (tabId.equals("progress"))
            fragment = ProgressDialogFragment.newInstance();
        else if (tabId.equals("picker"))
            fragment = PickerDialogFragment.newInstance();

        getFragmentManager().beginTransaction()
                .replace(android.R.id.tabcontent, fragment).commit();
    }
}
