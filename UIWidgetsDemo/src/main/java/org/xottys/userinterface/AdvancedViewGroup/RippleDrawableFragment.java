/**
 * 本例在RIPPLE中演示了Graphics中的一个新控件RippleDrawable的用法，使用要点：
 * 1)在res/drawable中用xml定义ripple资源，其关键是配置mask（无/颜色/图片/形状），如：ripple_red_with_pic_mask
 * 2)在布局xml中用定义的ripple作为某可点击控件的background的资源，android:background="@drawable/ripple_red_with_pic_mask"
 * 3)此时通常设定该控件：android:clickabl=true
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:RippleDrawableFragment
 * <br/>Date:Jan，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdvancedViewGroup;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.xottys.userinterface.R;


public class RippleDrawableFragment extends Fragment {

    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rootView = inflater.inflate(R.layout.fragment_rippledrawable_landscape, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fragment_rippledrawable_portrait, container, false);
        }
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        return rootView;
    }
}
