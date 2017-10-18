/**
 * 本例演示了ToolBar的基本用法：：
 * 1）xml中用<android.support.v7.widget.Toolbar>定义，其中可选择定义一些简单控件
 * 2）使用ToolBar的Activity必须是AppCompatActivity，且要屏蔽actionBar（用相应Theme或requestWindowFeature(Window.FEATURE_NO_TITLE)）
 * 3）构建ToolBar的各个组件：NavigationIcon、Logo、Title、Subtitle、自定义控件、MenuItem（作为ActionBar时才会有，且其Item设置app:showAsAction）
 * 4）在styles.xml中用Theme方式定义各组件的颜色和大小，包括MenuItem弹出框的样式和位置
 * 5）setSupportActionBar(toolbar)
 * 6）定义NavigationIcon、自定义控件和菜单项点击事件
 * 7) onOptionsItemSelected中android.R.id.home分支失效
 * 8) 可以独立不作为ActionBar来使用，水平放置在屏幕的任何位置，此时不再具有Menu绑定关系
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:ToolBarActivity
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AppBarDemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xottys.userinterface.R;


public class ToolBarActivity extends AppCompatActivity {
    private static final String TAG = "ToolBarActivity";
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case android.R.id.home:
                    msg += "Click home";
                case R.id.action_edit:
                    msg += "Click edit";
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
                case R.id.action_settings:
                    msg += "Click setting";
                    break;
            }

            if (!msg.equals("")) {
                Log.i(TAG, "onMenuItemClick: " + msg);
                Toast.makeText(ToolBarActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        Button bt1 = (Button) findViewById(R.id.bt1);
        Button bt2 = (Button) findViewById(R.id.bt2);
        Button bt3 = (Button) findViewById(R.id.bt3);


        // App Logo
        toolbar1.setLogo(R.drawable.ic_launcher);
        // Title
        toolbar1.setTitle("Title");
        toolbar1.setTitleTextColor(getResources().getColor(R.color.red));

        // Sub Title
        toolbar1.setSubtitle("Sub title");
        //设置子标题的外观，包括文字颜色，文字大小等
        toolbar1.setSubtitleTextAppearance(this, R.style.Theme_ToolBar_Subtitle);

        setSupportActionBar(toolbar1);

        //设置导航图标
        toolbar1.setNavigationIcon(R.drawable.ic_drawer_home);
    /*导航图标也可以换成普通的返回箭头
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    */
        //设置导航图标点击监听器
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "HomeIcon Clicked");
            }
        });

        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
        toolbar1.setOnMenuItemClickListener(onMenuItemClick);

        //toolbar2中按钮点击事件
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Edit");
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Share");
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Alarm");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
}
