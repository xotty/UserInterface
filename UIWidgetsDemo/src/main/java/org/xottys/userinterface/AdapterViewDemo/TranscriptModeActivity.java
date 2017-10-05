/**
 * 本例演示了ListView的下列要点：
 * 1)ArrayAdapter一种构造方法，用Adatper.add添加具体内容,Adatper.clear清空
 * 2)transcriptMode属性：新增listview项目行时，disalbe不滚动，normal滚动到可见区最后一行，autoScroll总是滚动到总表最后一行
 * 3)stackFromBottom属性：true从下往上移动  false：从上往下移动
 * 4)setEmptyView方法设置listview内容为空时的显示
 * 5)listview之外可以安排其他控件（如：Button）
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

import android.app.ListActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


import org.xottys.userinterface.R;

public class TranscriptModeActivity extends ListActivity implements OnClickListener, OnKeyListener {

    private EditText mUserText;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.list_12);
        // Tell the list view which view to display when the list is empty
        getListView().setEmptyView(findViewById(R.id.empty));

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        setListAdapter(mAdapter);
        // Wire up the add button to add a new photo
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sendText();
            } });
        // Wire up the clear button to remove all photos
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
            } });

        mUserText = (EditText) findViewById(R.id.userText);
        mUserText.setOnClickListener(this);
        mUserText.setOnKeyListener(this);
    }

    public void onClick(View v) {
        sendText();
    }

    private void sendText() {
        String text = mUserText.getText().toString();

        //将text(用户输入文本)添加到listview中
        mAdapter.add(text);
        mUserText.setText(null);
        mAdapter.notifyDataSetChanged();
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                    sendText();
                    return true;
            }
        }
        return false;
    }
}
