/**
 * 本例用于启动演示各种常用对话框（Dialog）的用法，用Tab选项卡方式分别启动：
 * 1）AlertDialog演示
 * 2）ProgressDialog演示
 * 3）DatePickerDialog、TimePickerDialog和NumberPicker演示
 *TabHost的用法要点：
 * 1）在xml中用<TabHost></TabHost>设置布局，其中含有TabWidget（标签）+FrameLayout（内容）,所有id都必须是系统规定的
 * 2）获取TabHost
 * 3）给TabHost添加标签及其内容（指定内容生成器位置）
 * 4）设置Tab标签点击监听器
 * TabWidget在FrameLayout上面就是Tab标签显示在顶部，若要使Tab标签显示在底部，则有下列两种方法：
 * 1）TabWidget和FrameLayout被嵌套在LinearLayout布局中，FrameLayout中添加属性：android:layout_weight="1"，
 * TabWidget在FrameLayout下面
 * 2）TabWidget和FrameLayout被嵌套在LinearLayout布局中，FrameLayout中添加属性：android:layout_alignParentBottom="true"
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:DialogActivity
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.DialogDemo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.Toast;

import org.xottys.userinterface.R;

public class DialogActivity extends Activity implements TabHost.TabContentFactory {
    Fragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        //获取TabHost，若果是过时的TabActivity则使用TabHost tabHost = getTabHost();
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        //给TabHost添加标签及其内容（指定内容生成器位置）
        //标签可以是label(文字)/label+drawable(图片)/view
        //内容可以是View/TabContentFactory（本例）/Intent
        tabHost.addTab(tabHost.newTabSpec("alert")
                .setIndicator("Alert")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("progress")
                .setIndicator("Progress")
                .setContent(this));
        tabHost.addTab(tabHost.newTabSpec("picker")
                .setIndicator("Picker")
                .setContent(this));
        //设置Tab标签点击监听器
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
               changeFragment(tabId);
                Toast.makeText(DialogActivity.this, "Tab Change to " + tabId, Toast.LENGTH_LONG).show();
            }
        });
    }

    //用TabContentFactory生成Tab内容
    @Override
    public View createTabContent(String tag) {
        changeFragment(tag);
        View v = findViewById(android.R.id.tabcontent);
        Log.i("TAG", "createTabContent: "+tag);
        return v;
    }

    private void changeFragment(String tabId)
    {
        switch (tabId) {
            case "alert":
                fragment = AlertDialogFragment.newInstance(tabId);
                break;
            case "progress":
                fragment = ProgressDialogFragment.newInstance();
                break;
            case "picker":
                fragment = PickerDialogFragment.newInstance();
                break;
        }

        getFragmentManager().beginTransaction()
                .replace(android.R.id.tabcontent, fragment).commit();
    }
}
