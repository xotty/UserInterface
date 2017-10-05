/**
 * 本例演示了BaseExpandableListAdapter的用法：
 * 1)定义BaseExpandableListAdapter的子类，覆写其相关方法
 * 2)用一维数组准备父项数据，二维数组准备子项数据
 * 3)准备父项和子项的视图，可以是系统的，可以是xml，也可以是代码生成的
 * 4)在覆写方法中使用上述数据和视图
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

import android.app.ExpandableListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

import org.xottys.userinterface.R;

/**
 * Demonstrates expandable lists using a custom {@link ExpandableListAdapter}
 * from {@link BaseExpandableListAdapter}.
 */
public class BaseExpandableActivity extends ExpandableListActivity {
    private static final String TAG  ="BaseExpandableActivity";
    ExpandableListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        mAdapter = new MyExpandableListAdapter();
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());

        //父项点击监听事件
        getExpandableListView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView var1, View var2, int var3, long var4)
            {
                Toast.makeText(BaseExpandableActivity.this, "第"+var3+"个父项被点击了", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "OnGroupClick");
                return false;
            }
        });
        //子项点击监听事件
        getExpandableListView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(BaseExpandableActivity.this, "第"+i1+"个子项被点击了", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onChildClick");
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
        menu.add(0, 0, 0, R.string.expandable_list_sample_action);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();
        
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
            Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        
        return false;
    }


    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        private String[] groups = { "People Names", "Dog Names", "Cat Names", "Fish Names" };
        private String[][] children = {
                { "Arnold", "Barry", "Chuck", "David" },
                { "Ace", "Bandit", "Cha-Cha", "Deuce" },
                { "Fluffy", "Snuggles" },
                { "Goldy", "Bubbles" }
        };

        //用代码生成Item的视图
        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 100);
            TextView textView = new TextView(BaseExpandableActivity.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPaddingRelative(36, 0, 0, 0);
            // Set the text alignment
            textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            return textView;
        }

        // 获获取子项数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        // 获获取子项视图
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setTextColor(Color.GRAY);
            textView.setTextSize(15);
            textView.setPadding(60,0,0,0);
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }
        // 获获取父项数据
        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }
        @Override
        public int getGroupCount() {
            return groups.length;
        }
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        // 获获取父项视图
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
