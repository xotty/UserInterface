/**
 * 本例演示了ImageSwitcher和TextSwitcher的基本用法，具体使用步骤如下：
 * 1）在xml中用标签<ImageSwitcher>或<TextSwitcher>定义,用view.findViewById(R.id.xx_switcher)来加载
 * 2）在ViewFactory中为其定义ImageView或TextView模版，用mSwitcher.setFactory加载该模版
 * 3）设置进入和退出动画：
 *    mySwitcher.setInAnimation(animation)；
 *    mySwitcher.setOutAnimation(animation)；
 * 4）给ImageSwitcher和TextSwitcher设置具体内容
 *    mImageSwitcher.setImageResource();
 *    mTextSwitcher.setText();
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.view_animator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.xottys.userinterface.animation.R;


public class ImageTextSwitcherFragment extends Fragment {
    private int[]        imageIDs;
    private int          imageCount,currenImageNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIDs = new int[] { R.drawable.bird1,
                R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;
        currenImageNumber=0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_imagetextswitcher, null);
        final ImageSwitcher mImageSwitcher=view.findViewById(R.id.imageswitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                //创建ImageView对象
                ImageView imageView = new ImageView(getActivity());
                //设置图片居中显示
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //ImageSwitcher必须返回ImageView对象
                return imageView; }
        });
        //给ImageSwitcher设置具体内容（初值）
        mImageSwitcher.setImageResource(imageIDs[0]);

        final TextSwitcher mTextSwitcher=view.findViewById(R.id.textswitcher);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                //创建TextView对象
                TextView textView = new TextView(getActivity());
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(80);
                //TextSwitcher必须返回TextView对象
                return textView;
            }
        });
        //给TextSwitcher设置具体内容（初值）
        mTextSwitcher.setText("1");

        Button btn_next =  view.findViewById(R.id.next);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (currenImageNumber < imageCount - 1) {
                currenImageNumber++;
                //设置动画，ImageSwitcher的动画已在xml中定义
                mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                                android.R.anim.slide_in_left));
                mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                                android.R.anim.slide_out_right));
                //给ImageSwitcher设置具体内容
                mImageSwitcher.setImageResource(imageIDs[currenImageNumber]);
                //给TextSwitcher设置具体内容
                mTextSwitcher.setText(currenImageNumber+1+"");
               }
            }
        });

        Button btn_prev = view.findViewById(R.id.prev);
        btn_prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (currenImageNumber >0) {
                    currenImageNumber--;
                    //设置动画，ImageSwitcher的动画已在xml中定义
                    mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(),
                            R.anim.slide_in_right));
                    mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),
                            R.anim.slide_out_left));
                    //设置内容
                    mImageSwitcher.setImageResource(imageIDs[currenImageNumber]);
                    mTextSwitcher.setText(currenImageNumber+1+"");
                }

            }
        });

        return view;
    }

}