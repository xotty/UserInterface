/**
 * 本例演示了ListView的下列要点：
 * 1)CHOICE_MODE_MULTIPLE_MODAL,一旦有一个item被选中，即进入到多选状态，item的onclick事件被屏蔽
 * 2)ActionMode的使用，自动启动，可直接操作
 * 启用多选操作，只有两种办法：
 * 1)长按Item
 * 2)主动调用ListView的setItemChecked(int position, boolean value)方法选中一个item
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
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.xottys.userinterface.R;

/**
 * This demo illustrates the use of CHOICE_MODE_MULTIPLE_MODAL, a.k.a. selection mode on ListView.
 */
public class SelectionModeActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv.setMultiChoiceModeListener(new ModeCallback());
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, Cheeses.sCheeseStrings));
                //用android.R.layout.simple_list_item_activated_1可以换一种新的选择显示方式
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getActionBar().setSubtitle("Long press to start selection");
    }
    
    private class ModeCallback implements ListView.MultiChoiceModeListener {
        //当ActionMode第一次被创建的时候调用

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.list_select_menu, menu);
            mode.setTitle("Select Items");
            setSubtitle(mode);
            //返回true表示action mode会被创建  false表示action mode会退出
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        //当ActionMode被点击时调用
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(SelectionModeActivity.this, "Shared " + getListView().getCheckedItemCount() +
                        " items", Toast.LENGTH_SHORT).show();
                mode.finish();
                break;
            default:
                Toast.makeText(SelectionModeActivity.this, "Clicked " + item.getTitle(),
                        Toast.LENGTH_SHORT).show();
                break;
            }
            return true;
        }

        //当ActionMode退出销毁时调用
        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }

        //Item点击时回调此方法
        @Override
        public void onItemCheckedStateChanged(ActionMode mode,
                int position, long id, boolean checked) {
            setSubtitle(mode);
        }

        private void setSubtitle(ActionMode mode) {
            final int checkedCount = getListView().getCheckedItemCount();
            switch (checkedCount) {
                case 0:
                    mode.setSubtitle(null);
                    break;
                case 1:
                    mode.setSubtitle("One item selected");
                    break;
                default:
                    mode.setSubtitle("" + checkedCount + " items selected");
                    break;
            }
        }
    }
}
