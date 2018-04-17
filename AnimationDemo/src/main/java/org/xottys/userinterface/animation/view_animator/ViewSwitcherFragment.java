/**
 * 本例演示了ViewSwitcher的基本用法，具体使用步骤如下：
 * 1）在xml中用标签<ViewSwitcher>定义,用view.findViewById(R.id.view_switcher)来加载
 * 2）在ViewFactory中为其定义View模版，用mViewSwitcher.setFactory加载该模版
 * 3）设置进入和退出动画：
 *    myViewSwitcher.setInAnimation(animation)；
 *    myViewSwitcher.setOutAnimation(animation)；
 * 4）给ViewSwitcher设置具体内容，用mViewSwitcher.getNextView()获取2）中的模版
 * 5）切换视图：myViewSwitcher.showNext()
 *            myViewSwitcher.showPrevious()
 *    此时会播放3）中定义的动画
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import org.xottys.userinterface.animation.R;


public class ViewSwitcherFragment extends Fragment {
    private int[]        imageIDs;
    private int          imageCount,currentImageNumber;
   private ViewSwitcher mViewSwitcher;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIDs = new int[] { R.drawable.bird1,
                R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_viewswitcher, null);
        mViewSwitcher =  view.findViewById(R.id.view_switcher);
        //加载视图模版
        mViewSwitcher.setFactory(new myViewFactory(this.getLayoutInflater()));
        //设置初始视图
        currentImageNumber=-1;
        nextView();

        Button btn_next =  view.findViewById(R.id.next);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
              nextView();
            }
        });

        Button btn_prev =  view.findViewById(R.id.prev);
        btn_prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
              previousView();
            }
        });
        return view;
    }

    class myViewFactory implements ViewSwitcher.ViewFactory
    {
        private LayoutInflater inflater;

        public myViewFactory(LayoutInflater inf)
        {
            inflater = inf;
        }

        @Override
        public View makeView()
        {
            //提供ViewSwitcher中的视图模版
            return inflater.inflate(R.layout.viewswitcher_layout, null);
        }

    }

    private void nextView()
    {
        if (currentImageNumber < imageCount - 1)
        {
            currentImageNumber++;
            // 设置视图切换的动画效果
            mViewSwitcher.setInAnimation(getContext(), android.R.anim.slide_in_left);
            mViewSwitcher.setOutAnimation(getContext(), android.R.anim.slide_out_right);
            //获取视图模版
            LinearLayout ll = (LinearLayout) mViewSwitcher.getNextView();
            //为视图模版赋予实际内容
            ImageView img = (ImageView) ll.findViewById(R.id.img);
            img.setImageResource(imageIDs[currentImageNumber]);
            //切换视图
            mViewSwitcher.showNext();
        }
    }

    private void previousView()
    {
        if (currentImageNumber > 0)
        {
            currentImageNumber--;
            // 设置视图切换的动画效果
            mViewSwitcher.setInAnimation(getContext(), R.anim.slide_in_right);
            mViewSwitcher.setOutAnimation(getContext(), R.anim.slide_out_left);
            //获取视图模版
            LinearLayout ll = (LinearLayout) mViewSwitcher.getNextView();
            //为视图模版赋予实际内容
            ImageView img = (ImageView) ll.findViewById(R.id.img);
            img.setImageResource(imageIDs[currentImageNumber]);
            //切换视图
            mViewSwitcher.showPrevious();

        }
    }
}