/**
 * 本例演示了Material Designd的八个新控件：
 * 1)TextInputLayout
 * 2)FloatingActionButton
 * 3)Snackbar
 * 4)TabLayout
 * 5)NavigationView
 * 6)AppBarLayout
 * 7)CollapsingToolbarLayout
 * 8)CoordinatorLayout
 * 和V4、V7Surpport库的七个新控件：
 * 1)DrawerLayout
 * 2)SwipeRefreshLayout
 * 3)RecyclerView
 * 4)CardView
 * 5)NestedScrollView
 * 6)BottomNavigationView
 * 7)BottomSheetDialog
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.MaterialDesignDemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import org.xottys.userinterface.R;

import java.util.ArrayList;
import java.util.List;

public class MaterialDesignActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MaterialDesignActivity";
    private static boolean isShowPageStart = true;
    private final int MESSAGE_SHOW_DRAWER_LAYOUT = 0x001;
    private final int MESSAGE_SHOW_START_PAGE = 0x002;
    private DrawerLayout drawer;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SHOW_DRAWER_LAYOUT:
                    drawer.openDrawer(GravityCompat.START);
                    SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isFirst", false);
                    editor.apply();
                    break;

                case MESSAGE_SHOW_START_PAGE:
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    alphaAnimation.setDuration(300);
                    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    break;
            }
        }
    };
    private FloatingActionButton fab;

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

        //侧滑抽屉控件drawer与toolbar同步
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //给MenuItem设置点击监听
        navigationView.setNavigationItemSelectedListener(this);

        //给MenuItem图标设置颜色
        navigationView.setItemIconTintList(null);

        //设置HedaerView点击事件
        View headerView = navigationView.getHeaderView(0);
        LinearLayout nav_header = (LinearLayout) headerView.findViewById(R.id.nav_header);
        nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: Header");
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        //设置FloatingActionButton点击事件
        fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, getString(R.string.main_snack_bar), Snackbar.LENGTH_LONG)
                        //设置动作事件
                        .setAction(getString(R.string.main_snack_bar_action), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i(TAG, "onClick: Floating ActionBAr");
                            }
                        })
                        .show();
            }
        });
    }

    private void initViewPager() {

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout_main);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.tab_title_main_1));
        titles.add(getString(R.string.tab_title_main_2));
        titles.add(getString(R.string.tab_title_main_3));
        //为TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MatrialWidgetsFragment());
        fragments.add(new CollapsingToolbarFragment());
        fragments.add(new CardViewFragment());

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
                if (position == 1) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //将TabLayout与ViewPager关联
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_recycler_and_swipe_refresh:
                intent.setClass(this, RecyclerViewActivity.class);
                startActivity(intent);
                Log.i(TAG, "onNavigationItemSelected:recycler_and_swipe_refresh ");
                break;
            case R.id.nav_settings:
                Log.i(TAG, "onNavigationItemSelected: Settins");
                break;

            case R.id.nav_about:
                Log.i(TAG, "onNavigationItemSelected: About");
                break;
        }
        //关闭抽屉
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
