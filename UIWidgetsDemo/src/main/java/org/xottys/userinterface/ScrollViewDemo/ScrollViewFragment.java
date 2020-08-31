/**
 * 本例用于演示了ScrollView的各种用法
 * 1)标准的水平和垂直ScrollView
 * 2)各种ScrollBar的样式
 * 3)自定义ScrollView(用水平ScrollView模拟Gallery的效果）
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.ScrollViewDemo;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class ScrollViewFragment extends Fragment {
    private MyHorizontalScrollView mHorizontalScrollView;
    private MyHorizontalScrollViewAdapter mAdapter;
    private ImageView mImg;

    //Fragment带参数的构造器只能用这种方法定义
    static ScrollViewFragment newInstance(int tag) {
        ScrollViewFragment frag = new ScrollViewFragment();
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

        //根据参数将背景设为不同颜色,这里同时展示了4种常用颜色代码设置方法
        View view = null;
        switch (tag) {
            //标准的水平和垂直ScrollView
            case 1:
                view = inflater.inflate(R.layout.fragment_scrollview1, container, false);
                LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
                //循环构造多组TextView和Button，在垂直ScrollView中显示
                for (int i = 2; i < 30; i++) {
                    TextView textView = new TextView(getContext());
                    textView.setText("Text View " + i);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layout.addView(textView, p);

                    Button buttonView = new Button(getContext());
                    buttonView.setText("Button " + i);
                    layout.addView(buttonView, p);
                }
                break;
            //各种ScrollBar的ScrollView
            case 2:
                view = inflater.inflate(R.layout.fragment_scrollbar, container, false);
                view.findViewById(R.id.view3).setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                break;
            //用自定义水平ScrollView模拟画廊的效果
            case 3:
                // 定义一个装画廊图片的数组
                final int[] images = new int[]{
                        R.drawable.lijiang,
                        R.drawable.qiao,
                        R.drawable.shuangta,
                        R.drawable.shui,
                        R.drawable.xiangbi,
                };
                // 定义一个装图片名称的数组
                String[] titleArray = new String[]{"漓江", "桥", "双塔", "水", "象鼻山"};

                view = inflater.inflate(R.layout.fragment_scrollview2, container, false);
                Button bt_last = (Button) view.findViewById(R.id.bt_Left);
                Button bt_next = (Button) view.findViewById(R.id.bt_Right);
                mImg = (ImageView) view.findViewById(R.id.content);
                mImg.setImageResource(images[0]);
                mHorizontalScrollView = (MyHorizontalScrollView) view.findViewById(R.id.horizontalScrollView);

                //上一张按钮点击
                bt_last.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHorizontalScrollView.scrollToRight();
                    }
                });
                //下一张按钮点击
                bt_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHorizontalScrollView.scrollToLeft();
                    }
                });

                //构造自定义适配器HorizontalScrollViewAdapter，将图片和名称数据传入
                mAdapter = new MyHorizontalScrollViewAdapter(getContext(), images, titleArray);
                mHorizontalScrollView.setAdapter(mAdapter);

                //添加滚动回调，将大图设为当前画框第一张图
                mHorizontalScrollView
                        .setOnImageChangeListener(new MyHorizontalScrollView.OnImageChangeListener() {
                            @Override
                            public void onCurrentImgChanged(int position, View viewIndicator) {
                                mImg.setImageResource(images[position]);
                                viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
                            }
                        });

                //添加点击回调,点击显示大图
                mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        mImg.setImageResource(images[position]);
                        view.setBackgroundColor(Color.parseColor("#AA024DA4"));
                    }
                });
                break;
        }
        return view;
    }
}

