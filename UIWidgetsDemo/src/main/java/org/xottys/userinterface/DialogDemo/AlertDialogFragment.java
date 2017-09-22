/**本例演示了AlertDialog的创建和使用方法
 * 1)创建方法一：AlertDialog.Builder构造Builder（例1～2）
 * 2)创建方法二：AlertDialog.Builder.create构造AlertDialog（例3～5,6~7）
 * 例3～5：在builder中设置各项属性，例6～7：用alertDialog方法设置主要属性
 * 3)使用方法一：Builder.show或AlertDialog.show(例1~7)
 * 4)使用方法二：AlertDialogFragment.show(例8~11)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.DialogDemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xottys.userinterface.R;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlertDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlertDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertDialogFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private OnFragmentInteractionListener mListener;


    private MyAlertDialogFragment myDialogFragment;

    public AlertDialogFragment() {
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
    public static AlertDialogFragment newInstance(String param1) {
        AlertDialogFragment fragment = new AlertDialogFragment();
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
        View v = inflater.inflate(R.layout.fragment_alertdialog, container, false);
        initView(v);
        return v;
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

    //13种AlertDialog演示
    private void initView(View v) {
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

        //AlertDialog-2，三个Button，在Builder构造时加了主题参数，全部链式构造
        Button threeButton = (Button) v.findViewById(R.id.three_buttons);
        threeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Dialog_Alert)
                        .setIcon(R.drawable.alert_dialog_icon)
                        .setTitle("标题：我是一个带主题的Dialog")
                        .setMessage("你要点击哪一个按钮呢?")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showMessage("你点击了：'确定'");
                                    }
                                })
                        .setNeutralButton("中间按钮",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showMessage("你点击了：'中间按钮'");
                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showMessage("你点击了：'取消'");
                            }
                        })
                        .show();
            }
        });

        //AlertDialog-3，列表Dialog,用builder.create构建
        Button text_listButton = (Button) v.findViewById(R.id.text_list);
        text_listButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String[] items = {"选项1", "选项2", "选项3", "选项4"};
                AlertDialog listDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("我是一个列表Dialog")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // which下标从0开始
                                showMessage("你点击了" + items[which]);
                            }
                        })
                        .create();
                listDialog.show();
            }
        });

        //AlertDialog-4，单选Dialog,用builder.create构建,部分属性用Builder设置，部分属性用AlertDialog设置
        Button singleSelectButton = (Button) v.findViewById(R.id.radio_button);
        singleSelectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String[] items = {"选项1", "选项2", "选项3", "选项4"};
                AlertDialog singleChoiceDialog =
                        new AlertDialog.Builder(getActivity())
                                // 第二个参数是默认选项，此处设置为0
                                .setSingleChoiceItems(items, 0,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                showMessage("你选择了" + items[which]);
                                            }
                                        })
                                .setTitle("我是一个单选Dialog")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which >= 0)
                                            showMessage("你点击了'确定'，选择了" + items[which]);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showMessage("你点击了'取消'");
                                    }
                                })
                                .create();
                singleChoiceDialog.show();
            }
        });

        //AlertDialog-5，多选Dialog
        Button multiSelectButton = (Button) v.findViewById(R.id.checkbox_button);
        multiSelectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String[] items = {"选项1", "选项2", "选项3", "选项4"};
                // 设置默认选中的选项，全为false默认均未选中
                final ArrayList<Integer> yourChoices = new ArrayList<>();
                final boolean initChoiceSets[] = {false, false, false, false};
                yourChoices.clear();
                AlertDialog multiChoiceDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("我是一个多选Dialog")
                        .setMultiChoiceItems(items, initChoiceSets,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which,
                                                        boolean isChecked) {
                                        if (isChecked) {
                                            yourChoices.add(which);
                                        } else {
                                            yourChoices.remove(which);
                                        }
                                    }
                                })
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int size = yourChoices.size();
                                        String str = "";
                                        for (int i = 0; i < size; i++) {
                                            str += items[yourChoices.get(i)] + " ";
                                        }
                                        showMessage("你选中了" + str);
                                    }
                                })
                        .create();
                multiChoiceDialog.show();
            }
        });


        //AlertDialog-6，文本输入Dialog
        Button textEntryButton = (Button) v.findViewById(R.id.text_entry);
        textEntryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText editText = new EditText(getActivity());
                AlertDialog inputDialog = new AlertDialog.Builder(getActivity()).create();
                inputDialog.setTitle("我是一个文本输入Dialog");
                inputDialog.setView(editText);
                inputDialog.setButton(BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMessage("输入了：" + editText.getText().toString());
                    }
                });
                inputDialog.setButton(BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMessage("你点击了'取消'");
                    }
                });
                inputDialog.show();
            }
        });

        //AlertDialog-7，自定义文本输入Dialog
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //处理"取消"按钮的点击
                showMessage(msg.obj.toString());
            }
        };
        Button customEextEntryButton = (Button) v.findViewById(R.id.custom_text_entry);
        customEextEntryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final View dialogView = LayoutInflater.from(getActivity())
                        .inflate(R.layout.dialog_customize, null);
                AlertDialog customizeDialog =
                        new AlertDialog.Builder(getActivity()).create();
                customizeDialog.setTitle("我是一个自定义文本输入Dialog");
                customizeDialog.setView(dialogView);
                customizeDialog.setButton(BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        EditText edit_text1 =
                                (EditText) dialogView.findViewById(R.id.edit_text1);
                        EditText edit_text2 =
                                (EditText) dialogView.findViewById(R.id.edit_text2);
                        showMessage(edit_text1.getText().toString() + "／" + edit_text2.getText().toString());
                    }
                });
                //另一种按钮设置方式，在handler中处理点击事件
                Message msg = handler.obtainMessage();
                msg.obj = "你点击了'取消'";
                customizeDialog.setButton(BUTTON_NEGATIVE, "取消", msg);
                setDialogPosition(customizeDialog);
                customizeDialog.show();
            }
        });

        //AlertDialog-8，超长文本Dialog，DialogFragment方式使用
        Button ultraButton = (Button) v.findViewById(R.id.two_buttons2ultra);
        ultraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //获取DialogFragment实例
                myDialogFragment = MyAlertDialogFragment.newInstance(
                        R.string.alert_dialog_two_buttons2ultra_msg);
                //自动show方法，其中已经自带transaction.add,无需额外加载
                myDialogFragment.show(getFragmentManager(), "dialog");
            }
        });
        //AlertDialog-9，Tradition主题Dialog，DialogFragment方式使用
        Button traditionThemeButton = (Button) v.findViewById(R.id.two_buttons_old_school);
        traditionThemeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //获取DialogFragment实例
                myDialogFragment = MyAlertDialogFragment.newInstance(
                        R.string.alert_dialog_two_buttons_title1);
                //自动show方法，其中已经自带transaction.add,无需额外加载
                myDialogFragment.show(getFragmentManager(), "dialog");
            }
        });

        //AlertDialog-10，Holo_Light主题Dialog，DialogFragment方式使用
        Button holoLightThemeButton = (Button) v.findViewById(R.id.two_buttons_holo_light);
        holoLightThemeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //获取DialogFragment实例
                myDialogFragment = MyAlertDialogFragment.newInstance(
                        R.string.alert_dialog_two_buttons_title2);

                //自动show方法，其中已经自带transaction.add,无需额外加载
                myDialogFragment.show(getFragmentManager(), "dialog");
            }
        });

        //AlertDialog-11，Default_Light主题Dialog，DialogFragment方式使用
        Button deviceDefaultLightButton = (Button) v.findViewById(R.id.two_buttons_default_light);
        deviceDefaultLightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //获取DialogFragment实例
                myDialogFragment = MyAlertDialogFragment.newInstance(
                        R.string.alert_dialog_two_buttons_title);
                //自动show方法，其中已经自带transaction.add,无需额外加载
                myDialogFragment.show(getFragmentManager(), "dialog");
            }
        });
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
        void onFragmentInteraction(Uri uri);
    }

    //内嵌的AlertDialogFragment,在构造器中处理传入参数，在onCreateDialog中构造ALertDialog即可
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
                                    showMessage("你点击了:" + getResources().getString(R.string.alert_dialog_ok));
                                }
                            })
                            .setNeutralButton(R.string.alert_dialog_something, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:" + getResources().getString(R.string.alert_dialog_something));
                                }
                            })
                            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:" + getResources().getString(R.string.alert_dialog_cancel));

                                }
                            })
                            .create();

                case R.string.alert_dialog_two_buttons_title1:
                    return new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert)
                            .setIconAttribute(android.R.attr.alertDialogIcon)
                            .setTitle(R.string.alert_dialog_two_buttons_title1)
                            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:" + getResources().getString(R.string.alert_dialog_ok));
                                }
                            })
                            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:" + getResources().getString(R.string.alert_dialog_cancel));
                                }
                            })
                            .create();
                case R.string.alert_dialog_two_buttons_title2:
                    return new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Light_Dialog_Alert)
                            .setIconAttribute(android.R.attr.alertDialogIcon)
                            .setTitle(R.string.alert_dialog_two_buttons_title2)
                            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:" + getResources().getString(R.string.alert_dialog_ok));
                                }
                            })
                            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:" + getResources().getString(R.string.alert_dialog_cancel));
                                }
                            })
                            .create();
                case R.string.alert_dialog_two_buttons_title:
                    return new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
                            .setTitle(R.string.alert_dialog_two_buttons_title)
                            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:" + getResources().getString(R.string.alert_dialog_ok));

                                }
                            })
                            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    showMessage("你点击了:" + getResources().getString(R.string.alert_dialog_cancel));
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
