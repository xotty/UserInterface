/**
 * 用传入的书籍id获取对应的书籍信息，将其填充到fragment_book_detail布局中去
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name: FragmentGeneral
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.FragmentGeneral;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class BookDetailFragment extends Fragment
{
	public static final String ITEM_ID = "item_id";
	// 保存该Fragment显示的Book对象
	BookContent.Book book;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 如果启动该Fragment时包含了ITEM_ID参数，将传入的id对应的书籍取出来
		if (getArguments().containsKey(ITEM_ID))
		{
			book = BookContent.ITEM_MAP.get(getArguments()
				.getInt(ITEM_ID));
		}
	}

	// 重写该方法，该方法返回的View将作为Fragment显示的组件
	@Override
	public View onCreateView(LayoutInflater inflater
		, ViewGroup container, Bundle savedInstanceState)
	{
		// 加载/res/layout/目录下的fragment_book_detail.xml布局文件
		View rootView = inflater.inflate(R.layout.fragment_book_detail,
				container, false);
		if (book != null)
		{
			// 让book_title文本框显示book对象的title属性
			((TextView) rootView.findViewById(R.id.book_title))
					.setText(book.title);
			// 让book_desc文本框显示book对象的desc属性
			((TextView) rootView.findViewById(R.id.book_desc))
				.setText(book.desc);		
		}
		return rootView;
	}
}
