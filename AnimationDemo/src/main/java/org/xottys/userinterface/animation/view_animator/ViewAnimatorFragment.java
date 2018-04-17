/**
 * 本例演示了ViewAnimator的基本用法，具体使用步骤如下：
 * 1）在xml中用标签<ViewAnimator>定义,用view.findViewById(R.id.view_animator)来加载
 * 2）添加子Views：myViewAnimator.addView(childView)，子Views也可以直接在xml中定义
 * 3）设置进入和退出动画：
 *    myViewAnimator.setInAnimation(animation)；
 *    myViewAnimator.setOutAnimation(animation)；
 *    这个也可以直接在xml中定义
 * 4）切换视图：myViewAnimator.showNext()
 *           myViewAnimator.showPrevious()
 *    此时会播放上一步中定义的动画
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
import android.widget.ViewAnimator;

import org.xottys.userinterface.animation.R;

public class ViewAnimatorFragment extends Fragment {
    private int[]        imageIDs;
    private int          imageCount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIDs = new int[] { R.drawable.bird1, R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_viewanimator, null);
        final ViewAnimator mViewAnimator =  view.findViewById(R.id.view_animator);
        //为ViewAnimator加载全部视图
        for (int i = 0; i < imageCount; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(imageIDs[i]);
            mViewAnimator.addView(imageView);
        }

        //设置属性：是否在第一次显示ViewAnimator时启用动画
        mViewAnimator.setAnimateFirstView(true);

        Button btn_next =  view.findViewById(R.id.next);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
                Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);
                //设置动画
                mViewAnimator.setInAnimation(in);
                mViewAnimator.setOutAnimation(out);
                //切换视图
                mViewAnimator.showNext();
            }
        });

        Button btn_prev =  view.findViewById(R.id.prev);
        btn_prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
                Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
                //设置动画
                mViewAnimator.setInAnimation(in);
                mViewAnimator.setOutAnimation(out);
                //切换视图
                mViewAnimator.showPrevious();

            }
        });

        return view;
    }
}