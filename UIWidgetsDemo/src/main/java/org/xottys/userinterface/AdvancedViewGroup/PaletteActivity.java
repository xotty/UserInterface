/**
 * Palette用于从图片中获取主题颜色，可用来使页面其他部分的颜色保持与其相似，实现流程为：
 * 1)从图片中获取Palette对象，如：Palette p = Palette.from(bitmap).generate();
 * 2)用Palette对象生成的色样，如：Palette.Swatch swatch = palette.getVibrantSwatch();//有活力的
 * 3)从色样中提取相应颜色，如：swatch.getRgb()
 * 4)给相关控件（如ActionBar）设置为提取的颜色，如：setTextColor（swatch.getBodyTextColor()）
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:PaletteActivity
 * <br/>Date:Jan，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdvancedViewGroup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Window;

import org.xottys.userinterface.R;

public class PaletteActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout toolbar_tab;
    private ViewPager main_vp_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_palette);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbar_tab = (TabLayout) findViewById(R.id.toolbar_tab);
        main_vp_container = (ViewPager) findViewById(R.id.main_vp_container);

        PaletteViewPagerAdapter vpAdapter = new PaletteViewPagerAdapter(getSupportFragmentManager(), this);
        main_vp_container.setAdapter(vpAdapter);
        toolbar_tab.setupWithViewPager(main_vp_container);

        //设为初始颜色
        changeTopBgColor(0);

        main_vp_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            //根据滑动位置设置相应颜色
            @Override
            public void onPageSelected(int position) {

                changeTopBgColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //根据Palette提取的颜色，修改Tab标题栏、Toolbar和状态栏的颜色
    private void changeTopBgColor(int position) {
        //用来提取颜色的Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), PaletteFragment
                .getBackgroundBitmapPosition(position));

        // Palette的生成
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                if (vibrant != null) {
                    //根据调色板Palette获取到图片中的颜色设置到Tab标题栏、Toolbar和状态栏，使整个UI界面颜色统一
                    toolbar_tab.setBackgroundColor(vibrant.getRgb());
                    toolbar_tab.setSelectedTabIndicatorColor(colorBurn(vibrant.getRgb()));
                    toolbar.setBackgroundColor(darkVibrant.getRgb());

                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                        Window window = getWindow();
                        window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                        window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
                    }
                }
            }

        });
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return 加深后的颜色代码
     */
    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.5));
        green = (int) Math.floor(green * (1 - 0.5));
        blue = (int) Math.floor(blue * (1 - 0.5));
        return Color.rgb(red, green, blue);
    }
}
