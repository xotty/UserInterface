/**
 * 本例演示了ListView的下列两个特色：
 * 1)setTextFilterEnabled的作用，输入文字时会自动匹配输入的内容
 * 2)android.R.layout.simple_list_item_activated_1的作用，点击Item后，背景颜色会改变
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
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A list view where the last item the user clicked is placed in
 * the "activated" state, causing its background to highlight.
 */
public class ActivatedItemsActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use the built-in layout for showing a list item with a single
        // line of text whose background is changes when activated.
        setListAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1, mStrings));
        getListView().setTextFilterEnabled(true);
        
        // Tell the list view to show one checked/activated item at a time.
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        // Start with first item activated.
        // Make the newly clicked item the currently selected one.
        getListView().setItemChecked(0, true);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Make the newly clicked item the currently selected one.
        getListView().setItemChecked(position, true);
    }

    private String[] mStrings = Cheeses.sCheeseStrings;
}
