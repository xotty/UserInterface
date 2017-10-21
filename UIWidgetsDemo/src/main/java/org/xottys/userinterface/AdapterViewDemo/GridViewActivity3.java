/**
 * 本例演示了用BaseAdapter装配的可进行多选的GridView的用法：
 * 1)主xml中使用GridView控件，其特殊属性有：numColumns="auto_fi"、columnWidth="60dp"、
 * stretchMode="columnWidth"、verticalSpacing="20dp"、horizontalSpacing="20dp"
 * 2)GridView设置setChoiceMode和setMultiChoiceModeListener
 * 3)自定义Checkable布局
 * 4)在GetView中将App图标放到Checkable布局上
 * 5)在监听器中处理点击事件
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
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import org.xottys.userinterface.R;

import java.util.List;

/**
 * This demo illustrates the use of CHOICE_MODE_MULTIPLE_MODAL, a.k.a. selection mode on GridView.
 */
public class GridViewActivity3 extends Activity {

    GridView mGrid;
    private List<ResolveInfo> mApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadApps();
        setContentView(R.layout.activity_grid);
        mGrid = (GridView) findViewById(R.id.myGrid);
        mGrid.setAdapter(new AppsAdapter());
        //设为可选，还有单选方式
        mGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        mGrid.setMultiChoiceModeListener(new MultiChoiceModeListener());

        /*可换成
        mGrid.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        mGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("TAG", "onItemClick: "+position);
            }
        });*/

    }

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    private class AppsAdapter extends BaseAdapter {
        private AppsAdapter() {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            CheckableLayout l;
            ImageView i;

            if (convertView == null) {
                //将app图标加载到自定义的Checkable布局上
                i = new ImageView(GridViewActivity3.this);
                i.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                l = new CheckableLayout(GridViewActivity3.this);
                l.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT,
                        GridView.LayoutParams.WRAP_CONTENT));
                l.addView(i);
            } else {
                l = (CheckableLayout) convertView;
                i = (ImageView) l.getChildAt(0);
            }

            ResolveInfo info = mApps.get(position);
            i.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));

            return l;
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

    //自定义Checkable布局
    public class CheckableLayout extends FrameLayout implements Checkable {
        private boolean mChecked;
        public CheckableLayout(Context context) {
            super(context);
        }

        @Override
        public boolean isChecked() {
            return mChecked;
        }

        @Override
        public void setChecked(boolean checked) {
            mChecked = checked;
            setBackground(checked ?
                    getResources().getDrawable(R.color.blue,null)
                    : null);
        }

        @Override
        public void toggle() {
            setChecked(!mChecked);
        }

    }

    //点击监听器
    private class MultiChoiceModeListener implements GridView.MultiChoiceModeListener {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Select Items");
            mode.setSubtitle("One item selected");
            return true;
        }
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return true;
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
                                              boolean checked) {
            int selectCount = mGrid.getCheckedItemCount();
            switch (selectCount) {
            case 1:
                mode.setSubtitle("One item selected");
                break;
            default:
                mode.setSubtitle("" + selectCount + " items selected");
                break;
            }
        }

    }
}
