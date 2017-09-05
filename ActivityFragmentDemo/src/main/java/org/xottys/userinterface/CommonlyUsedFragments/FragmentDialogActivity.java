/**
 * 本例演示了DialogFragment的基本用法：
 * 1)获取DialogFragment实例
 * 2)启动DialogFragment的show方法
 * 3)在onCreateDialog中构造和返回Dialog对象
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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import org.xottys.userinterface.R;

public class FragmentDialogActivity extends Activity {
    private static final String TAG = "DialogFragment";
    private DialogFragment myDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取DialogFragment实例
        myDialogFragment = MyAlertDialogFragment.newInstance(
                R.string.alert_dialog_two_buttons_title);

        //自动show方法，其中已经自带transaction.add,无需额外加载
        myDialogFragment.show(getFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        Log.i(TAG, "Positive click!");
        finish();
    }

    public void doNegativeClick() {
        Log.i(TAG, "Negative click!");
        finish();
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        //将Title参数传入构造器
        public static MyAlertDialogFragment newInstance(int title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        //构造一个完整的Dialog
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.alert_dialog_icon)
                    .setTitle(title)
                    .setPositiveButton(R.string.alert_dialog_ok,
                       new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int whichButton) {
                              ((FragmentDialogActivity) getActivity()).doPositiveClick();
                                }
                            }
                    )
                    .setNegativeButton(R.string.alert_dialog_cancel,
                       new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((FragmentDialogActivity) getActivity()).doNegativeClick();
                                }
                            }
                    )
                    .create();
        }
    }
}
