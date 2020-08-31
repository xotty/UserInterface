/**
 * 本例用于演示了装载Fragment的ViewPager，被FragmentViewPagerActivity调用
 * 1)用static方法定义带参数Fragment构造器
 * 2)将参数传递到onCreateView中以便建立不同视图的Fragment
 * 3)此时不能使用内部嵌套的Fragment
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class ViewPagerFragment  extends Fragment {

    //Fragment带参数的构造器只能用这种方法定义
    static ViewPagerFragment newInstance(int tag)
    {   ViewPagerFragment frag = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt("Tag", tag);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //解析传入的参数
        int tag = getArguments().getInt("Tag");

        View view=inflater.inflate(R.layout.fragment_viewpager, container,false);
        RelativeLayout ll=(RelativeLayout) view.findViewById(R.id.ll);
        TextView tv=(TextView) view.findViewById(R.id.tv);

        //设置每页显示的文本为当前标识编号:tag
        tv.setText(String.valueOf(tag));

        //根据参数将背景设为不同颜色,这里同时展示了4种常用颜色代码设置方法
        int color=0;
        switch (tag) {
            case 1:
                color=Color.BLUE;
                break;
            case 2:
                color=getResources().getColor(R.color.red,null);
                break;
            case 3:
                color=0xFF8A2BE2;
                break;
            case 4:
                color=Color.parseColor("green");
                break;
            case 5:
                color=Color.parseColor("#FF00FFFF");
                break;
        }
        ll.setBackgroundColor(color);
        Log.i("TAG", "onCreateView: "+tag);
        return view;
    }
}

