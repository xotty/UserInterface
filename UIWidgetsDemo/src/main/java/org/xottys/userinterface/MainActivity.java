package org.xottys.userinterface;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.xottys.userinterface.AdapterViewDemo.AdapterViewActivity;
import org.xottys.userinterface.AdvancedViewGroup.AdvancedWidgetActivity;
import org.xottys.userinterface.DialogDemo.DialogActivity;
import org.xottys.userinterface.MenuToolbarDemo.MenuToolbarActivity;
import org.xottys.userinterface.MiscWidgetDemo.MiscWidgetActivity;
import org.xottys.userinterface.ScrollViewDemo.ScrollViewActivity;

public class MainActivity extends LauncherActivity {
    //定义要跳转的各个Activity的名称
    String[] names = {"TextView Demo", "ImageView Demo", "ProgressBar Demo", "CustomView Demo",
            "ScrollView Demo", "Picker Demo", "Menu/Toolbar Demo", "MiscWidget Demo", "WebView Demo",
            "AdaterView Demo", "AdvancedWidget Demo", "Dialog Demo"};

    //定义各个Activity对应的实现类
    Class<?>[] clazzs = {TextViewActivity.class, ImageViewActivity.class, ProgressBarActivity.class, CustomViewActivity.class,
            ScrollViewActivity.class, PickerActivity.class, MenuToolbarActivity.class, MiscWidgetActivity.class, WebViewActivity.class,
            AdapterViewActivity.class, AdvancedWidgetActivity.class, DialogActivity.class,};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //定义BaseAdapter子类并重写相关方法
        final BaseAdapter adapter = new BaseAdapter() {

            //返回有多少个item
            @Override
            public int getCount() {

                return names.length;

            }

            //获取item的数据对象
            @Override
            public Object getItem(int position) {
                return names[position];
            }

            //获取item的对应索引值
            @Override
            public long getItemId(int position) {
                return position;
            }

            //获取每个item对应的布局对象
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listitem_main, parent, false);

                TextView text = (TextView) convertView.findViewById(R.id.tv);
                text.setText(names[position]);
                if (position <= 3)
                    text.setBackgroundResource(R.drawable.selector_listitem_simpleview);
                else if (position <= 8)
                    text.setBackgroundResource(R.drawable.selector_listitem_simpleviewgroup);
                else if (position <= 10)
                    text.setBackgroundResource(R.drawable.selector_listitem_advancedviewgroup);
                else
                    text.setBackgroundResource(R.drawable.selector_listitem_dialog);

                return convertView;
            }
        };

        setListAdapter(adapter);
    }

    //将clazzs数组直接放入，系统将按顺序对应listview上的每一行，行点击后将跳转相应Intent的Activity
    @Override
    public Intent intentForPosition(int position) {
        return new Intent(MainActivity.this, clazzs[position]);
    }
}
