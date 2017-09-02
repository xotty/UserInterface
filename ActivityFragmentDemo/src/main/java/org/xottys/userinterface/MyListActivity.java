package org.xottys.userinterface;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by changqing on 2017/8/29.
 */

public class MyListActivity extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.数据源
        String[] data = {"老师","学生","课桌","书本","铅笔","橡皮","粉笔","黑板","凳子","扫帚","簸箕","炉子","窗花","讲台","教鞭","小红花","花瓶"};
        //1.数据源:使用默认样式显示两行文本
        List listData = new ArrayList();
        for(int index = 0; index < 50; ++index){
            Hashtable table = new Hashtable();
            table.put("name", "name"+index);
            table.put("desc", "desc"+index);
            listData.add(table);
        }
        //2.适配器:使用默认的布局显示两行文本
        @SuppressWarnings("unchecked")
      //  ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);

        //2.适配器:使用默认样式显示单选按钮
       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,data);
        //2.适配器:使用默认样式显示单选框
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,data);
        //2.适配器:使用默认样式显示多选框
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,data);



        //2.适配器:使用默认的布局显示两行文本
        //@SuppressWarnings("unchecked")

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                listData,
                android.R.layout.simple_list_item_2,
                new String[] {"name", "desc"},
                new int[] {android.R.id.text1, android.R.id.text2});
        //设置ListView的属性
//        ListView listView = this.getListView();
//        listView.setItemsCanFocus(false);
//        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //3.绑定
       // setListAdapter(arrayAdapter);

        setListAdapter(simpleAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this,"点中了第"+id+"个",Toast.LENGTH_LONG).show();
    }
}
