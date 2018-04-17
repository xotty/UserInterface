/**
 * 本例演示了ViewPager+Fragment切换动画的用法：
 * 1)自定义Transformer，继承ViewPager.PageTransformer
 * 2)使用Transformer, mViewPager.setPageTransformer(true, new MyTransformer());
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.page_transformer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import org.xottys.userinterface.animation.R;

public class PageTransformerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    static private int[]  imageIDs;
    private int          imageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagetransformer);
        imageIDs= new int[] { R.drawable.bird1,
                 R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager =  findViewById(R.id.container_vp);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //设置初始Transformer
        mViewPager.setPageTransformer(true, new DepthPageTransformer());

        //根据Radio按钮选择不同的Transformer
        RadioGroup radiogroup = findViewById(R.id.btn_page_transformer);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.depth:
                        mViewPager.setPageTransformer(true, new DepthPageTransformer());
                        break;
                    case R.id.mycube:
                        mViewPager.setPageTransformer(true, new MyCubeTransformer(90));
                        break;
                    case R.id.zoom_out:
                        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                        break;
                }
            }
        });
    }


    //与ViewPager配套的Fragment
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            //传入参数sectionNumber
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.flipper_cell, container, false);
            ImageView section_label = rootView.findViewById(R.id.img);
            int number = getArguments().getInt(ARG_SECTION_NUMBER);

            //根据传入的参数sectionNumber来为Fragment设置不同的图片
            section_label.setImageResource(imageIDs[number]);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {

            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return imageCount;
        }
    }
}
