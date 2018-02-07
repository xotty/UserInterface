/**
 * 演示FragmentGeneral用到的数据类，主要包含Book、ITEMS和ITEM_MAP三个对象
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name: FragmentGeneral
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.FragmentGeneral;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

public class BookContent
{
    public static List<Book> ITEMS = new ArrayList<>();
    public static SparseArray<Book> ITEM_MAP
            = new SparseArray<>();

	static
	{
		addItem(new Book(1, "疯狂Java讲义"
			, "一本全面、深入的Java学习图书，已被多家高校选做教材。"));
		addItem(new Book(2, "疯狂Android讲义"
			, "Android学习者的首选图书，常年占据京东、当当、亚马逊3大网站Android销量排行榜的榜首"));
		addItem(new Book(3, "轻量级Java EE企业应用实战"
			, "全面介绍Java EE开发的Struts 2、Spring 3、Hibernate 4框架"));
	}

	private static void addItem(Book book)
	{
		ITEMS.add(book);
		ITEM_MAP.put(book.id, book);
	}

    public static class Book {

        public Integer id;
        public String title;
        public String desc;

        public Book(Integer id, String title, String desc) {
            this.id = id;
            this.title = title;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
