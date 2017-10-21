/**
 * 本例演示了自定义Adapter的更多变化用法：
 * 1)使用外置的自定义Adapetr：activity_customeradapter2
 * 2)实现了常用的右侧边栏字母索引功能
 * 3)使用自定义注解方式来代替findViewById方法，关联变量和布局文件中的控件
 * <p>
 * <br/>Copyright (C), 2017-2018, wcy
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 * @author wcy
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.xottys.userinterface.AdapterViewDemo.binding.Bind;
import org.xottys.userinterface.AdapterViewDemo.binding.ViewBinder;
import org.xottys.userinterface.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomerAdapterActivity2 extends Activity implements OnItemClickListener, IndexBar.OnIndexChangedListener {
    private static final String TAG  = "CustomerAdapterActivity";

    //用注解方式将xml中的view与这里的变量关联起来
    @Bind(R.id.lv_company)
    private ListView lvCompany;
    @Bind(R.id.ib_indicator)
    private IndexBar ibIndicator;
    @Bind(R.id.tv_indicator)
    private TextView tvIndicator;

    private List<CompanyEntity> mCompanyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customeradapter2);
        //@Bind注解生效的关键步骤
        ViewBinder.bind(this);

        readCompany();

        lvCompany.setAdapter(new CustomerAdapter2(mCompanyList));

        lvCompany.setOnItemClickListener(this);
        ibIndicator.setOnIndexChangedListener(this);
    }

    private void readCompany() {
        //用Gson框架从文件中读取公司名称等信息
        try {
            InputStream is = getAssets().open("company.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            int bytesread=is.read(buffer);
            is.close();
            String json = new String(buffer);

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray = parser.parse(json).getAsJsonArray();
            for (JsonElement obj : jArray) {
                CompanyEntity company = gson.fromJson(obj, CompanyEntity.class);
                mCompanyList.add(company);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> view, View arg1, int position, long arg3) {
          Toast.makeText(this, "点击了:"+mCompanyList.get(position).getName(), Toast.LENGTH_SHORT).show();
          Log.i(TAG, "点击了:"+mCompanyList.get(position).getName());
    }

    @Override
    public void onIndexChanged(String index, boolean showIndicator) {
        int position = -1;
        for (CompanyEntity company : mCompanyList) {
            if (TextUtils.equals(company.getName(), index)) {
                //company.name与index相同时，取出该company所在的位置
                position = mCompanyList.indexOf(company);
                break;
            }
        }
        if (position != -1) {
            //将listview第一行设置为position所在的行
            lvCompany.setSelection(position);
        }
        tvIndicator.setText(index);
        tvIndicator.setVisibility(showIndicator ? View.VISIBLE : View.GONE);
    }
}
