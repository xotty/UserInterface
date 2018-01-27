/**
 * 本例演示了Material Designd的八个新控件：
 * 1)TextInputLayout/TextInputEditText
 * 2)FloatingActionButton
 * 3)Snackbar
 * 4)TabLayout
 * 5)NavigationView
 * 6)AppBarLayout(侧2)
 * 7)CollapsingToolbarLayout(侧2)
 * 8)CoordinatorLayout(侧2)
 * V4、V7Surpport库及其它的十一个新控件：
 * 1)DrawerLayout(侧)
 * 2)SwipeRefreshLayout(侧1)
 * 3)RecyclerView(侧1)
 * 4)CardView(正Page2)
 * 5)NestedScrollView
 * 6)BottomNavigationView
 * 7)BottomSheetDialog
 * 8)ConstraintLayout(侧3)
 * 9)RippleDrawable(正Page3)
 * 10)Palette(侧4)
 * 11)Theme(侧5)
 * 注--括号中的标注含义，正Page2：正常页面中第2项；侧：抽屉本身，左右均可，右抽屉不具有点击功效；侧2：抽屉视图中第2项
 *   --其它未用括号标注的项均在正常页面中第1项（MD WIDGETS）中
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:MaterialDesignDemo
 * <br/>Date:Oct，2017～Jan，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.MaterialDesignDemo;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xottys.userinterface.R;

import java.util.ArrayList;
import java.util.List;

public class MaterialDesignActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MaterialDesignActivity";
    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materialdesign);
        initView();
        initViewPager();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //侧滑抽屉控件drawer与toolbar同步,
        //ActionBarDrawerToggl实现用Home按钮完成Drawer拉出/隐藏，并带有动画效果
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_left);
        //给MenuItem设置点击监听
        navigationView.setNavigationItemSelectedListener(this);
        //给MenuItem图标设置颜色，null表示原色
        navigationView.setItemIconTintList(null);

        //设置HedaerView点击事件
        View headerView = navigationView.getHeaderView(0);
        LinearLayout nav_header = (LinearLayout) headerView.findViewById(R.id.nav_header);
        nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Header");

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //关闭侧滑抽屉控件
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //一个页面中最主要的操作，个页面最好只有一个FAB
        fab = (FloatingActionButton) findViewById(R.id.fab_main);
        //设置FloatingActionButton点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.LENGTH_INDEFINITE:不消失显示，除非手动取消
                final Snackbar sb = Snackbar.make(view, getString(R.string.main_snack_bar), Snackbar.LENGTH_INDEFINITE);
                //设置动作事件
                sb.setAction(getString(R.string.main_snack_bar_action), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sb.dismiss();
                        Log.i(TAG, "onClick: Floating ActionBAr");
                    }
                });


                sb.addCallback(new Snackbar.Callback() {
                    //Snackbar关闭时回调
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        Log.i(TAG, "Snackbar关闭 !");
                    }

                    // Snackbar打开时回调
                    @Override
                    public void onShown(Snackbar snackbar) {
                        super.onShown(snackbar);
                        Log.i(TAG, "Snackbar打开 !");

                    }
                });

                sb.setActionTextColor(Color.BLUE);      //改变按钮的文字颜色
                View mView = sb.getView();
                mView.setBackgroundColor(Color.LTGRAY);   //改变消息内容的背景
                TextView tv = (TextView) mView.findViewById(R.id.snackbar_text);
                tv.setTextColor(Color.RED);               //改变消息内容的文字颜色
                sb.show();
            }
        });
    }

    private void initViewPager() {
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout_main);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //选中了tab的逻辑
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG, "onTabSelected: " + tab.getText());
            }

            //未选中tab的逻辑
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.i(TAG, "onTabUnselected: " + tab.getText());

            }

            //再次选中tab的逻辑
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                Log.i(TAG, "onTabReselected: " + tab.getText());
            }
        });

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.tab_title_main_1));
        titles.add(getString(R.string.tab_title_main_2));
        titles.add(getString(R.string.tab_title_main_3));

        /*也可以用代码为TabLayout添加TabItem，其Title可以是文字和/或图标
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setIcon(R.drawable.ic_file_download_black_24dp));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)).setIcon(R.drawable.ic_shop_black_24dp));
        */

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MatrialWidgetsFragment());
        fragments.add(new CardViewFragment());
        fragments.add(new RippleDrawableFragment());

        //定义ViewPager及其适配器
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager_main);
        mViewPager.setOffscreenPageLimit(2);
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapter);

        //定义ViewPager滑动事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //适时显示FloatingActionButton
                if (position != 2) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //将TabLayout与ViewPager关联，使二者联动
        //联动后Tab就同时具备了点击和滑动效果，
        //联动后，相关的Fragment通过Viewpager的适配器自动切换，Tab的点击事件也不用再写了
        //联动后，ViewPager的Title代替了Tab的Title
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Log.i(TAG, "onBackPressed: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meterialdesign, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_main_1:
                Log.i(TAG, "onOptionsItemSelected: ");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //处理侧滑抽屉菜单项的点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_recycler_and_swipe_refresh:
                startActivityTo(RecyclerViewActivity.class);
                break;
            case R.id.nav_collapsing_toolbar:
                startActivityTo(CollapsingToolbarActivity.class);
                break;
            case R.id.nav_constraint_layout:
                startActivityTo(ConstraintLayoutActivity.class);
                break;
            case R.id.nav_palette:
                startActivityTo(PaletteActivity.class);
                break;
            case R.id.nav_daynight:
                if (GVariable.isNight) {
                    //恢复日间模式，调用values下的xml
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    GVariable.isNight = false;
                } else {
                    //启动夜间模式，调用values-night下的xml
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    GVariable.isNight = true;
                }
                //刷新屏幕
                recreate();
                return true;
            case R.id.nav_settings:
                Log.i(TAG, "onNavigationItemSelected: Settings");
                break;
            case R.id.nav_about:
                Log.i(TAG, "onNavigationItemSelected: About");
                break;
        }
        //关闭抽屉
        drawer.closeDrawers();
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    private void startActivityTo(Class<? extends AppCompatActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
