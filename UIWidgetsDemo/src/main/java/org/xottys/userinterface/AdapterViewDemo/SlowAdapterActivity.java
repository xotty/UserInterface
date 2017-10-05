/**
 * 本例演示了自定义Adapter的延迟加载数据方法：
 * 1)getViw中只给出虚拟数据：Loding...
 * 2)onScrollStateChanged状态为Idle时，填入真实数据
 * <p>
 * <br/>Copyright (C), 2017-2018, wcy
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 * @author wcy
 * @version 1.0
 */

package org.xottys.userinterface.AdapterViewDemo;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xottys.userinterface.R;


/**
 * Demonstrates how a list can avoid expensive operations during scrolls or flings. In this
 * case, we pretend that binding a view to its data is slow (even though it really isn't). When
 * a scroll/fling is happening, the adapter binds the view to temporary data. After the scroll/fling
 * has finished, the temporary data is replace with the actual data.
 *
 */
public class SlowAdapterActivity extends ListActivity implements ListView.OnScrollListener {

    private TextView mStatus;

    private boolean mBusy = false;

    /**
     * Will not bind views while the list is scrolling
     *
     */
    private class SlowAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public SlowAdapter(Context context) {
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return mStrings.length;
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         *
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         *
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         *      android.view.ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView text;
            
            if (convertView == null) {
                text = (TextView)mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                text = (TextView)convertView;
            }

            if (!mBusy) {
                text.setText(mStrings[position]);
                // Null tag means the view has the correct data
                text.setTag(null);
            } else {
                text.setText("Loading...");
                // Non-null tag means the view still needs to load it's data
                text.setTag(this);
            }

            return text;
        }
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slowadapter);
        mStatus = (TextView) findViewById(R.id.status);
        mStatus.setText("~~~~~Idle~~~~~");
        
        // Use an existing ListAdapter that will map an array
        // of strings to TextViews
        setListAdapter(new SlowAdapter(this));
        
        getListView().setOnScrollListener(this);
    }
    
    
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
    }
    

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
        case OnScrollListener.SCROLL_STATE_IDLE:
            mBusy = false;
            
            int first = view.getFirstVisiblePosition();
            int count = view.getChildCount();
            for (int i=0; i<count; i++) {
                TextView t = (TextView)view.getChildAt(i);
                if (t.getTag() != null) {
                    t.setText(mStrings[first + i]);
                    t.setTag(null);
                }
            }
            
            mStatus.setText("~~~~~Idle~~~~~");
            break;
        case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
            mBusy = true;
            mStatus.setText("~~~~~Touch Scroll~~~~~");
            break;
        case OnScrollListener.SCROLL_STATE_FLING:
            mBusy = true;
            mStatus.setText("~~~~~Fling~~~~~");
            break;
        }
    }
    private static final String[] mStrings = Cheeses.sCheeseStrings;

}
