/**
 * 本例演示了用BaseAdapter装配的GridView的用法：
 * 1)主xml中使用GridView控件，其特殊属性有：numColumns="auto_fi"、columnWidth="60dp"、
 * stretchMode="columnWidth"、verticalSpacing="20dp"、horizontalSpacing="20dp"
 * 2)在GetView覆写方法中定义每个Item的内容：将全部app的图标放进去
 * <p>
 * <br/>Copyright (C), 2017-2018, Google
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author Google Inc.
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.xottys.userinterface.R;

import java.util.List;

//Need the following import to get access to the app resources, since this
//class is in a sub-package.

public class GridViewActivity2 extends Activity {

    GridView mGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadApps();

        setContentView(R.layout.activity_grid);
        mGrid = (GridView) findViewById(R.id.myGrid);
        mGrid.setAdapter(new AppsAdapter());
    }

    private List<ResolveInfo> mApps;

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        //获取手机上当前安装的全部app
        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    private class AppsAdapter extends BaseAdapter {
        private AppsAdapter() {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i;

            if (convertView == null) {
                i = new ImageView(GridViewActivity2.this);
                i.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i.setLayoutParams(new GridView.LayoutParams(150,150));
            } else {
                i = (ImageView) convertView;
            }
            //获取app的图标
            ResolveInfo info = mApps.get(position);
            i.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));

            return i;
        }


        public final int getCount() {
            return mApps.size();
        }

        public final Object getItem(int position) {
            return mApps.get(position);
        }

        public final long getItemId(int position) {
            return position;
        }
    }

}
