/**
 * 本例演示了自定义Adapter的更多变化用法：
 * 1)getItemViewType和getViewTypeCount可以定义多种不同类型的Item
 * 2)areAllItemsEnabled和isEnabled可以决定相应的行是否可以点击
 * 3)第三方Glide控件可以简单快速地加载网络图片
 * <p>
 * <br/>Copyright (C), 2017-2018, wcy
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author wcy
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xottys.userinterface.AdapterViewDemo.binding.Bind;
import org.xottys.userinterface.AdapterViewDemo.binding.ViewBinder;
import org.xottys.userinterface.R;

import java.util.List;


public class CustomerGroupAdapter extends BaseAdapter {
    private static final int TYPE_TITLE = 0;
    private static final int TYPE_COMPANY = 1;
    private List<CompanyEntity> mCompanyList;

    public CustomerGroupAdapter(List<CompanyEntity> companyList) {
        mCompanyList = companyList;
    }

    @Override
    public int getCount() {
        return mCompanyList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCompanyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(mCompanyList.get(position).getCode())) {
            return TYPE_TITLE;
        } else {
            return TYPE_COMPANY;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return !TextUtils.isEmpty(mCompanyList.get(position).getCode());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        IndexViewHolder indexHolder;
        CompanyViewHolder companyHolder;
        switch (getItemViewType(position)) {
            case TYPE_TITLE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_holder_company_index, parent, false);
                    indexHolder = new IndexViewHolder(convertView);
                    convertView.setTag(indexHolder);
                } else {
                    indexHolder = (IndexViewHolder) convertView.getTag();
                }
                indexHolder.tvIndex.setText(mCompanyList.get(position).getName());
                break;
            case TYPE_COMPANY:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.view_holder_company, parent, false);
                    companyHolder = new CompanyViewHolder(convertView);
                    convertView.setTag(companyHolder);
                } else {
                    companyHolder = (CompanyViewHolder) convertView.getTag();
                }
                String url = String.format("https://cdn.kuaidi100.com/images/all/%s", mCompanyList.get(position).getLogo());
                Glide.with(context)
                        .load(url)
                        .dontAnimate()
                        .placeholder(R.drawable.default_logo)
                        .into(companyHolder.ivLogo);
                companyHolder.tvName.setText(mCompanyList.get(position).getName());
                break;
        }
        return convertView;
    }

    private static class IndexViewHolder {
        @Bind(R.id.tv_index)
        private TextView tvIndex;

        private IndexViewHolder(View view) {
            ViewBinder.bind(this, view);
        }
    }

    private static class CompanyViewHolder {
        @Bind(R.id.iv_logo)
        private ImageView ivLogo;
        @Bind(R.id.tv_name)
        private TextView tvName;

        private CompanyViewHolder(View view) {
            ViewBinder.bind(this, view);
        }
    }
}
