/**
 * 本例演示了常用AdapterView的各种用法：
 * 1)ListView的四种常用Adapter（ArrayAdapter、SimpleAdapter、SimpleCursorAdapter、BaseAdapter）及其各种变化
 * 2)Spinner
 * 3)GridView
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:AdapterViewActivity
 * <br/>Date:Oct，2017
 * @author xotty@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterViewActivity extends ExpandableListActivity {
    private static final String TAG = "AdapterViewActivity";
    //定义各个Activity对应的实现类
    Class<?>[] clazzs = {BasicAdapterViewActivity.class, AdvancedActivatedItemsActivity.class,
            AdvancedOverlayArrayActivity.class, AdvancedViewBinderActivity.class, AdvancedTranscriptModeActivity.class,
            AdvancedSelectionModeActivity.class, CustomerExpandableActivity.class, CustomerGroupActivity.class,
            CustomerSlowActivity.class, CustomerEfficentActivity.class, ExpandableListViewSimpleActivity.class,
            ExpandableListViewCursorTreeActivity.class, ExpandableListViewBaseActivity.class, SpinnerActivity.class,
            GridViewSimpleActivity.class, GridViewBaseActivity.class, GridViewSelectionActivity.class,
            ViewPagerBasicActivity.class, ViewPagerTitleActivity.class, ViewPagerFragmentActivity.class};
    private String[] groupArr = new String[]
            {"Basic AdapterView", "Advanced AdapterView", "Customer AdapterView" ,
              "ExpandableListView","Spinner","GridView","ViewPager"
             };
    private String[][] childArr = new String[][]
            {
              {},
              { "Activated Items", "Overlay Array", "ViewBinder", "Transcript Mode","Selection Mode"},
              { "可伸缩","分组","延迟加载","即时加载" },
              { "SimpleExpandableListAdapter","SimpleCursorTreeAdapter","BaseExpandableListAdapter"},
              {},
              {"SimpleAdapter GridView","BaseAdapter GridView","Selection Mode GridView"},
              {"Basic ViewPager","Title ViewPager","Fragment ViewPager"}
            };

    //将clazzs数组直接放入，系统将按顺序对应listview上的每一行，行点击后将跳转相应Intent的Activity
    public Intent intentForPosition(int position) {
        return new Intent(AdapterViewActivity.this, clazzs[position]);
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ExpandableListAdapter adapter = new BaseExpandableListAdapter()
        {

            @Override
            public Object getChild(int groupPosition, int childPosition)
            {
                return childArr[groupPosition][childPosition];
            }
            @Override
            public long getChildId(int groupPosition, int childPosition)
            {
                return childPosition;
            }

            @Override
            public int getChildrenCount(int groupPosition)
            {
                return childArr[groupPosition].length;
            }

            private TextView getGroupTextView()
            {
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 120);
                TextView textView = new TextView(AdapterViewActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                textView.setPadding(20, 0, 0, 0);
                textView.setTextSize(22);
                textView.setTextColor(Color.WHITE);

                return textView;
            }

            private TextView getChildTextView()
            {
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 120);
                TextView textView = new TextView(AdapterViewActivity.this);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                textView.setPadding(80, 0, 0, 0);
                textView.setTextSize(18);
                textView.setTextColor(Color.LTGRAY);

                return textView;
            }
            @Override
            public View getChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent)
            {
                TextView textView = getChildTextView();
                textView.setText(getChild(groupPosition, childPosition).toString());
                return textView;
            }

            @Override
            public Object getGroup(int groupPosition)
            {
                return groupArr[groupPosition];
            }

            @Override
            public int getGroupCount()
            {
                return groupArr.length;
            }

            @Override
            public long getGroupId(int groupPosition)
            {
                return groupPosition;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent)
            {
                LinearLayout ll = new LinearLayout(AdapterViewActivity.this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                TextView textView = getGroupTextView();
                textView.setText(getGroup(groupPosition).toString());
                ll.addView(textView);
                return ll;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition)
            {
                return true;
            }
            @Override
            public boolean hasStableIds()
            {
                return true;
            }
        };
        //父项点击监听事件
        getExpandableListView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            public boolean onGroupClick (ExpandableListView var1, View var2,int var3, long var4)
            {
                Log.i(TAG, "OnGroupClick:"+var3);
                int l=0;
                for (int i=0;i<var3;i++)
                {
                    l=l+(childArr[i].length==0?1:childArr[i].length);
                }
                if (childArr[var3].length==0)
                    startActivity(intentForPosition(l));
                return false;
            }
        });
        getExpandableListView().setGroupIndicator(null);
        setListAdapter(adapter);
    }

    //子项点击监听事件
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                int childPosition, long id) {
        int l=0;
        for (int i=0;i<groupPosition;i++)
        {
           l=l+(childArr[i].length==0?1:childArr[i].length);
        }
        //跳转到相应Activity
        startActivity(intentForPosition(childPosition+l));
        return false;
    }

    //当子项收缩时回调
    @Override
    public void onGroupCollapse(int groupPosition) {
        Log.i(TAG, "onGroupCollapse: "+groupPosition);
    }

    //当子项展开时回调
    @Override
    public void onGroupExpand(int groupPosition) {
        Log.i(TAG, "onGroupExpand:"+groupPosition);
    }

}
