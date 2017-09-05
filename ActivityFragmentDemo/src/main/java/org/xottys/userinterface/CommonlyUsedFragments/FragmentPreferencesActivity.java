/**
 * 本例演示了PreferencesFragment的基本用法：
 * 1)在onCreate中加载XML Preferences
 * 2)加载Fragment(此步骤本例简化成直接用MyWebViewFragment替换android.R.id.content)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FragmentPreferencesActivity
 * <br/>Date:Sep，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */

package org.xottys.userinterface.CommonlyUsedFragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.xottys.userinterface.R;

public class FragmentPreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载fragment，并将其作为主显示内容
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefsFragment()).commit();
    }

    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //从XML文件中加载Preferences
            addPreferencesFromResource(R.xml.fragment_preferences);
        }
    }

}
