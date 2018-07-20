/**
 * 本例演示了Menu、ActionMode、ActionBar、ToolBar、ActionMenuView的基本用法
 * 一、三种Menu的用法
 * 1)OptionsMenu：用手机Menu键弹出或显示在屏幕右上角位置，点击后弹出
 * 2)ContextMenu：上下文操作的一种方法，用registerForContextMenu与某个view关联,长按弹出菜单项
 * 3)PopupMenu：初始化new PopupMenu时与某个view关联,点击弹出菜单项
 * Menu使用要点如下：
 * 1）定义菜单项内容：代码或xml方式均可，建议后者。在下列位置定义：onCreateOptionsMenu、onCreateContextMenu、onClick
 * 2）定义菜单项点击事件处理：onOptionsItemSelected、onContextItemSelected、onMenuItemClick
 * 3）关联相关View
 * 4）invalidateOptionsMenu()会再次启动onCreateOptionsMenu和onPrepareOptionsMenu
 * 5）menu.findItem可以从xml中获取菜单的item
 * 6）item.setIntent可以直接跳转到新的Activity，此时onOptionsItemSelected和onContextItemSelected必须返回false
 * 二、ActionMode的用法
 *  临时借用ActionBar位置展示菜单，以完成当前上下文的重要操作。使用要点如下：
 * 1）临时占据了ActionBar的位置，将Menu中的全部MenuItem摆放其中，
 * 2）通过startActionMode(actionModeCallback)启动，通过点击左侧内置导航按钮或actionMode.finish()方法取消
 * 3）覆写actionModeCallback的四个方法，在onCreateActionMode中启用Menu，在onActionItemClicked中处理点击事件
 * 4）在Theme中定义其各种组件的样式
 * 三、ActionBar的用法
 * 在屏幕上方固定位置显示OptiionMenu，可以起到导航和常用操作入口作用，使用要点如下：
 * 1）位置固定在屏幕上方状态栏之下，组件有：返回箭头、Logo、Title、自定义视图和MenuItem
 * 2）定义菜单项内容：在onCreateOptionsMenu中用代码或xml方式定义菜单项，并定义showAsAction属性
 * 3）定义ActionBar的选项属性：用setDisplayOptions或xml来实现不同的的视图和功能
 * 4）定义菜单项点击事件处理：在onOptionsItemSelected中实现，
 * 5）如果用ActionProvider完成动作，则要提供ActionProvider的功能实现
 * 6）通过菜单xml中actionViewClass设置可以在Action Bar点击后呈现一些简单视图，如搜索框
 * 7) Menu中定义的onClick优先于onOptionsItemSelected
 * 四、Toolbar和ActionMenuView
 * ActionBar的升级版，通常用来提换ActionBar。偶尔也可以作为底部导航或其它使用。
 * ActionMenuView，作为Toolbar的子控件，将MenuItem收纳其中，用于动态添加MenuItem或enuItem在特殊位置显示使用
 * 详细用法见ToolbarActivity的注释说明
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Jan，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.MenuToolbarDemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.xottys.userinterface.BuildConfig;
import org.xottys.userinterface.ImageViewActivity;

import org.xottys.userinterface.ScrollViewDemo.ScrollViewActivity;
import org.xottys.userinterface.WebViewActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.xottys.userinterface.R;

public class MenuToolbarActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String SHARED_FILE_NAME = "mshared.png";
    private static final String TAG = "MenuActivity";
    /**
     * Different example menu resources.
     */
    private static final int sMenuExampleResources[] = {
            R.menu.main_menu, R.menu.groups_menu, R.menu.checkable_menu, R.menu.shortcuts_menu, R.menu.category_order_menu
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
    int mSortMode = -1;
    private TextView mContexMenutText, mSearchText;
    private ActionMode mActionMode;
    private PopupMenu popup = null;
    private int position = 0;
    /**
     * Safe to hold on to this.
     */
    private Menu mMenu;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        //在初始创建的时候调用
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Log.i(TAG, "onCreateActionMode: ");
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_toolbar1, menu);
            mode.setTitle("ActionMode");
            mode.setSubtitle("SubTitle");
            return true;
        }

        //准备绘制的时候调用
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            Log.i(TAG, "onPrepareActionMode: ");
            return false;
        }

        //点击 ActionMode 菜单选项的时候调用
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit:
                    Log.i(TAG, "onActionItemClicked: Edit");
                    break;
                case R.id.action_share:
                    Log.i(TAG, "onActionItemClicked: Share");
                    break;
                case R.id.action_back:
                    Log.i(TAG, "onActionItemClicked: Back");
                    mode.finish();
                    break;
            }
            return true;
        }

        //退出 ActionMode 的时候调用
        @Override
        public void onDestroyActionMode(ActionMode mode) {

            Log.i(TAG, "onDestroyActionMode: ");
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mContexMenutText = (TextView) findViewById(R.id.contextmenu_tv);
        TextView mActionModeText = (TextView) findViewById(R.id.actionmode_tv);
        mSearchText = (TextView) findViewById(R.id.searchinfo_tv);
        Button mPopunMenuButton = (Button) findViewById(R.id.popupmenu_btn);
        Button mActionBarButton = (Button) findViewById(R.id.actionbar_btn);
        Button mToolbarButton = (Button) findViewById(R.id.toolbar_btn);
        copyPrivateRawResuorceToPubliclyAccessibleFile();

        //PopupMenu----------------------------------------------------------------
        mPopunMenuButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View btn) {
                // 创建PopupMenu对象，关联相关View
                popup = new PopupMenu(MenuToolbarActivity.this, btn);
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
        registerForContextMenu(mContexMenutText);

        //启动ActionMode
        mActionModeText.setOnLongClickListener(new View.OnLongClickListener() {
            // Called when the user long-clicks on someView
            public boolean onLongClick(View view) {
                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });

        //启动AtionBar----------------------------------------------------------------
        mActionBarButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View btn) {
                Log.i(TAG, "onCreateActionBar: ");

                //删除ActionMode的菜单，否则ActionBar菜单显示不出来
                if (mActionMode != null) {
                    mActionMode.finish();
                }

                mMenu.clear();
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu_actionbar, mMenu);

                //设置ActionBar的一些显示属性
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("ActionBar");

                SearchView searchView = (SearchView) mMenu.findItem(R.id.action_search).getActionView();
                searchView.setOnQueryTextListener(MenuToolbarActivity.this);
                // Set file with share history to the provider and set the share intent.
                MenuItem actionItem = mMenu.findItem(R.id.action_share);
                ShareActionProvider actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(actionItem);

                actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
                // Note that you can set/change the intent any time,
                // say when the user has selected an image.
                actionProvider.setShareIntent(createShareIntent());
            }
        });


        //启动Toolbar----------------------------------------------------------------
        mToolbarButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View btn) {
                Intent intent = new Intent(getApplicationContext(), ToolbarActivity.class);
                startActivity(intent);
            }
        });

    }

    //OptionsMenu----------------------------------------------------------------
    //当用户单击MENU键时触发该方法，代码定义菜单项
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
        Intent intent;

        //判断单击的是哪个菜单项，并有针对性地作出响应
        switch (menuItem.getItemId()) {
            case R.id.font_10:
                mContexMenutText.setTextSize(10 * 2);
                break;
            case R.id.font_12:
                mContexMenutText.setTextSize(12 * 2);
                break;
            case R.id.font_14:
                mContexMenutText.setTextSize(14 * 2);
                break;
            case R.id.font_16:
                mContexMenutText.setTextSize(16 * 2);
                break;
            case R.id.font_18:
                mContexMenutText.setTextSize(18 * 2);
                break;
            case R.id.red_font:
                mContexMenutText.setTextColor(Color.RED);
                //menuItem.setChecked(true);
                break;
            case R.id.green_font:
                mContexMenutText.setTextColor(Color.GREEN);
                //menuItem.setChecked(true);
                break;
            case R.id.blue_font:
                mContexMenutText.setTextColor(Color.BLUE);
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

            case R.id.actionbar_displayoptioons:
                intent = new Intent(this, ActionBarActivity.class);
                startActivity(intent);
                break;
            case R.id.action_setting:
                intent = SettingsActionProvider.sSettingsIntent;
                startActivity(intent);
                break;
            case android.R.id.home:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("Options Menu");
                position = 0;
                invalidateOptionsMenu();
                Log.i(TAG, "onOptionsItemSelected: home");
                break;

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
                mContexMenutText.setTextSize(10 * 2);
                break;
            case FONT_12:
                mContexMenutText.setTextSize(12 * 2);
                break;
            case FONT_14:
                mContexMenutText.setTextSize(14 * 2);
                break;
            case FONT_16:
                mContexMenutText.setTextSize(16 * 2);
                break;
            case FONT_18:
                mContexMenutText.setTextSize(18 * 2);
                break;
            case FONT_RED:
                mContexMenutText.setTextColor(Color.RED);
                break;
            case FONT_GREEN:
                mContexMenutText.setTextColor(Color.GREEN);
                break;
            case FONT_BLUE:
                mContexMenutText.setTextColor(Color.BLUE);
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
        Toast.makeText(MenuToolbarActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    // This method is specified as an onClick handler in the menu xml and will
    // take precedence over the Activity's onOptionsItemSelected method.
    public void onSort(MenuItem item) {
        mSortMode = item.getItemId();
        // Request a call to onPrepareOptionsMenu so we can change the sort icon
        invalidateOptionsMenu();
        Log.i(TAG, "onSort: " + mSortMode);
    }

    // 将要分享的图片文件名以Uri形式放到Intent的Extra域中
    private Intent createShareIntent() {
        //将所有intent-filter为ACTION_SEND的App均列为可接收分享内容的程序
        Intent intent = new Intent(Intent.ACTION_SEND);
        //除Intent.EXTRA_STREAM外，还有：EXTRA_TEXT、EXTRA_EMAIL, EXTRA_CC, EXTRA_BCC, EXTRA_SUBJECT
        //  intent.setType("image/*");
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", getFileStreamPath(SHARED_FILE_NAME));
            //  intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            uri = Uri.fromFile(getFileStreamPath(SHARED_FILE_NAME));
            Uri.fromFile(getFileStreamPath(SHARED_FILE_NAME));
            //  intent.setDataAndType(Uri.fromFile(getFileStreamPath(SHARED_FILE_NAME)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
//        intent.setType("image/*");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        return intent;
    }

    //将要分享的图片R.raw.robot挪到shared.png
    private void copyPrivateRawResuorceToPubliclyAccessibleFile() {
        try {
            File f = new File(SHARED_FILE_NAME);
            if (!f.exists()) {

                InputStream inputStream = null;
                FileOutputStream outputStream = null;
                try {
                    inputStream = getResources().openRawResource(R.raw.robot);
                    outputStream = openFileOutput(SHARED_FILE_NAME,
                            Context.MODE_PRIVATE);
                    byte[] buffer = new byte[1024];
                    int length;
                    try {
                        while ((length = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }
                    } catch (IOException ioe) {

                /* ignore */
                    }
                } catch (FileNotFoundException fnfe) {
               /* ignore */
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException ioe) {
               /* ignore */
                    }
                    try {
                        outputStream.close();
                    } catch (IOException ioe) {
               /* ignore */
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // The following callbacks are called for the SearchView.OnQueryChangeListener
    // For more about using SearchView, see src/.../view/SearchView1.java and SearchView2.java
    public boolean onQueryTextChange(String newText) {
        newText = newText.isEmpty() ? "" : "Query so far: " + newText;
        mSearchText.setText(newText);
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
        return true;
    }

    //自定义的ActionPovider
    public static class SettingsActionProvider extends ActionProvider {

        /**
         * An intent for launching the system settings.
         */
        public static final Intent sSettingsIntent = new Intent(Settings.ACTION_SETTINGS);

        /**
         * Context for accessing resources.
         */
        private final Context mContext;

        /**
         * Creates a new instance.
         *
         * @param context Context for accessing resources.
         */
        public SettingsActionProvider(Context context) {
            super(context);
            mContext = context;
        }

        //这里的View将代替OptionMenu中的View,返回null则直接使用OptionMenu中的View
        @Override
        public View onCreateActionView() {
            Log.i(TAG, "onCreateActionView");
            // Inflate the action view to be shown on the action bar.
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.action_bar_settings_action_provider, null);
            ImageButton button = (ImageButton) view.findViewById(R.id.button);
            // Attach a click listener for launching the system settings.
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(sSettingsIntent);
                }
            });
            return view;
        }

        //此方法仅在没有定义onOptionsItemSelected方法时会被调用
        @Override
        public boolean onPerformDefaultAction() {
            // This is called if the host menu item placed in the overflow menu of the
            // action bar is clicked and the host activity did not handle the click.
            mContext.startActivity(sSettingsIntent);
            Log.i(TAG, "onPerformDefaultAction: ");
            return true;
        }
    }

}
