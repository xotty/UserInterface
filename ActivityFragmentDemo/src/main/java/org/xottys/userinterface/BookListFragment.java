package org.xottys.userinterface;

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

/**
 * Description:
 * <br/>website: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class BookListFragment extends ListFragment
{
	private Callbacks mCallbacks;

	public interface Callbacks
	{
		public void onItemSelected(Integer id);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 为该ListFragment设置Adapter
		setListAdapter(new ArrayAdapter<BookContent.Book>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, BookContent.ITEMS));  //①
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
		// 输出日志
		Log.d(TAG, "-------onCreateView------");

		return super.onCreateView(inflater,container,data);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		// 输出日志
		Log.d(TAG, "-------onActivityCreated------");
	}

	@Override
	public void onStart()
	{
		super.onStart();
		// 输出日志
		Log.d(TAG, "-------onStart------");
	}

	@Override
	public void onResume()
	{
		super.onResume();
		// 输出日志
		Log.d(TAG, "-------onResume------");
	}

	@Override
	public void onPause()
	{
		super.onPause();
		// 输出日志
		Log.d(TAG, "-------onPause------");
	}

	@Override
	public void onStop()
	{
		super.onStop();
		// 输出日志
		Log.d(TAG, "-------onStop------");
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		// 输出日志
		Log.d(TAG, "-------onDestroyView------");
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// 输出日志
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


	// 当用户点击某列表项时回调该方法
	@Override
	public void onListItemClick(ListView listView
		, View view, int position, long id)
	{
		super.onListItemClick(listView, view, position, id);
		mCallbacks.onItemSelected(BookContent.ITEMS.get(position).id);
	}

	public void setActivateOnItemClick(boolean activateOnItemClick)
	{
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}
}
