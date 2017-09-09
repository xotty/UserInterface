/**
 * 本例演示了AlertDialog的创建和使用方法
 * 1)创建方法一：AlertDialog.Builder构造Builder（例1～2）
 * 2)创建方法二：AlertDialog.Builder.create构造AlertDialog（例3～5,8~9）
 * 例3～5：在builder中设置各项属性，例8～9：用alertDialog方法设置主要属性
 * 4)创建方法三：ProgressDialog构造AlertDialog（例6～7）
 * 3)使用方法一：Builder.show或AlertDialog.show(例1~9)
 * 4)使用方法二：AlertDialogFragment.show(例10~13)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.DialogDemo;

import android.app.Fragment;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import org.xottys.userinterface.R;

public class DialogActivity extends TabActivity implements TabHost.TabContentFactory {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    /** {@inheritDoc} */
    public View createTabContent(String tag) {
        Fragment fragment=null;
        if (tag.equals("alert"))
            fragment = AlertDialogFragment.newInstance(tag);
        else if (tag.equals("progress"))
            fragment = ProgressFragment.newInstance(tag);
        else if (tag.equals("picker"))
            fragment = PickerFragment.newInstance(tag);

        View v = findViewById(android.R.id.tabcontent);
        getFragmentManager().beginTransaction()
                .add(android.R.id.tabcontent, fragment).commit();
        return v;
    }
}
