/**
 * 本例演示了ViewStub的基本用法：
 * 1）在xml中以<ViewStub>标签定义，用android:layout="@layout/viewstub"定义关联布局文件
 * 2）定义viewstub.xml布局文件
 * 3）需要时加载ViewStub：viewStub.inflate()或viewStub.setVisibility(View.VISIBLE),前者只能执行一次，执行后会自动
 * 删除viewstub，而用其关联的layout代替，第二次执行inflate会报异常；后者可正常可执行多次，只有第一次会调用inflate。
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Jan，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.MiscWidgetDemo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xottys.userinterface.R;

public class Fragment1 extends Fragment {
    private ViewStub viewStub;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_miscwidgetfragment1, null);
        viewStub = (ViewStub) view.findViewById(R.id.vs);

        Button inflate_btn = (Button) view.findViewById(R.id.btn1);
        inflate_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    //激活ViewStub，用其关联的布局替换掉ViewStub，同时将替换后的View赋值给stubview，只能执行一次
                    View stubview = viewStub.inflate();
                    textView = (TextView) stubview.findViewById(R.id.stub_tv);
                    textView.setText("Empty DATA!!!");
                } catch (Exception e) {
                    //第二次以后点击按钮会在这儿报异常
                    textView.setText("再次启动inflate会报错：" + e.getMessage());
                }

				/*代替inflate的另一种方法，可以执行多次
                viewStub.setVisibility(View.INVISIBLE);
				textView  = (TextView) view.findViewById(R.id.stub_tv);*/
            }

        });

        Button setdata_btn = (Button) view.findViewById(R.id.btn2);
        setdata_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (textView != null)
                    textView.setText("DATA!!!");
                else
                    Toast.makeText(getContext(), "请先点击inflate按钮以提换ViewStub", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
