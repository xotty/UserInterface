/**
 * 本例演示ListActivity(自带Listview)的基本用法
 * 1)onCreate中构造listview适配器
 * 2)onListItemClick中实现点击事件
 * 显示内容可以是文本（单行或双行）、单选按钮、单选框、多选框
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentLifecycle
 * <br/>Date:Aug，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.CommonlyUsedActivities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.定义数据源
        String[] data = {"老师", "学生", "课桌", "书本", "铅笔", "橡皮", "粉笔", "黑板", "凳子", "扫帚", "簸箕", "炉子", "窗花", "讲台", "教鞭", "小红花", "花瓶"};
        /*需要显示两行文本时的数据源
        List listData = new ArrayList();
        for (int index = 0; index < 50; ++index) {
            Hashtable table = new Hashtable();
            table.put("name", "name" + index);
            table.put("desc", "desc" + index);
            listData.add(table);
        }*/

        //2.构建适配器:使用默认的布局显示单行文本
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);

        /*2.适配器:使用默认样式显示单选按钮
          ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,data);*/
        /*2.适配器:使用默认样式显示单选框
          ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,data);*/
        /*2.适配器:使用默认样式显示多选框
          ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,data);*／
        /*2.显示两行文本时的Adapter
          @SuppressWarnings("unchecked")
          SimpleAdapter simpleAdapter = new SimpleAdapter(this,
          listData,android.R.layout.simple_list_item_2,
          new String[]{"name", "desc"},
          new int[]{android.R.id.text1, android.R.id.text2});
         */

        //3.设置ListView的属性
        ListView listView = this.getListView();
        listView.setItemsCanFocus(true);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //4.绑定adapter
         setListAdapter(arrayAdapter);
         //setListAdapter(simpleAdapter);
    }

    //实现点击事件
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, "点中了第" + id + "个", Toast.LENGTH_LONG).show();
    }
}
