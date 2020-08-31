/**
 * 本例在MD WIDGETS中演示了下列Material Design主要控件的用法：
 * 1)TextInputLayout/TextInputEditText
 * 2)FloatingActionButton
 * 3)Snackbar
 * 4)TabLayout
 * 5)NestedScrollView
 * 6)BottomNavigationView
 * 7)BottomSheetDialog
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:MaterialDesignFragment
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdvancedViewGroup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import org.xottys.userinterface.R;

import java.lang.reflect.Field;

public class MaterialDesignFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MaterialDesignFragment";
    NestedScrollView nestedScrollView;
    TextInputLayout input_password;
    TextInputEditText input_user_name;

    //BottomNavigationView中Item点击事件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_white:
                    nestedScrollView.setBackgroundColor(getResources().getColor(R.color.main_background, null));
                    return true;
                case R.id.bottom_navigation_green:
                    nestedScrollView.setBackgroundColor(getResources().getColor(R.color.google_green, null));

                    return true;
                case R.id.bottom_navigation_yellow:
                    nestedScrollView.setBackgroundColor(getResources().getColor(R.color.google_yellow, null));

                    return true;
                case R.id.bottom_navigation_blue:
                    nestedScrollView.setBackgroundColor(getResources().getColor(R.color.google_blue, null));

                    return true;
            }
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(
                getActivity(), R.style.AppTheme_NoActionBar);
        LayoutInflater localInflater = inflater
                .cloneInContext(contextThemeWrapper);

        nestedScrollView = (NestedScrollView) localInflater.inflate(R.layout.fragment_materialwidgets, container, false);

        initLoginView(nestedScrollView);

        Button btn_bottom_dialog = (Button) nestedScrollView.findViewById(R.id.btn_bottom_dialog);
        Button btn_fuulscreen_dialog = (Button) nestedScrollView.findViewById(R.id.btn_fullscreen_dialog);
        btn_bottom_dialog.setOnClickListener(this);
        btn_fuulscreen_dialog.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) nestedScrollView.findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // If BottomNavigationView has more than 3 items, using reflection to disable shift mode
        // disableShiftMode(navigation);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        return nestedScrollView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initLoginView(View view) {
        //TextInputLayout视图
        AutoCompleteTextView tvpassword = (AutoCompleteTextView) view.findViewById(R.id.tv_password);
        input_password = (TextInputLayout) view.findViewById(R.id.input_password);
        input_user_name = (TextInputEditText) view.findViewById(R.id.input_user_name);

        //密码输入框再次获得焦点时删除错误提示信息
        tvpassword.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    input_password.setErrorEnabled(false);
                } else {
                    // 此处为失去焦点时的处理内容
                    Log.i(TAG, "onFocusChange: Lost Focus");
                }
            }
        });

        Button forgot_password = (Button) view.findViewById(R.id.btn_forgot_password);
        forgot_password.setOnClickListener(this);
        Button register = (Button) view.findViewById(R.id.btn_forgot_register);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Snackbar是一种针对操作的轻量级反馈机制，它们出现在屏幕所有层的最上方，包括浮动操作按钮；屏幕上同时最多只能现实一个Snackbar
            case R.id.btn_forgot_password:
                Snackbar.make(v, getString(R.string.snackbar_forgot_password), Snackbar.LENGTH_LONG)
                        .show();
                break;
            //与Totast区别：可以提供一个文字操作按钮，可以在屏幕上滑动关闭
            case R.id.btn_forgot_register:
                input_password.setErrorEnabled(true);
                //设置错误提示信息
                input_password.setError("密码不正确");
                input_user_name.setError("电话或邮箱不正确");
                //焦点转移的用户名输入框
                input_user_name.requestFocus();
                break;
            case R.id.btn_bottom_dialog:
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, nestedScrollView);
                Button btn_dialog_bottom_sheet_ok = (Button) dialogView.findViewById(R.id.btn_dialog_bottom_sheet_ok);
                Button btn_dialog_bottom_sheet_cancel = (Button) dialogView.findViewById(R.id.btn_dialog_bottom_sheet_cancel);
                ImageView img_bottom_dialog = (ImageView) dialogView.findViewById(R.id.img_bottom_dialog);
                img_bottom_dialog.setImageResource(R.drawable.google_assistant);
                mBottomSheetDialog.setContentView(dialogView);

                btn_dialog_bottom_sheet_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });
                btn_dialog_bottom_sheet_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });
                mBottomSheetDialog.show();
                break;

            case R.id.btn_fullscreen_dialog:
                final Dialog fullscreenDialog = new Dialog(getContext(), R.style.DialogFullscreen);
                fullscreenDialog.setContentView(R.layout.dialog_fullscreen);
                ImageView img_full_screen_dialog = (ImageView) fullscreenDialog.findViewById(R.id.img_full_screen_dialog);
                img_full_screen_dialog.setImageResource(R.drawable.google_assistant);
                ImageView img_dialog_fullscreen_close = (ImageView) fullscreenDialog.findViewById(R.id.img_dialog_fullscreen_close);
                img_dialog_fullscreen_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fullscreenDialog.dismiss();
                    }
                });
                fullscreenDialog.show();
                break;
        }
    }

//    //通过反射关闭BottomNavigationView中Item的移动
//    public void disableShiftMode(BottomNavigationView navigationView) {
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
//        try {
//            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//            shiftingMode.setAccessible(true);
//            shiftingMode.setBoolean(menuView, false);
//            shiftingMode.setAccessible(false);
//
//            for (int i = 0; i < menuView.getChildCount(); i++) {
//                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
//                itemView.setShiftingMode(false);
//                itemView.setChecked(itemView.getItemData().isChecked());
//            }
//
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
}
