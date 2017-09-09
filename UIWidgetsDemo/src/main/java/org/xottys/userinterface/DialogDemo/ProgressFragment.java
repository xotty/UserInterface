package org.xottys.userinterface.DialogDemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.xottys.userinterface.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProgressFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProgressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private MyAlertDialogFragment myDialogFragment;

    public ProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AlertDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressFragment newInstance(String param1) {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //AlertDialog-13，Default_Light主题Dialog，DialogFragment方式使用  return inflater.inflate(R.layout.fragment_alertdialog, container, false);
        View v=inflater.inflate(R.layout.fragment_alertdialog, container, false);

   /* AlertDialog-1，两个Button
         * setIcon 设置对话框图标
         * setTitle 设置对话框标题
         * setMessage 设置对话框消息提示
         * setXXXButton 设置按钮文字和回调响应方法
         * 属性部分链式构造，部分正常构造
         */
        Button twoButton = (Button) v.findViewById(R.id.two_buttons);
        twoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
                myDialog.setIcon(R.drawable.alert_dialog_icon)
                        .setTitle("标题：我是一个普通双按钮Dialog")
                        .setMessage("内容：你要点击哪一个按钮呢?");
                myDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMessage("你点击了：'确定'");
                    }
                });
                myDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showMessage("你点击了：'取消'");
                            }
                        });
                //创建AlertDialog实例并显示
                myDialog.show();
            }
        });
        Button deviceDefaultLightButton = (Button) v.findViewById(R.id.two_buttons_default_light);
        deviceDefaultLightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //获取DialogFragment实例
                myDialogFragment = MyAlertDialogFragment.newInstance(
                        R.string.alert_dialog_two_buttons_title);
                //自动show方法，其中已经自带transaction.add,无需额外加载
                FragmentManager fm=getFragmentManager();
                myDialogFragment.show(fm,"d");
            }
        });

        return inflater.inflate(R.layout.fragment_alertdialog, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //用AlertDialog或Toast显示信息
    private void showMessage(final String msg) {
        if (TextUtils.isEmpty(msg)) return;
        new AlertDialog.Builder(getActivity())
                .setMessage(msg)
                .show();
        //Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    //设置AlertDialog 的位置和大小
    private void setDialogPosition(Dialog dg) {
        Window dialogWindow = dg.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

        //位置坐标，到Gravity边缘的距离
        lp.x = 100;
        lp.y = 50;
        //大小尺寸
        int width = WRAP_CONTENT;
        int height = WRAP_CONTENT;
        //设置大小
        lp.width = width;
        lp.height = height;

        dialogWindow.setAttributes(lp);
    }

    //AlertDialogFragment,在构造器中处理传入参数，在onCreateDialog中构造ALertDialog即可
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
            //获取传入的参数
            int title = getArguments().getInt("title");

            //根据参数值分辨构造不同的AlertDialog
            switch (title) {
                case R.string.alert_dialog_two_buttons2ultra_msg:
                    return new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.alert_dialog_two_buttons_msg)
                            .setMessage(R.string.alert_dialog_two_buttons2ultra_msg)
                            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:"+getResources().getString(R.string.alert_dialog_ok));
                                }
                            })
                            .setNeutralButton(R.string.alert_dialog_something, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:"+getResources().getString(R.string.alert_dialog_something));
                                }
                            })
                            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:"+getResources().getString(R.string.alert_dialog_cancel));

                                }
                            })
                            .create();

                case R.string.alert_dialog_two_buttons_title1:
                    return new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert)
                            .setIconAttribute(android.R.attr.alertDialogIcon)
                            .setTitle(R.string.alert_dialog_two_buttons_title1)
                            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:"+getResources().getString(R.string.alert_dialog_ok));
                                }
                            })
                            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:"+getResources().getString(R.string.alert_dialog_cancel));
                                }
                            })
                            .create();
                case R.string.alert_dialog_two_buttons_title2:
                    return new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert)
                            .setIconAttribute(android.R.attr.alertDialogIcon)
                            .setTitle(R.string.alert_dialog_two_buttons_title2)
                            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:"+getResources().getString(R.string.alert_dialog_ok));
                                }
                            })
                            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:"+getResources().getString(R.string.alert_dialog_cancel));
                                }
                            })
                            .create();
                case R.string.alert_dialog_two_buttons_title:
                    return new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
                            .setTitle(R.string.alert_dialog_two_buttons_title)
                            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:"+getResources().getString(R.string.alert_dialog_ok));

                                }
                            })
                            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:"+getResources().getString(R.string.alert_dialog_cancel));
                                }
                            })
                            .create();
            }
            return null;
        }

        private void showMessage(final String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
    }
}
