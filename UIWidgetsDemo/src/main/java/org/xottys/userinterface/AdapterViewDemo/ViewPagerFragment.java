
package org.xottys.userinterface.AdapterViewDemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        tv.setText(""+tag);

        //根据参数将背景设为不同颜色
        int color=0;
        switch (tag) {
            case 1:
                color=getResources().getColor(R.color.blue,null);
                break;
            case 2:
                color=getResources().getColor(R.color.red,null);
                break;
            case 3:
                color=getResources().getColor(R.color.green,null);
                break;
        }
        ll.setBackgroundColor(color);
        Log.i("TAG", "onCreateView: "+tag);
        return view;
    }
}

