/**
 * 本例用ListFragment来显示书籍名称，其主要用法是：
 * 1)用setListAdapter填充其自带的ListView数据
 * 2)重写onListItemClick响应项目点击事件
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
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static android.content.ContentValues.TAG;

public class BookListFragment extends ListFragment
{
	private Callbacks mCallbacks;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//为该ListFragment设置Adapter，将Book数据(BookContent.ITEMS)添加进去
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, BookContent.ITEMS));

		Log.d(TAG, "-------onCreate------");
	}

	// 当该Fragment被添加、显示到Activity时，回调该方法
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		// 如果Activity没有实现Callbacks接口，抛出异常
		if (!(activity instanceof Callbacks))
		{
			throw new IllegalStateException(
				"BookListFragment所在的Activity必须实现Callbacks接口!");
		}
		// 把该Activity当成Callbacks对象
		mCallbacks = (Callbacks) activity;

		Log.d(TAG, "-------onAttach------");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle data)
	{
		// ListFragment使用系统定义的ListView，不需要自定义view
		Log.d(TAG, "-------onCreateView------");

		return super.onCreateView(inflater,container,data);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		Log.d(TAG, "-------onActivityCreated------");
	}

	@Override
	public void onStart()
	{
		super.onStart();

		Log.d(TAG, "-------onStart------");
	}

	@Override
	public void onResume()
	{
		super.onResume();

		Log.d(TAG, "-------onResume------");
	}

	@Override
	public void onPause()
	{
		super.onPause();

		Log.d(TAG, "-------onPause------");
	}

	@Override
	public void onStop()
	{
		super.onStop();

		Log.d(TAG, "-------onStop------");
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();

		Log.d(TAG, "-------onDestroyView------");
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();

		Log.d(TAG, "-------onDestroy------");
	}

	// 当该Fragment从它所属的Activity中被删除时回调该方法
	@Override
	public void onDetach()
	{
		super.onDetach();
		// 将mCallbacks赋为null。
		mCallbacks = null;

		Log.d(TAG, "-------onDetach------");
	}

	// 当用户点击某列表项时回调该方法，通过调用Avtivity的回调方法，把数据传给Activity
	@Override
	public void onListItemClick(ListView listView
		, View view, int position, long id)
	{
		super.onListItemClick(listView, view, position, id);
		mCallbacks.onItemSelected(BookContent.ITEMS.get(position).id);
	}

	// 设置listview中项目点击模式
	public void setActivateOnItemClick(boolean activateOnItemClick)
	{
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

    public interface Callbacks {
        void onItemSelected(Integer id);
    }
}
