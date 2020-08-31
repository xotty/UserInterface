
/**
 * 为Fragment专设的ViewPagerAdapter，负责为PaletteActivity页面装填Fragment
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

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PaletteViewPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[]{"华东", "华南", "西北", "东北", "华中"};
    private Context context;

    public PaletteViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PaletteFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}