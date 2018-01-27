/**
 * 简单的显示一张图片和名字的Fragment，可以根据传入的参数显示不同的图片和文字
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:RippleDrawableFragment
 * <br/>Date:Jan，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */

package org.xottys.userinterface.MaterialDesignDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class PaletteFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final int[] drawables = {R.mipmap.one, R.mipmap.two, R.mipmap.four, R.mipmap
            .three, R.mipmap.five};
    private int position;

    public static PaletteFragment newInstance(int position) {
        PaletteFragment f = new PaletteFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    //提供当前Fragment的主色调的Bitmap对象,供Palette解析颜色
    public static int getBackgroundBitmapPosition(int selectViewPagerItem) {
        return drawables[selectViewPagerItem];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        //设置布局参数
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                .MATCH_PARENT);
        final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());

        params.setMargins(margin, margin, margin, margin);

        //将图片设置为背景，根据传入的position参数确定使用用哪张图片
        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);
        fl.setBackgroundResource(drawables[position]);

        //将文字视图添加的布局中
        TextView v = new TextView(getActivity());
        v.setLayoutParams(params);
        v.setLayoutParams(params);
        v.setGravity(Gravity.BOTTOM);
        v.setText("CARD " + (position + 1));
        v.setTextColor(Color.WHITE);
        fl.addView(v);
        return fl;
    }

}