/**
 * 本例演示了ActionBar的各种参数选项和表现形式，主要有：
 * 1)是否显示返回箭头、Logo、Title
 * 2)显示自定义View
 * 3)两种已过期的导航方式：Tab和List
 * 只有setDisplayHomeAsUpEnabled为true返回上级才可点击，此时需对返回的目的地进行设置
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.MenuToolbarDemo;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.xottys.userinterface.R;

/**
 * This demo shows how various action bar display option flags can be combined
 * and their effects.
 */
public class ActionBarActivity extends Activity implements View.OnClickListener,
        ActionBar.TabListener, Spinner.OnItemSelectedListener, ActionBar.OnNavigationListener {
    private View mCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.action_bar_display_options);

        findViewById(R.id.toggle_home_as_up).setOnClickListener(this);
        findViewById(R.id.toggle_show_home).setOnClickListener(this);
        findViewById(R.id.toggle_use_logo).setOnClickListener(this);
        findViewById(R.id.toggle_show_title).setOnClickListener(this);
        findViewById(R.id.toggle_show_custom).setOnClickListener(this);
        findViewById(R.id.cycle_custom_gravity).setOnClickListener(this);
        findViewById(R.id.toggle_visibility).setOnClickListener(this);
        findViewById(R.id.toggle_system_ui).setOnClickListener(this);

        ((Spinner) findViewById(R.id.toggle_navigation)).setOnItemSelectedListener(this);

        mCustomView = getLayoutInflater().inflate(R.layout.action_bar_display_options_custom, null);
        // Configure several action bar elements that will be toggled by display options.
        final ActionBar bar = getActionBar();
        bar.setLogo(R.drawable.androidlogo);
        bar.setCustomView(mCustomView,
                new ActionBar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        bar.addTab(bar.newTab().setText("Tab 1").setTabListener(this));
        bar.addTab(bar.newTab().setText("Tab 2").setTabListener(this));
        bar.addTab(bar.newTab().setText("Tab 3").setTabListener(this));

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);
        adapter.add("Item 1");
        adapter.add("Item 2");
        adapter.add("Item 3");
        bar.setListNavigationCallbacks(adapter, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_options_actions, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            //点击返回箭头，此功能也可以通过在AndroidManifest.xml中设置android:parentActivityName(上级)来实现
            //此时要求onOptionsItemSelected返回false
            case android.R.id.home:
                finish();
                break;
            //点击Go Back
            case R.id.simple_item:
                finish();
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        final ActionBar bar = getActionBar();
        int flags = 0;
        switch (v.getId()) {
            case R.id.toggle_home_as_up:
                flags = ActionBar.DISPLAY_HOME_AS_UP;
                break;
            case R.id.toggle_show_home:
                flags = ActionBar.DISPLAY_SHOW_HOME;
                break;
            case R.id.toggle_use_logo:
                flags = ActionBar.DISPLAY_USE_LOGO;
                break;
            case R.id.toggle_show_title:
                flags = ActionBar.DISPLAY_SHOW_TITLE;
                break;
            case R.id.toggle_show_custom:
                flags = ActionBar.DISPLAY_SHOW_CUSTOM;
                break;
            case R.id.cycle_custom_gravity:
                ActionBar.LayoutParams lp = (ActionBar.LayoutParams) mCustomView.getLayoutParams();
                int newGravity = 0;
                switch (lp.gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
                    case Gravity.START:
                        newGravity = Gravity.CENTER_HORIZONTAL;
                        break;
                    case Gravity.CENTER_HORIZONTAL:
                        newGravity = Gravity.END;
                        break;
                    case Gravity.END:
                        newGravity = Gravity.START;
                        break;
                }
                lp.gravity = lp.gravity & ~Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK | newGravity;
                bar.setCustomView(mCustomView, lp);
                return;
            case R.id.toggle_visibility:
                if (bar.isShowing()) {
                    bar.hide();
                } else {
                    bar.show();
                }
                return;
            case R.id.toggle_system_ui:
                if ((getWindow().getDecorView().getSystemUiVisibility()
                        & View.SYSTEM_UI_FLAG_FULLSCREEN) != 0) {
                    getWindow().getDecorView().setSystemUiVisibility(0);
                } else {
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_FULLSCREEN);
                }
                return;
        }

        int change = bar.getDisplayOptions() ^ flags;

        //只有当options在mask中被设置才能被设为true
        //setDisplayOptions(int options, int mask);
        bar.setDisplayOptions(change, flags);
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final ActionBar bar = getActionBar();
        switch (parent.getId()) {
            case R.id.toggle_navigation:
                final int mode;
                switch (position) {
                    case 1:
                        mode = ActionBar.NAVIGATION_MODE_TABS;
                        break;
                    case 2:
                        mode = ActionBar.NAVIGATION_MODE_LIST;
                        break;
                    default:
                        mode = ActionBar.NAVIGATION_MODE_STANDARD;
                }
                bar.setNavigationMode(mode);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }
}
