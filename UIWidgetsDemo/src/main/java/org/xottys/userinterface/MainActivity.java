package org.xottys.userinterface;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.xottys.userinterface.AdapterViewDemo.AdapterViewActivity;
import org.xottys.userinterface.DialogDemo.DialogActivity;
import org.xottys.userinterface.ScrollViewDemo.ScrollViewActivity;

public class MainActivity extends LauncherActivity {
    //定义要跳转的各个Activity的名称
    String[] names = {"TextView Demo", "ImageView Demo", "ProgressBar Demo", "AdaterView Demo", "ScrollView Demo", "Picker Demo", "WebView Demo", "CustomView Demo", "Dialog Demo"};

    //定义各个Activity对应的实现类
    Class<?>[] clazzs = {TextViewActivity.class, ImageViewActivity.class, ProgressBarActivity.class, AdapterViewActivity.class, ScrollViewActivity.class, PickerActivity.class, WebViewActivity.class, CustomViewActivity.class, DialogActivity.class};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将names数组的内容装入Adapter,以便显示在listview中
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);
    }

    //将clazzs数组直接放入，系统将按顺序对应listview上的每一行，行点击后将跳转相应Intent的Activity
    @Override
    public Intent intentForPosition(int position) {
        return new Intent(MainActivity.this, clazzs[position]);
    }
}
