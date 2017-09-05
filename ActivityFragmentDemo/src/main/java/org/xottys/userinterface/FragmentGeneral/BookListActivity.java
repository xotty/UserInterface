/**
 * 本例演示了Fragment的基本用法：
 * 1) 在xml布局文件中直接定义Fragment，启动Activity就会直接启动其中的Fragment
 * 2）在xml布局文件中定义一个容器，用transaction中的add或replace方法启动Fragment
 * 同时演示了设备屏幕横竖切换的过程，Activity每次横竖屏切换都会重新调用一轮：
 * onPause-> onStop-> onDestory-> onCreate->onStart->onResume操作
 * 这样既可以象本例一样用代码判断屏幕方向，进而调用不用xml布局文件；也可以在res目录下建立
 * layout-land和layout-port两个目录，分别放置横屏和竖屏的xml布局文件，供系统自动调用
 * 这种方法必然导致数据的丢失和重新获取，为此就要在切换前对数据进行保存，切换重启后对数据进行恢复
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LifecycleActivity
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.FragmentGeneral;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import org.xottys.userinterface.R;

public class BookListActivity extends Activity implements
        BookListFragment.Callbacks {
    // 定义一个旗标，用于标识该应用是否使用双区显示模式
    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /*恢复此前保存的数据
        final MyDataObject data = (MyDataObject) getLastNonConfigurationInstance();
        if (data == null) {
            data = loadMyData();
        }*/

        //如果竖屏加载activity_book_list，横屏则加载activity_book_twopane布局文件
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //双Fragment界面，其中一个已在xml布局文件中直接定义好，另一个定义为容器
            setContentView(R.layout.activity_book_twopane);
            mTwoPane = true;
            ((BookListFragment) getFragmentManager()
                    .findFragmentById(R.id.book_list))
                    .setActivateOnItemClick(true);
        }
        else
            //单Fragment 界面，已在xml布局文件中直接定义好
            setContentView(R.layout.activity_book_list);
    }

    //BooKlistFragment的一个回调接口，用于处理项目（书名）点击后的事件
    @Override
    public void onItemSelected(Integer id) {
        //使用Fragment显示书籍详细介绍信息，直接加载BookDetailFragment
        if (mTwoPane) {
            // 创建Bundle，准备向BookDetailFragment传入参数
            Bundle arguments = new Bundle();
            arguments.putInt(BookDetailFragment.ITEM_ID, id);
            // 创建BookDetailFragment对象
            BookDetailFragment fragment = new BookDetailFragment();
            // 向Fragment传入参数
            fragment.setArguments(arguments);
            // 使用fragment替换book_detail_container容器当前的Fragment
            getFragmentManager().beginTransaction()
                    .replace(R.id.book_detail_container, fragment).commit();
        }
        //使用Activity显示书籍详细介绍信息，在新的Activity中再加载BookDetailFragment
        else {
            // 创建启动BookDetailActivity的Intent
            Intent detailIntent = new Intent(this, BookDetailActivity.class);
            // 设置传给BookDetailActivity的参数
            detailIntent.putExtra(BookDetailFragment.ITEM_ID, id);
            // 启动BookDetailActivity
            startActivity(detailIntent);
        }
    }

    /*屏幕切换前保存数据
    @Override
    public Object onRetainNonConfigurationInstance() {
        final MyDataObject data = collectMyLoadedData();
        return data;
    }*/
}
