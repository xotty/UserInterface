/**
 * 本例演示了AdapterViewFlipper的基本用法，具体使用步骤如下：
 * 1）在xml中用标签<AdapterViewFlipper>定义,用view.findViewById(R.id.adapter_view_flipper)来加载
 * 2）在Adapter中设置具体View的内容， 用myAdapterViewFlipper.setAdapter加载
 * 3）设置进入和退出动画：
 *    myAdapterViewFlipper.setInAnimation(animation)；
 *    myAdapterViewFlipper.setOutAnimation(animation)；
 *    也可以在xml中定义
 * 4）启动切换：
 *    myAdapterViewFlipper.startFlipping()----自动循环切换
 *    或
 *    myAdapterViewFlipper.showNext()
 *    myAdapterViewFlipper.showPrevious()----手动切换
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.adapterview_animator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;

public class AdapterViewFlipperFragment extends Fragment {
    private int[]        imageIDs;
    private int          imageCount;
    private AdapterViewFlipper flipper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIDs = new int[] { R.drawable.bird1, R.drawable.cat,
                R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_adapterviewflipper, null);
        //加载AdapterViewFlipper
        flipper=view.findViewById(R.id.flipper);
        //设置动画
        flipper.setInAnimation(getContext(),android.R.animator.fade_in);
        flipper.setOutAnimation(getContext(),android.R.animator.fade_out);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.autoplay:
                         //自动切换
                         flipper.startFlipping();
                         break;
                    case R.id.prev:

                        if (flipper.isFlipping())
                            flipper.stopFlipping();
                        //手动切换
                        flipper.showPrevious();

                    break;
                    case R.id.next:

                        if (flipper.isAutoStart())
                            flipper.stopFlipping();

                        //手动切换
                        flipper.showNext();
                    break;
                }

            }
        };

        view.findViewById(R.id.next).setOnClickListener(listener);
        view.findViewById(R.id.prev).setOnClickListener(listener);
        view.findViewById(R.id.autoplay).setOnClickListener(listener);

        BaseAdapter adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return imageCount;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //设置AdapterViewFlipper的内容
                if (convertView==null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_vpanim, null);
                    ImageView imgview=convertView.findViewById(R.id.section_label);
                    imgview.setImageResource(imageIDs[position]);
                }
                return convertView;
            }
        };

        //加载Adapter，作为AdapterViewFlipper的内容
        flipper.setAdapter(adapter);

        return view;
    }
}