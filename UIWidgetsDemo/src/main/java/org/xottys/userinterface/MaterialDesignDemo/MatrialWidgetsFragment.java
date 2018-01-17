/**
 * 本例演示了下列Material Design控件用法
 * 1)TextInputLayout/TextInputEditText：
 * 2)FloatingActionButton
 * 3)Snackbar
 * 6)AppBarLayout
 * 7)CollapsingToolbarLayout
 * 8)CoordinatorLayout
 * <p>
 * 1）xml中用<android.support.v7.widget.Toolbar>定义，其中可选择定义一些简单控件
 * 2）使用ToolBar的Activity必须是AppCompatActivity，且要屏蔽actionBar（用相应Theme或requestWindowFeature(Window.FEATURE_NO_TITLE)）
 * 3）构建ToolBar的各个组件：NavigationIcon、Logo、Title、Subtitle、自定义控件、MenuItem（作为ActionBar时才会有，且其Item设置app:showAsAction）
 * 4）在styles.xml中用Theme方式定义各组件的颜色和大小，包括MenuItem弹出框的样式和位置
 * 5）setSupportActionBar(toolbar)
 * 6）定义NavigationIcon、自定义控件和菜单项点击事件
 * 7) onOptionsItemSelected中android.R.id.home分支失效
 * 8) 可以独立不作为ActionBar来使用，水平放置在屏幕的任何位置，此时不再具有Menu绑定关系
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:ToolBarActivity
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.MaterialDesignDemo;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import org.xottys.userinterface.R;

import java.lang.reflect.Field;

public class MatrialWidgetsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MatrialWidgetsFragment";
    NestedScrollView nestedScrollView;

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
        nestedScrollView = (NestedScrollView) inflater.inflate(R.layout.fragment_materialwidgets, container, false);

        initLoginView(nestedScrollView);

        Button btn_bottom_dialog = nestedScrollView.findViewById(R.id.btn_bottom_dialog);
        Button btn_fuulscreen_dialog = nestedScrollView.findViewById(R.id.btn_fullscreen_dialog);
        btn_bottom_dialog.setOnClickListener(this);
        btn_fuulscreen_dialog.setOnClickListener(this);


        BottomNavigationView navigation = nestedScrollView.findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // If BottomNavigationView has more than 3 items, using reflection to disable shift mode
        disableShiftMode(navigation);
        // ViewGroup viewGroup = (ViewGroup) mRecyclerView.getParent();
        // if (viewGroup != null) { viewGroup.removeAllViews(); }

        return nestedScrollView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initLoginView(View view) {
        //TextInputLayout视图
        AutoCompleteTextView mUserNameView = view.findViewById(R.id.tv_user_name);
        TextInputEditText mPasswordView = view.findViewById(R.id.tv_password);
        final TextInputLayout input_user_name = view.findViewById(R.id.input_user_name);
        final TextInputLayout input_password = view.findViewById(R.id.input_password);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//        public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//            if (id == getResources().getInteger(R.integer.customImeActionId) || id == EditorInfo.IME_NULL) {
//                Log.i(TAG, "Login:"+input_user_name.toString()+"/"+input_password.toString());
//                return true;
//            }
//            return false;
//        }
//    });

        Button forgot_password = view.findViewById(R.id.btn_forgot_password);
        forgot_password.setOnClickListener(this);
        Button register = view.findViewById(R.id.btn_forgot_register);
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
                Snackbar.make(v, getString(R.string.snackbar_register), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
            case R.id.btn_bottom_dialog:
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
                Button btn_dialog_bottom_sheet_ok = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_ok);
                Button btn_dialog_bottom_sheet_cancel = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_cancel);
                ImageView img_bottom_dialog = dialogView.findViewById(R.id.img_bottom_dialog);
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
                ImageView img_full_screen_dialog = fullscreenDialog.findViewById(R.id.img_full_screen_dialog);
                img_full_screen_dialog.setImageResource(R.drawable.google_assistant);
                ImageView img_dialog_fullscreen_close = fullscreenDialog.findViewById(R.id.img_dialog_fullscreen_close);
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

    //通过反射关闭BottomNavigationView中Item的移动
    public void disableShiftMode(BottomNavigationView navigationView) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
