/**
 * 本例演示了三种Menu的用法：
 * 1)OptionsMenu：用手机Menu键弹出或显示在屏幕右上角位置，点击后弹出
 * 2)ContextMenu：用registerForContextMenu与某个view关联,长按弹出菜单项
 * 3)PopupMenu：初始化new PopupMenu时与某个view关联,点击弹出菜单项
 * 使用要点如下：
 * 1）定义菜单项内容：代码或xml方式均可，建议后者。在下列位置定义：onCreateOptionsMenu、onCreateContextMenu、onClick
 * 2）定义菜单项点击事件处理：onOptionsItemSelected、onContextItemSelected、onMenuItemClick
 * 3）关联相关View
 * 4）invalidateOptionsMenu()会再次启动onCreateOptionsMenu和onPrepareOptionsMenu
 * 5）menu.findItem可以从xml中获取菜单的item
 * 6）item.setIntent可以直接跳转到新的Activity，此时onOptionsItemSelected和onContextItemSelected必须返回false
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.xottys.userinterface.ScrollViewDemo.ScrollViewActivity;

import java.lang.reflect.Method;

public class MenuActivity extends Activity {
    private static final String TAG = "MenuActivity";
    /**
     * Different example menu resources.
     */
    private static final int sMenuExampleResources[] = {
            R.menu.main_menu, R.menu.groups_menu, R.menu.checkable_menu, R.menu.shortcuts_menu, R.menu.category_order_menu,
    };
    /**
     * Names corresponding to the different example menu resources.
     */
    private static final String sMenuExampleNames[] = {"Basic", "Groups",
            "Checkable", "Shortcuts", "Category and Order"
    };
    // 定义字体大小菜单项的标识
    final int FONT_10 = 0x111;
    final int FONT_12 = 0x112;
    final int FONT_14 = 0x113;
    final int FONT_16 = 0x114;
    final int FONT_18 = 0x115;
    // 定义普通菜单项的标识
    final int PLAIN_ITEM = 0x11b;
    // 定义字体颜色菜单项的标识
    final int FONT_RED = 0x116;
    final int FONT_BLUE = 0x117;
    final int FONT_GREEN = 0x118;
    // 定义启动菜单项的标识
    final int INTENT_ITEM = 0x11c;
    private TextView textview;
    private Button button;
    private PopupMenu popup = null;
    private int position = 0;
    /**
     * Safe to hold on to this.
     */
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        textview = (TextView) findViewById(R.id.tv);
        button = (Button) findViewById(R.id.btn);
        //PopupMenu----------------------------------------------------------------
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View btn) {
                // 创建PopupMenu对象，关联相关View
                popup = new PopupMenu(MenuActivity.this, btn);
                // 将R.menu.popup_menu菜单资源加载到popup菜单中
                getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                // 为popup菜单的菜单项单击事件绑定事件监听器
                popup.setOnMenuItemClickListener(
                        new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.exit:
                                        // 隐藏该对话框
                                        popup.dismiss();
                                        break;
                                    default:
                                        // 显示用户单击的菜单项
                                        position = item.getOrder();
                                        Log.e(TAG, "onMenuItemClick: " + position);
                                        invalidateOptionsMenu();
                                        showMessage("您单击了【" + item.getTitle() + "】菜单项");
                                        break;
                                }
                                return true;
                            }
                        });
                popup.show();
            }
        });

        // 为文本框注册上下文菜单
        registerForContextMenu(textview);
    }

    //OptionsMenu----------------------------------------------------------------
    //当用户单击MENU键时触发该方法，代码定义菜单想
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu: " + position);

        // Hold on to this
        menu.clear();
        mMenu = menu;

        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(sMenuExampleResources[position], menu);

        if (position == 0) {
            MenuItem item1 = menu.findItem(R.id.activity1);
            MenuItem item2 = menu.findItem(R.id.activity2);
            item1.setIntent(new Intent(this, WebViewActivity.class));
            item2.setIntent(new Intent(this, ScrollViewActivity.class));
        }
        //缺省是不显示Icon的
        setIconsVisible(menu, true);
        //true：显示   false：不显示
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    // 选项菜单的菜单项被单击后调用
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Log.i(TAG, "onOptionsItemSelected: " + menuItem.getItemId());
        //判断单击的是哪个菜单项，并有针对性地作出响应
        if (menuItem.isCheckable()) {
            // 勾选该菜单项
            menuItem.setChecked(true);
        }
        //判断单击的是哪个菜单项，并有针对性地作出响应
        switch (menuItem.getItemId()) {
            case R.id.font_10:
                textview.setTextSize(10 * 2);
                break;
            case R.id.font_12:
                textview.setTextSize(12 * 2);
                break;
            case R.id.font_14:
                textview.setTextSize(14 * 2);
                break;
            case R.id.font_16:
                textview.setTextSize(16 * 2);
                break;
            case R.id.font_18:
                textview.setTextSize(18 * 2);
                break;
            case R.id.red_font:
                textview.setTextColor(Color.RED);
                //menuItem.setChecked(true);
                break;
            case R.id.green_font:
                textview.setTextColor(Color.GREEN);
                //menuItem.setChecked(true);
                break;
            case R.id.blue_font:
                textview.setTextColor(Color.BLUE);
                //menuItem.setChecked(true);
                break;
            case R.id.plain_item:
                showMessage("您单击了普通菜单项");
                break;
            case R.id.intent_item:
                startActivity(new Intent(this, ImageViewActivity.class));
                break;
            // For "Groups": Toggle visibility of grouped menu items with nongrouped menu items
            case R.id.browser_visibility:
                // The refresh item is part of the browser group
                final boolean shouldShowBrowser = !mMenu.findItem(R.id.refresh).isVisible();
                mMenu.setGroupVisible(R.id.browser, shouldShowBrowser);
                break;

            case R.id.email_visibility:
                // The reply item is part of the email group
                final boolean shouldShowEmail = !mMenu.findItem(R.id.reply).isVisible();
                mMenu.setGroupVisible(R.id.email, shouldShowEmail);
                break;

            // Generic catch all for all the other menu resources
            default:
                // Don't toast text when a submenu is clicked
                if (!menuItem.hasSubMenu()) {
                    showMessage(menuItem.getTitle().toString());

                }
                break;
        }

        //true表示该方法执行完毕后，点击事件不会再向下一个事件处理方法传递了
        //false表示执行完该方法后，点击事件继续向下传递
        //此处若为true，启动程序一中的Activity无法跳转
        return false;
    }

    //菜单显示之前调用
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu: ");
        return super.onPrepareOptionsMenu(menu);
    }

    //菜单打开后调用
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        Log.i(TAG, "onMenuOpened: ");

        // true 显示menu, false 不显示menu
        return super.onMenuOpened(featureId, menu);
    }

    //菜单关闭后调用
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        Log.i(TAG, "onOptionsMenuClosed: ");
    }

    //ContextMenu----------------------------------------------------------------
    // 创建上下文菜单时触发该方法
    @Override
    public void onCreateContextMenu(ContextMenu menu, View source,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // 向menu中添加字体大小的子菜单
        SubMenu fontMenu = menu.addSubMenu("字体大小");
        // 设置菜单的图标
        fontMenu.setIcon(R.drawable.font);
        // 设置菜单头的图标
        fontMenu.setHeaderIcon(R.drawable.font);
        // 设置菜单头的标题
        fontMenu.setHeaderTitle("选择字体大小");

        fontMenu.add(0, FONT_10, 0, "10号字体");
        fontMenu.add(0, FONT_12, 0, "12号字体");
        fontMenu.add(0, FONT_14, 0, "14号字体");
        fontMenu.add(0, FONT_16, 0, "16号字体");
        fontMenu.add(0, FONT_18, 0, "18号字体");
        //向menu中添加普通菜单项
        menu.add(0, PLAIN_ITEM, 0, "添加").setIcon(android.R.drawable.ic_menu_add);
        // 向menu中添加文字颜色的子菜单
        SubMenu colorMenu = menu.addSubMenu("字体颜色");
        // 设置菜单头的图标
        colorMenu.setHeaderIcon(R.drawable.color);
        // 设置菜单头的标题
        colorMenu.setHeaderTitle("选择文字颜色");
        colorMenu.add(0, FONT_RED, 0, "红色");
        colorMenu.add(0, FONT_GREEN, 0, "绿色");
        colorMenu.add(0, FONT_BLUE, 0, "蓝色");

        // 向menu中添加启动程序子菜单
        SubMenu prog = menu.addSubMenu("启动程序一");
        // 设置菜单头的图标
        prog.setHeaderIcon(R.drawable.world);
        // 设置菜单头的标题
        prog.setHeaderTitle("选择您要启动的程序");
        // 添加菜单项
        MenuItem item1 = prog.add("WebViewActivity");
        MenuItem item2 = prog.add("ScrollViewActivity");
        //为菜单项设置关联的Activity
        item1.setIntent(new Intent(this, WebViewActivity.class));
        item2.setIntent(new Intent(this, ScrollViewActivity.class));

        //向menu中添加手动启动程序菜单项
        menu.add(0, INTENT_ITEM, 0, "启动程序二");

    }

    // 上下文菜单的菜单项被单击时触发该方法
    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case FONT_10:
                textview.setTextSize(10 * 2);
                break;
            case FONT_12:
                textview.setTextSize(12 * 2);
                break;
            case FONT_14:
                textview.setTextSize(14 * 2);
                break;
            case FONT_16:
                textview.setTextSize(16 * 2);
                break;
            case FONT_18:
                textview.setTextSize(18 * 2);
                break;
            case FONT_RED:
                textview.setTextColor(Color.RED);
                break;
            case FONT_GREEN:
                textview.setTextColor(Color.GREEN);
                break;
            case FONT_BLUE:
                textview.setTextColor(Color.BLUE);
                break;
            case PLAIN_ITEM:
                showMessage("您单击了普通菜单项");
                break;
            case INTENT_ITEM:
                startActivity(new Intent(this, ImageViewActivity.class));
                break;
        }

        //true表示该方法执行完毕后，点击事件不会再向下一个事件处理方法传递了
        //false表示执行完该方法后，点击事件继续向下传递
        //此处若为true，启动程序一中的Activity无法跳转
        return false;
    }

    //Private Tools----------------------------------------------------------------
    private void setIconsVisible(Menu menu, boolean flag) {
        //判断menu是否为空
        if (menu != null) {
            try {
                //如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                //暴力访问该方法
                method.setAccessible(true);
                //调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(MenuActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
