/**
 * 本例演示了StackView的基本用法，具体使用步骤如下：
 * 1）在xml中用标签<StackView>定义,用view.findViewById(R.id.stackview)来加载
 * 2）在Adapter中设置具体View的内容， 用mStackView.setAdapter加载
 * 3）切换视图：mStackView.showNext()
 *            mStackView.showPrevious()
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:StackViewFragment
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.adapterview_animator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.StackView;

import org.xottys.userinterface.animation.R;

public class StackViewFragment extends Fragment {
    private int[]        imageIDs;
    private int          imageCount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIDs = new int[] { R.drawable.bird1,
                R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_stackview, null);
        //加载StackView
        final StackView mStackView=view.findViewById(R.id.stackview);
        //为StackView提供内容
        mStackView.setAdapter(new ImageAdapter(getContext()));

        Button btn_next =  view.findViewById(R.id.next);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
              //切换StackView
              mStackView.showNext();
            }
        });

        Button btn_prev =  view.findViewById(R.id.prev);
        btn_prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                //切换StackView
                mStackView.showPrevious();
            }
        });
        return view;
    }

    private class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return imageCount;
        }

        @Override
        public Object getItem(int position) {
            return imageIDs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //为StackView设置内容
            ImageView mImageView = new ImageView(mContext);
            mImageView.setImageResource(imageIDs[position]);
            return mImageView;
        }
    }

}