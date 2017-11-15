/**
 * 本例演示了基本型Listview及其四种常用Adapter的使用方法
 * 1)不用Adapter，直接获取数组资源文件
 * 2)ArrayAdapter，单行TextView或其子类：文本，按钮、复选框.....
 * 3)SimpleAdapter，多行任意数据类型，可以带图片等
 * 4)SimpleCursorAdapter，数据准备用Cursor代替ArrayList，其它功能和用法与3)相同
 * 5)BaseAdapter，本身是抽象类，需要实例化其子类（匿名的也行），
 * 子类中要重写getCount(), getItem(), getItemId()和getView()方法，然后使用
 * 其中最常用的2）3）实现步骤为：
 * 1）用Array或ArrayList定义要呈现的数据（文本、图片等）
 * 2）定义每一行Item的视图样式（xml布局文件或系统自带的ListItem布局）
 * 3）创建ArrayAdapter/SimpleAdapter，在其中将数据和视图组装在一起
 * 4）ListView使用创建好的Adapter
 * 5）ListView相关监听事件的定义和处理
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xottys.userinterface.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class BasicAdapterViewActivity extends Activity {
    private static final String TAG = "BasicAdapterView";
    boolean reachToBottom;
    private ArrayList<String> strArr = new ArrayList<>();
    private String[] names = new String[]
            {"虎头", "弄玉", "李清照", "李白"};
    private String[] descs = new String[]
            {"可爱的小孩", "一个擅长音乐的女孩"
                    , "一个擅长文学的女性", "浪漫主义诗人"};
    private int[] imageIds = new int[]
            {R.drawable.tiger, R.drawable.nongyu
                    , R.drawable.qingzhao, R.drawable.libai};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //直接在布局文件中用android:entries定义了第一组Listview的数据来源
        setContentView(R.layout.activity_basicadapterview);

        //ArrayAdapter-1---------------------------------------------------------------------------
        //可以呈现单行文本、按钮、复选框等TextView类型的数据
        ListView list1 = (ListView) findViewById(R.id.list1);
        //准备数据
        String[] arr1 = new String[]{"唐僧","孙悟空", "猪八戒","沙和尚"};
        //将数据和Item视图包装为ArrayAdapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, arr1);
        /*上面使用了系统视图，也可以替换成自定义试图，如(this, R.layout.array_item, arr1);
         还可以换成下列系统视图 ：android.R.layout.simple_list_item_single_choice
                             android.R.layout.simple_list_item_mutiple_choice
                             android.R.layout.simple_list_item_checked
        list1.setItemsCanFocus(false);
        list1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);*/

        // 为ListView设置Adapter
        list1.setAdapter(adapter1);
        //设置自适应行高度，以便与ScrollView兼容
        setListViewHeightBasedOnChildren(list1);
        //ArrayAdapter-2---------------------------------------------------------------------------
        //这里呈现的是CheckedTextView
        final ListView list2 = (ListView) findViewById(R.id.list2);
        final String[] arr2 = {"Java", "Android","Kotlin","Objective-C", "Swift"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>
                (this, R.layout.checked_item, arr2);
        list2.setAdapter(adapter2);
        setListViewHeightBasedOnChildren(list2);
        //在单击事件事件监听器中实现CheckedTextView的点击切换效果
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //第position项被单击时激发该方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取点击行Item的视图
                CheckedTextView tvCheck = (CheckedTextView) parent.getChildAt(position);

                if (!tvCheck.isChecked()) {
                    tvCheck.toggle();
                    for (int i = 0; i < arr2.length; i++) {
                        if (i != position) {
                            ((CheckedTextView) parent.getChildAt(i)).setChecked(false);
                        }
                    }
                }
            }
        });
        // SimpleAdapter---------------------------------------------------------------------------
        // 可以在ListView中呈现任意布局的视图
        // 创建一个List集合，List集合的元素是Map，在这里放入要呈现的各项图文数据
        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("header", imageIds[i]);
            listItem.put("personName", names[i]);
            listItem.put("descp", descs[i]);
            listItems.add(listItem);
        }

        //创建SimpleAdapter，将数据和视图关联起来
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
           android.R.layout.simple_list_item_2,
                new String[]{"personName", "descp"},
           new int[]{android.R.id.text1, android.R.id.text2});
           /*也可以换成自定义的布局，例如：
           R.layout.simple_item,
           new String[]{"personName", "header", "descp"},
           new int[]{R.id.name, R.id.header, R.id.desc});*/
        ListView list = (ListView) findViewById(R.id.mylist1);
        list.setAdapter(simpleAdapter);
        setListViewHeightBasedOnChildren(list);

        // SimpleCursorAdapter---------------------------------------------------------------------
        // 数据来自直接数据库查询结果的Cursor
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().toString() + "/myDB", null);
        //创建表
        db.execSQL("create table IF NOT EXISTS cursorAdapterTest(_id integer"
                + " primary key autoincrement,"
                + " name text not null,"
                + " header int not null,"
                + " descp text)");
        //插入数据
        db.execSQL("replace into cursorAdapterTest values(1,'武松',"+imageIds[0]+",'打虎英雄'),(2,'林冲'," +
                ""+imageIds[1]+",'豹子头，八十万禁军教头'),(3,'李逵',"+imageIds[2]+",'黑旋风')");
        //sql语句查询数据库得到Cursor
        String sql = "select _id,name,header,descp from cursorAdapterTest";
        Cursor cursor = db.rawQuery(sql,null);
        // 用Cursor构造适配器
        // 最后一个参数flags是一个标识，标识当数据改变调用onContentChanged()的时候，是
        // 否通知ContentProvider数据的改变，如果无需监听ContentProvider的改变，则可以传0。
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.simple_item, cursor, new String[]{"name", "header", "descp"},
                new int[]{R.id.name, R.id.header, R.id.desc}, 0);

        ListView listView = (ListView)findViewById(R.id.mylist2);
        listView.setAdapter(simpleCursorAdapter);
        setListViewHeightBasedOnChildren(listView);

        //BaseAdapter,完全自定义Listview内容和样式---------------------------------------------------------------------------
        //准备数据
        for (int i = 0; i < 20; i++) {
            strArr.add("第" + (i + 1) + "个列表项");
        }

        //设置该Listview的高度
        final ListView myList = (ListView) findViewById(R.id.mylist3);
        ViewGroup.LayoutParams params = myList.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = 800;
        myList.setLayoutParams(params);
        //定义BaseAdapter子类并重写相关方法
        final BaseAdapter adapter = new BaseAdapter() {

            //返回有多少个item
            @Override
            public int getCount() {
                // 指定一共包含20个选项
                Log.i(TAG, "getCount");
                int count;
                if (strArr.size() == 0)
                    count = 20;
                else
                    count = strArr.size();

                return count;
            }
            //获取item的数据对象
            @Override
            public Object getItem(int position) {
                return strArr.get(position);
            }

            //获取item的对应索引值
            @Override
            public long getItemId(int position) {
                return position;
            }

            //获取每个item对应的布局对象
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
               // 创建一个LinearLayout，并向其中添加两个组件
               LinearLayout line = new LinearLayout(BasicAdapterViewActivity.this);
               ImageView image = new ImageView(BasicAdapterViewActivity.this);
               image.setImageResource(android.R.drawable.ic_btn_speak_now);
               TextView text = new TextView(BasicAdapterViewActivity.this);
               text.setText(strArr.get(position));
               text.setTextSize(20);
               text.setTextColor(Color.RED);
               line.addView(image);
               line.addView(text);
               line.setPadding(40, 0, 0, 0);
                // 返回LinearLayout实例
                return line;
            }
        };
        myList.setAdapter(adapter);

        //为ListView的列表项绑定单击事件监听器
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 第position项被单击时激发该方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                showMessage(strArr.get(position) + "被单击了");
                Log.i(TAG, strArr.get(position) + "被单击了");
            }
        });
        //为ListView的列表项绑定长按击事件监听器
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // 第position项被单击时激发该方法
            @Override
            public boolean onItemLongClick(AdapterView<?> var1, View var2, int var3, long var4) {
                showMessage(strArr.get(var3) + "被长按了");
                Log.i(TAG, strArr.get(var3) + "被长按了");
                return true;
            }
        });
        // 为ListView的列表项的绑定选中事件监听器
        myList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 第position项被选中时激发该方法
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                showMessage(strArr.get(position) + "被选中了");
                Log.i(TAG, strArr.get(position) + "被选中了");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "没有任何项被选中");
            }
        });
        // 为ListView的列表项的绑定滚动状态改变事件监听器
        myList.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.i(TAG, "onScrollStateChanged:" + scrollState);
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        Log.i(TAG, "用户在手指离开屏幕之前，由于滑了一下，视图仍然依靠惯性继续滑动");
                        break;
                    //每次滚动到底部时新增一行列表项
                    case SCROLL_STATE_IDLE:
                        Log.i(TAG, "视图已经停止滑动");
                        if (reachToBottom) {    //刷新
                            int ct = strArr.size();
                            strArr.add("新增---第" + (ct + 1) + "个列表项");
                            adapter.notifyDataSetChanged();
                            showMessage("ListView在底部新增一行");
                        }
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        Log.i(TAG, "手指没有离开屏幕，视图正在滑动");
                        break;
                }
            }

            // 为ListView的列表项的绑定滚动事件监听器
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i(TAG, "onScroll：Listview滚动中");
                reachToBottom = false;
                //滚动到顶部
                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = myList.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                        Log.d("ListView", "-----滚动到顶部 ------");
                    }
                    //滚动到底部
                } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                    View lastVisibleItemView = myList.getChildAt(myList.getChildCount() - 1);
                    if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == myList.getHeight()) {
                        reachToBottom = true;
                        Log.d("ListView", "------ 滚动到底部 ------");
                    }
                }
            }
        });

        //其它-------------------------------------------------------------------------------------
        //解决listview和ScrollView滚动冲突
        final ScrollView myScrollView = (ScrollView) findViewById(R.id.myScroll);
        myList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                myScrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }


    /**
     * 动态设置ListView的高度,用于嵌套在ScrollView中时能正常显示
     *
     * @param listView 需要设置自动行高的Listview
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void showMessage(final String msg) {
        Toast.makeText(BasicAdapterViewActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}