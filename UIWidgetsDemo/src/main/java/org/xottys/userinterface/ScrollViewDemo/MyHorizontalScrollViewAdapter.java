/**
 * 本例为MyHorizontalScrollView定义Aapter：
 * 1)构造器，以便能传入数据：图片和名称
 * 2)getCount，定义其中项目的数量
 * 3)getView，定义每个项目的视图并将数据与视图组装起来
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.ScrollViewDemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class MyHorizontalScrollViewAdapter {

    private LayoutInflater mInflater;
    private int[] images;
    private String[] imgTitles;

    public MyHorizontalScrollViewAdapter(Context context, int[] images, String[] imgTitles) {
        mInflater = LayoutInflater.from(context);
        this.images = images;
        this.imgTitles = imgTitles;
    }

    public int getCount() {
        return images.length;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            //获取固定的Item视图
            convertView = mInflater.inflate(R.layout.item_scrollview2, parent, false);
            viewHolder.mImg = (ImageView) convertView
                    .findViewById(R.id.id_index_gallery_item_image);
            viewHolder.mText = (TextView) convertView
                    .findViewById(R.id.id_index_gallery_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //将数据与视图组装起来
        viewHolder.mImg.setImageResource(images[position]);
        viewHolder.mText.setText(imgTitles[position]);

        return convertView;
    }

    private class ViewHolder {
        ImageView mImg;
        TextView mText;
    }
}
