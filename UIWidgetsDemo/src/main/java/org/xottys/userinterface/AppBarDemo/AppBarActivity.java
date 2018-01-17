/**
 * 本例演示了ActionBar、ToolBar、ActionMode的基本用法：
 * 1)ActionBar：在屏幕上方固定位置显示OptiionMenu，可以起到导航和常用操作入口作用
 * 2)ToolBar：
 * ActionBar使用要点如下：
 * 1）位置固定在屏幕上方状态栏之下，组件有：返回箭头、Logo、Title、自定义视图和MenuItem
 * 2）定义菜单项内容：在onCreateOptionsMenu中用代码或xml方式定义菜单项，并定义showAsAction属性
 * 3）定义ActionBar的选项属性：用setDisplayOptions或xml来实现不同的的视图和功能
 * 4）定义菜单项点击事件处理：在onOptionsItemSelected中实现，
 * 5）如果用ActionProvider完成动作，则要提供ActionProvider的功能实现
 * 6）通过菜单xml中actionViewClass设置可以在Action Bar点击后呈现一些简单视图，如搜索框
 * 7) Menu中定义的onClick优先于onOptionsItemSelected
 *  <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */

package org.xottys.userinterface.AppBarDemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import org.xottys.userinterface.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * This demonstrates idiomatic usage of the Action Bar. The default Honeycomb theme
 * includes the action bar by default and a menu resource is used to populate the
 * menu data itself.
 */
public class AppBarActivity extends Activity implements SearchView.OnQueryTextListener {
    private static final String TAG = "AppBarActivity";
    private static final String SHARED_FILE_NAME = "mshared.png";
    TextView mSearchText;
    int mSortMode = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchText = new TextView(this);
        setContentView(mSearchText);
        // 设置是否显示应用程序图标
        getActionBar().setDisplayShowHomeEnabled(false);
        copyPrivateRawResuorceToPubliclyAccessibleFile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu1, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        // Set file with share history to the provider and set the share intent.
        MenuItem actionItem = menu.findItem(R.id.action_share);
        ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
        actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        // Note that you can set/change the intent any time,
        // say when the user has selected an image.
        actionProvider.setShareIntent(createShareIntent());
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mSortMode != -1) {
            Drawable icon = menu.findItem(mSortMode).getIcon();
            menu.findItem(R.id.action_sort).setIcon(icon);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.actionbar_displayoptioons:
                intent = new Intent(this, ActionBarDisplayOptions.class);
                break;
            case R.id.action_setting:
                intent = SettingsActionProvider.sSettingsIntent;
                break;
            case R.id.toolbar:
                intent = new Intent(this, ToolBarActivity.class);
                break;
//            case R.id.actionmode:
//                startActionMode(mCallback);
//                return true;
            default:
                return true;
        }
        startActivity(intent);
        return true;
    }

    // This method is specified as an onClick handler in the menu xml and will
    // take precedence over the Activity's onOptionsItemSelected method.
    public void onSort(MenuItem item) {
        mSortMode = item.getItemId();
        // Request a call to onPrepareOptionsMenu so we can change the sort icon
        invalidateOptionsMenu();
        Log.i(TAG, "onSort: " + mSortMode);
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

    // 将要分享的图片文件名以Uri形式放到Intent的Extra域中
    private Intent createShareIntent() {
        //将所有intent-filter为ACTION_SEND的App均列为可接收分享内容的程序
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //除Intent.EXTRA_STREAM外，还有：EXTRA_TEXT、EXTRA_EMAIL, EXTRA_CC, EXTRA_BCC, EXTRA_SUBJECT
        shareIntent.setType("image/*");
        Uri uri = Uri.fromFile(getFileStreamPath(SHARED_FILE_NAME));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

        return shareIntent;
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
                    Context.MODE_WORLD_WRITEABLE);
            byte[] buffer = new byte[1024];
            int length = 0;
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
            // Inflate the action view to be shown on the action bar.
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.action_bar_settings_action_provider, null);
            ImageButton button = view.findViewById(R.id.button);
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
