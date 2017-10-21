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
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.xottys.userinterface.R;

import java.lang.reflect.Field;

public class MatrialWidgetsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MatrialWidgetsFragment";
    NestedScrollView nestedScrollView;
    private AutoCompleteTextView mUserNameView;
    private TextInputLayout input_user_name, input_password;
    private EditText mPasswordView;
    private Button login_button;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_white:
                    nestedScrollView.setBackgroundColor(getResources().getColor(R.color.main_background));
                    return true;
                case R.id.bottom_navigation_green:
                    nestedScrollView.setBackgroundColor(getResources().getColor(R.color.google_green));

                    return true;
                case R.id.bottom_navigation_yellow:
                    nestedScrollView.setBackgroundColor(getResources().getColor(R.color.google_yellow));

                    return true;
                case R.id.bottom_navigation_blue:
                    nestedScrollView.setBackgroundColor(getResources().getColor(R.color.google_blue));

                    return true;
            }
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nestedScrollView = (NestedScrollView) inflater.inflate(R.layout.fragment_materialwidgets, container, false);

        initLoginView(nestedScrollView);

        Button btn_bottom_dialog = (Button) nestedScrollView.findViewById(R.id.btn_bottom_dialog);
        Button btn_fuulscreen_dialog = (Button) nestedScrollView.findViewById(R.id.btn_fullscreen_dialog);
        btn_bottom_dialog.setOnClickListener(this);
        btn_fuulscreen_dialog.setOnClickListener(this);


        BottomNavigationView navigation = (BottomNavigationView) nestedScrollView.findViewById(R.id.bottom_navigation);
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
//        et_main_3.requestFocus();
    }

    public void initLoginView(View view) {

        mUserNameView = (AutoCompleteTextView) view.findViewById(R.id.tv_user_name);
        mPasswordView = (EditText) view.findViewById(R.id.tv_password);
        input_user_name = (TextInputLayout) view.findViewById(R.id.input_user_name);
        input_password = (TextInputLayout) view.findViewById(R.id.input_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    Log.i(TAG, "Attempt to Login! ");
                    return true;
                }
                return false;
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

            case R.id.btn_forgot_password:
                Snackbar.make(v, getString(R.string.snackbar_forgot_password), Snackbar.LENGTH_LONG)
                        .setAction("^_^", null).show();
                break;

            case R.id.btn_forgot_register:
                Snackbar.make(v, getString(R.string.snackbar_register), Snackbar.LENGTH_SHORT)
                        .setAction("^_^", null).show();
                break;
            case R.id.btn_bottom_dialog:
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
                Button btn_dialog_bottom_sheet_ok = (Button) dialogView.findViewById(R.id.btn_dialog_bottom_sheet_ok);
                Button btn_dialog_bottom_sheet_cancel = (Button) dialogView.findViewById(R.id.btn_dialog_bottom_sheet_cancel);
                ImageView img_bottom_dialog = (ImageView) dialogView.findViewById(R.id.img_bottom_dialog);
                //Glide.with(getContext()).load(R.drawable.bottom_dialog).into(img_bottom_dialog);

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
                //  Glide.with(getContext()).load(R.drawable.google_assistant).into(img_full_screen_dialog);
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
