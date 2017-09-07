/**
 * 本例演示ExpandableListActivity(自带ExpandableListview)的基本用法
 * 1)onCreate中构造适配器ExpandableListAdapter
 * 2)onChildClick中实现子项点击事件
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Sep. 2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.CommonlyUsedActivities;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class MyExpandableListActivity extends ExpandableListActivity
{
	private static final String TAG = "ActivitySubclass";
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ExpandableListAdapter adapter = new BaseExpandableListAdapter()
		{
			int[] logos = new int[]
			{
				R.drawable.p,
				R.drawable.z,
				R.drawable.t
			};
 			private String[] armTypes = new String[]
				{ "神族兵种", "虫族兵种", "人族兵种"};
			private String[][] arms = new String[][]
			{
				{ "狂战士", "龙骑士", "黑暗圣堂", "电兵" },
				{ "小狗", "刺蛇", "飞龙", "自爆飞机" },
				{ "机枪兵", "护士MM" , "幽灵" }
			};

			//指定每个子列表项数据
			@Override
			public Object getChild(int groupPosition, int childPosition)
			{
				return arms[groupPosition][childPosition];
			}
			//指定每个子列表项id
			@Override
			public long getChildId(int groupPosition, int childPosition)
			{
				return childPosition;
			}

			//指定每个子列表项数据项数
			@Override
			public int getChildrenCount(int groupPosition)
			{
				return arms[groupPosition].length;
			}

			private TextView getTextView()
			{
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 64);
				TextView textView = new TextView(MyExpandableListActivity.this);
				textView.setLayoutParams(lp);
				textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
				textView.setPadding(36, 0, 0, 0);
				textView.setTextSize(20);
				return textView;
			}

			//设定每个子选项的外观
			@Override
			public View getChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent)
			{
				TextView textView = getTextView();
				textView.setText(getChild(groupPosition, childPosition).toString());
				return textView;
			}

			//指定组数据
			@Override
			public Object getGroup(int groupPosition)
			{
				return armTypes[groupPosition];
			}

			//指定组数
			@Override
			public int getGroupCount()
			{
				return armTypes.length;
			}

			//指定组id
			@Override
			public long getGroupId(int groupPosition)
			{
				return groupPosition;
			}

			//指定每个组外观
			@Override
			public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent)
			{
				LinearLayout ll = new LinearLayout(MyExpandableListActivity.this);
				ll.setOrientation(LinearLayout.HORIZONTAL);
				ImageView logo = new ImageView(MyExpandableListActivity.this);
				logo.setImageResource(logos[groupPosition]);
				ll.addView(logo);
				TextView textView = getTextView();
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
		// 设置该窗口显示列表
		setListAdapter(adapter);
	}

	//当子项被点击时回调
	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
								int childPosition, long id) {
		Log.i(TAG, "onChildClick: "+groupPosition+"----"+childPosition);
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
