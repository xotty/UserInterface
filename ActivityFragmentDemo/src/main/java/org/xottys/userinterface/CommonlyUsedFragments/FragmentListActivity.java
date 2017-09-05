/**
 * 本例演示了ListFragment的基本用法：
 * 1)设置数据和Adapter
 * 2)设置点击事件
 * 3)加载Fragment(此步骤本例简化成直接用MyListFragment替换android.R.id.content)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentListActivity
 * <br/>Date:Sep，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.CommonlyUsedFragments;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FragmentListActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new MyListFragment()).commit();
    }

    //内嵌的ListFragment
    public static class MyListFragment extends ListFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //为ListFragment准备数据
            List ls= new ArrayList<String>();
            ls.add("DialogFragmentDemo");
            ls.add("WebViewFragmentDemo");
            ls.add("PrefrencesFragmentDemo");

            //为ListFragment设置Adapter，
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,ls));
        }

        //当用户点击某列表项时回调该方法，跳转到相应的Activity去
        @Override
        public void onListItemClick(ListView listView
                , View view, int position, long id)
        {
            super.onListItemClick(listView, view, position, id);
            Intent intent=null;
            switch (position) {
                case 0:
                    intent = new Intent(getActivity(), FragmentDialogActivity.class);
                    break;
                case 1:
                    intent = new Intent(getActivity(), FragmentWebViewActivity.class);
                    break;
                case 2:
                    intent = new Intent(getActivity(), FragmentPreferencesActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}