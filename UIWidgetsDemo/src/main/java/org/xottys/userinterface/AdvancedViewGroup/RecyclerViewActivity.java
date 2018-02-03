/**
 * RecyclerView主程序，实现流程为：
 * 1)在xml布局文件中设置android.support.v7.widget.RecyclerView项
 * 2)通过mRecyclerView.setLayoutManager控制显示方式：LinearLayoutManager/GridLayoutManager/StaggeredGridLayoutManager
 * 3)mRecyclerView.setAdapter(adapter)设置适配器：extends RecyclerView.Adapter<RecyclerView.ViewHolder>
 * 4)通过mRecyclerView.addOnScrollListener实现ItemView滚动事件
 * 5)在Adapter中定义onClickListener实现ItemView点击事件
 * 6)通过mRecyclerView.addItemDecoration实现自定义Item间隔线（可选）
 * 7)通过mRecyclerView.setItemAnimaton实现自定义ItemView增删动画（可选）
 * <p>
 * SwipeRefreshLayout主程序，实现要点为：
 * 1)xml布局文件中设置android.support.v4.widget.SwipeRefreshLayout项，其中有且仅有一个可垂直滚动的控件
 * 2)在程序中设置SwipeRefreshLayout的相关属性，如下来条的颜色、下拉触发动画的距离......
 * 3)swipeRefreshLayout.setOnRefreshListener设置下来刷新监听器，其中实现数据和视图的刷新
 * <p>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:RecyclerViewActivity
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdvancedViewGroup;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import org.xottys.userinterface.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private RecyclerViewAdapter adapter;
    private int color = 0;
    private List<String> data;
    private String insertData;
    private boolean loading;
    private int loadTimes;
    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//       final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//       if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 1)) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (loadTimes <= 3) {
                        adapter.removeFooter();
                        loading = false;
                        adapter.addItems(data);
                        adapter.addFooter();
                        loadTimes++;
                    } else {
                        adapter.removeFooter();
                        Snackbar.make(mRecyclerView, getString(R.string.no_more_data), Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                loading = false;
                                adapter.addFooter();
                            }
                        }).show();
                    }
                }
            }, 1500);

            loading = true;
        }
//      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_recycler_view);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initData();
        initView();
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add(i + "");
        }

        insertData = "0";
        loadTimes = 0;
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_recycler_view);

        //设置ItemView滚动监听器
        mRecyclerView.addOnScrollListener(scrollListener);

        //根据屏幕宽度设置RecyclerView不同的布局显示方式
        if (getScreenWidthDp() >= 600) {
            final StaggeredGridLayoutManager staggeredGridlayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(staggeredGridlayoutManager);
        } else if (getScreenWidthDp() >= 400) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }
        //设置适配器
        adapter = new RecyclerViewAdapter(this, data);
        adapter.addFooter();
        mRecyclerView.setAdapter(adapter);
        //设置ItemView点击监听器
        adapter.setItemClickListener(new RecyclerViewAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("TAG", "onClick: 点击了第 " + position + " 个");
            }
        });

        //FAB点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                adapter.addItem(linearLayoutManager.findFirstVisibleItemPosition() + 1, insertData);
            }
        });

        //实现Recycleview拖拽排序与侧滑删除
        ItemTouchHelper.Callback callback = new RecyclerviewItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_recycler_view);
        //设置下拉进度条的颜色主题
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        //设置下来刷新监听器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (color > 4) {
                            color = 0;
                        }
                        //改变圆圈颜色
                        adapter.setColor(++color);
                        //停止显示动画
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });


    }

    private int getScreenWidthDp() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

}
