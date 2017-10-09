/**
 * 本例演示了自定义Adapter的基本用法：
 * 1)覆写BaseAdapter的四个基本方法
 * 2)在其中getView()中定义每一行的呈现方式，本例使用了自定义的view，以Textview的显示和隐藏实现了展开的收缩的效果
 * 3)在其中getView()中使用convertView来优化显示性能，而不是每次都完全重新生成每一行的View
 * <p>
 * <br/>Copyright (C), 2017-2018, Google
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 * @author Google Inc.
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class CustomerAdapterActivity1 extends ListActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Use our own list adapter
        setListAdapter(new SpeechListAdapter(this));
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
       ((SpeechListAdapter)getListAdapter()).toggle(position);
    }

    /**
     * A sample ListAdapter that presents content
     * from arrays of speeches and text.
     *
     */
    private class SpeechListAdapter extends BaseAdapter {
        private SpeechListAdapter(Context context)
        {
            mContext = context;
        }


        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return Shakespeare.TITLES.length;
        }

        /**
         * Since the data comes from an array, just returning
         * the index is sufficent to get at the data. If we
         * were using a more complex data structure, we
         * would return whatever object represents one
         * row in the list.
         *
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a SpeechView to hold each row.
         * @see android.widget.ListAdapter#getView(int, View, ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            SpeechView sv;
            if (convertView == null) {
                sv = new SpeechView(mContext, Shakespeare.TITLES[position],
                        Shakespeare.DIALOGUE[position],mExpanded[position]);
            } else {
                sv = (SpeechView)convertView;
                sv.setTitle(Shakespeare.TITLES[position]);
                sv.setDialogue(Shakespeare.DIALOGUE[position]);
                sv.setExpanded(mExpanded[position]);
            }

            return sv;
        }

        private void toggle(int position) {
            mExpanded[position] = !mExpanded[position];
            notifyDataSetChanged();
        }

        /**
         * Remember our context so we can use it when constructing views.
         */
        private Context mContext;

        private boolean[] mExpanded =
        {
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        };
    }

    /**
     * We will use a SpeechView to display each speech. It's just a LinearLayout
     * with two text fields.
     *
     */
    private class SpeechView extends LinearLayout {
        public SpeechView(Context context, String title, String dialogue, boolean expanded) {
            super(context);

            this.setOrientation(VERTICAL);

            // Here we build the child views in code. They could also have
            // been specified in an XML file.

            mTitle = new TextView(context);
            mTitle.setText(title);
            mTitle.setTextSize(18);
            mTitle.setTextColor(Color.CYAN);
            addView(mTitle, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            mDialogue = new TextView(context);
            mDialogue.setText(dialogue);
            addView(mDialogue, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            
            mDialogue.setVisibility(expanded ? VISIBLE : GONE);
        }
        
        /**
         * Convenience method to set the title of a SpeechView
         */
        public void setTitle(String title) {
            mTitle.setText(title);
        }
        
        /**
         * Convenience method to set the dialogue of a SpeechView
         */
        public void setDialogue(String words) {
            mDialogue.setText(words);
        }
        
        /**
         * Convenience method to expand or hide the dialogue
         */
        public void setExpanded(boolean expanded) {
            mDialogue.setVisibility(expanded ? VISIBLE : GONE);
        }
        
        private TextView mTitle;
        private TextView mDialogue;
    }
}
