/**
 * 本例演示了用SimpleAdapter装配的GridView的用法：
 * 1)主xml中使用GridView控件，其特殊属性有：numColumns="4"、columnWidth="80dp"、stretchMode="columnWidth"
 * 2)每个元素Item的xml中，定义好图文布局
 * 3)代码中用List<Map>准备所有Item的内容
 * 4)用SimpleAdapter将上述视图和内容装配起来
 * 5)用setOnItemClickListener设置事件监听器，在其中处理点击事件
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdapterViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xottys.userinterface.R;


public class GridViewActivity1 extends Activity {
	private GridView gview;
	private List<Map<String, Object>> data_list;
	private SimpleAdapter sim_adapter;
	// 图片封装为一个数组
	private int[] icon = { R.drawable.address_book, R.drawable.calendar,
			R.drawable.camera, R.drawable.aclock, R.drawable.games_control,
			R.drawable.messenger, R.drawable.ringtone, R.drawable.settings,
			R.drawable.speech_balloon, R.drawable.weather, R.drawable.world,
			R.drawable.youtube };
	// 名称封装为另一个数组
	private String[] iconName = { "通讯录", "日历", "照相机", "时钟", "游戏", "短信", "铃声",
			"设置", "语音", "天气", "浏览器", "视频" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_greiview);
		gview = (GridView) findViewById(R.id.gview);
		//数据list
		data_list = new ArrayList<Map<String, Object>>();
		//获取数据
		getData();
		//新建适配器
		String[] from ={"image","text"};
		int [] to = {R.id.image,R.id.text};
		sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_grid, from, to);
		//配置适配器
		gview.setAdapter(sim_adapter);
		// 添加列表项被单击的监听器
		gview.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
			{
				showToast("你点击了："+position+"～～～"+iconName[position]);
				Log.i("TAG", "onItemClick: "+position+"～～～"+iconName[position]);
			}
		});
	}

	public List<Map<String, Object>> getData(){
		//icon和iconName的长度是相同的，这里任选其一都可以
		for(int i=0;i<icon.length;i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", icon[i]);
			map.put("text", iconName[i]);
			data_list.add(map);
		}
		return data_list;
	}

	void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
