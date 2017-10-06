/**
 * 本例演示了Spinner的三种用法：
 * 1)直接在xml中装入数据
 * 2)用ArrayAdapter装数据
 * 3)自定义弹出框样式，ArrayAdapter数据来自资源文件
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

// Need the following import to get access to the app resources, since this
// class is in a sub-package.

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.xottys.userinterface.R;


public class SpinnerActivity extends Activity {

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        // 获取界面布局文件中的Spinner组件
        Spinner s1  = (Spinner) findViewById(R.id.spinner1);
        String[] arr = { "孙悟空", "猪八戒", "唐僧" };
        // 创建ArrayAdapter对象
        ArrayAdapter<String> adapter1= new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arr);
        // 为Spinner设置Adapter
        s1.setAdapter(adapter1);

        Spinner s2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 =ArrayAdapter.createFromResource(this, R.array.planets,
                android.R.layout.simple_spinner_item);
        //设置弹出框样式，可以自定义
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
        s2.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        showToast("Spinner2: position=" + position + " id=" + id);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner2: unselected");
                    }
                });
    }
}
