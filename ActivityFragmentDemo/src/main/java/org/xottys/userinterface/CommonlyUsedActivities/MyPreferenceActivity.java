/**
 * 本例演示了PreferenceActivity的基本用法：
 * 1)设置Preference一级分组header.xml，每个分组对应一页，指定一个Fragment。header.xml在onBuildHeaders中加载
 * 2)设置每一页的preference.xml，用相应的PreferenceFragmen去加载
 * 3)设置监听器，并在onPreferenceChange中处理收到的preference数据(此步骤可选，主要用于第一时间通知其他Activity等：系统某些设置进行了改变）
 * 更改后的prefrence值系统会自动进行保存和持久化维护，可以用PreferenceManager.getDefaultSharedPreferences(this)读取
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentListActivity
 * <br/>Date:Sep，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.CommonlyUsedActivities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.xottys.userinterface.R;

import java.util.List;

import static android.content.ContentValues.TAG;
import static org.xottys.userinterface.R.xml.preferences;

public class MyPreferenceActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public static final String LIST_KEY = "list_key";
    public static final String EDIT_TEXT_KEY = "edit_text_key";
    public static final String CHECK_BOX_KEY = "check_box_key";
    public static final String SWITCH_KEY = "switch_key";
    public static final String MS_LIST_KEY = "multiselect_list_key";
    public static final String PREFERENCE_KEY = "preference_key";
    public static final String CUSTOM_KEY = "custom_key";

    static private ListPreference mListPreference;
    static private EditTextPreference mEditTextPreference;
    static private CheckBoxPreference mCheckBoxPreference;
    static private SwitchPreference mSwitchPreference;
    static private RingtonePreference mRingtonePreference;
    static private MultiSelectListPreference mMultiSelectListPreference;
    static private Preference mPreference;
    static private MyPreference myPreference;

    static private boolean isFragmentCreated;  //true表示fragment已创建成功

    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 该方法用于为该界面设置一个标题按钮
        if (hasHeaders()) {
            Button button = new Button(this);
            button.setText("设置操作");
            // 将该按钮添加到该界面上
            setListFooter(button);
        }
        isFragmentCreated = false;

        //用来获取preference保存值
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        /*Android 3.0以前的做法，直接将preferences.xml在这里加载使用,此时不需要重写onBuildHeaders方法
		addPreferencesFromResource(preferences);
		isFragmentCreated = true;
		mListPreference = (ListPreference) this.findPreference(LIST_KEY);
		mEditTextPreference = (EditTextPreference) this.findPreference(EDIT_TEXT_KEY);
		mCheckBoxPreference = (CheckBoxPreference) this.findPreference(CHECK_BOX_KEY);
		mSwitchPreference = (SwitchPreference) this.findPreference(SWITCH_KEY);

		// EditTextPreference组件
		EditTextPreference mEditText = (EditTextPreference) findPreference("edit_text_key");
		//设置dialog按钮信息
		mEditText.setPositiveButtonText("确定");
		mEditText.setNegativeButtonText("取消");
		Log.i(TAG, "onCreate: PrefsActivity");
		*/
    }

    //Android 3.0之后的推荐用法，需重写该方法，加载header.xml文件，
    //其中每一项<header>都是新的一屏，用PreferenceFragment来具体实现
    @Override
    public void onBuildHeaders(List<Header> target) {
        // 加载选项设置列表的布局文件
        loadHeadersFromResource(R.xml.preference_headers, target);
        Log.i(TAG, "onBuildHeaders: ");
    }

    //FragmentActivity特有回调方法，保证相关Fragment已经实现
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return PrefsFragment.class.getName().equals(fragmentName);
    }

    //当任一Preference值变化时回调此方法
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Toast.makeText(this, "属性改变:" + preference.getKey() + "----" + newValue.toString(),Toast.LENGTH_LONG).show();
        Log.i(TAG, "onPreferenceChange: " + preference.getKey() + "----" + newValue.toString());

        return true;
    }

    //当任一Preference项目被点击时回调此方法
    @Override
    public boolean onPreferenceClick(Preference preference) {

        Toast.makeText(this, "点击:" + preference.getKey(), Toast.LENGTH_LONG).show();
        Log.i(TAG, "onPreferenceClick: " + preference.getKey());
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onResume() {
        super.onResume();
        if (isFragmentCreated) {
            //设置监听器
            mListPreference.setOnPreferenceClickListener(this);
            mEditTextPreference.setOnPreferenceClickListener(this);
            mCheckBoxPreference.setOnPreferenceClickListener(this);
            mSwitchPreference.setOnPreferenceClickListener(this);
            mMultiSelectListPreference.setOnPreferenceClickListener(this);
            mPreference.setOnPreferenceClickListener(this);
            myPreference.setOnPreferenceClickListener(this);

            mListPreference.setOnPreferenceChangeListener(this);
            mEditTextPreference.setOnPreferenceChangeListener(this);
            mCheckBoxPreference.setOnPreferenceChangeListener(this);
            mSwitchPreference.setOnPreferenceChangeListener(this);
            mMultiSelectListPreference.setOnPreferenceChangeListener(this);
            mPreference.setOnPreferenceChangeListener(this);
            myPreference.setOnPreferenceChangeListener(this);
        } else {
            //显示全部preference保存值
            Log.i(TAG, "全部preference值------" + mSharedPrefs.getAll());
        }

        Log.i(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragmentCreated = false;
        Log.i(TAG, "onPause: ");
    }

    //此PreferenceFragment加载xml，具体实现preference功能
    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(preferences);
            isFragmentCreated = true;
            mListPreference = (ListPreference) this.findPreference(LIST_KEY);
            mEditTextPreference = (EditTextPreference) this.findPreference(EDIT_TEXT_KEY);
            mCheckBoxPreference = (CheckBoxPreference) this.findPreference(CHECK_BOX_KEY);
            mSwitchPreference = (SwitchPreference) this.findPreference(SWITCH_KEY);
            mMultiSelectListPreference = (MultiSelectListPreference) this.findPreference(MS_LIST_KEY);
            mPreference = this.findPreference(PREFERENCE_KEY);
            myPreference = (MyPreference) this.findPreference(CUSTOM_KEY);

            //设置EditTextPreference Dialog按钮信息
            mEditTextPreference.setPositiveButtonText("确定");
            mEditTextPreference.setNegativeButtonText("取消");
            //设置EditTextPreference Dialog图标
            mEditTextPreference.setDialogIcon(R.drawable.tools);

            Log.i(TAG, "onCreate: Prefs1Fragment");
        }
    }
}
