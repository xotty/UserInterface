/**
 * 本例演示了ProgressDialog的使用方法
 * 1)环形进度条：标题、环形转轮、正文、按钮四部分组成
 * 2)水平进度条：标题、正文、水平进度和数值、按钮四部分组成
 * 3)自定义样式环形或水平进度条：可改变滚轮样式或水平进度条样式
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.DialogDemo;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.xottys.userinterface.R;

import static android.app.ProgressDialog.show;
public class ProgressDialogFragment extends Fragment {

    final static int MAX_PROGRESS = 80;
    // 记录进度对话框的完成百分比
    int progressStatus = 0;
    int hasData = 0;
    ProgressDialog pd1, pd2;
    //定义一个负责更新的进度的Handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 表明消息是由该程序发送的。
            if (msg.what == 0x123) {
                pd2.setProgress(progressStatus);
            }
        }
    };
    // 该程序模拟填充长度为100的数组
    private int[] data = new int[50];

    public ProgressDialogFragment() {
    }

    public static ProgressDialogFragment newInstance() {
        return new ProgressDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_progressdialog, container, false);

        //button1~button3环形进度条
        Button button1 = (Button) v.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 调用静态方法显示环形进度条
                final ProgressDialog dialog = show(getActivity(), null, "任务执行中，请等待......");
                // 3s后消失
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 2000);
            }
        });
        Button button2 = (Button) v.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 调用静态方法显示环形进度条，共5个参数：Context、标题、正文、是否不确定状态、进度条是否可以取消、进度条取消回调方法
                ProgressDialog.show(getActivity(), "向服务器发送数据", "任务执行中，请等待......", true, true,
                        new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                showMessage("任务被取消！");
                            }
                        });
            }
        });
        Button button3 = (Button) v.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(getActivity());
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
                dialog.setCancelable(true);             // 设置是否可以通过点击Back键取消
                dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
                dialog.setIndeterminate(false);         //进度值是否确定，true则在最小到最大值之间来回移动
                dialog.setIcon(R.drawable.alert_dialog_icon);// 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                dialog.setTitle("向服务器发送数据");
                dialog.setMessage("任务执行中，请等待......");
                dialog.setMax(MAX_PROGRESS);
                // dismiss监听
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        showMessage("onDismiss");
                    }
                });
                // 监听Key事件被传递给dialog
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode,
                                         KeyEvent event) {
                        showMessage("onKey");
                        return false;
                    }
                });
                // 监听cancel事件
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        showMessage("onCancel");
                    }
                });
                //设置可点击的按钮，最多有三个(默认情况下)
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showMessage("onClick:确定");
                            }
                        });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showMessage("onClick:取消");
                            }
                        });
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中间",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showMessage("onClick:中间");
                            }
                        });
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int progress = 0;
                        while (progress < MAX_PROGRESS) {
                            try {
                                Thread.sleep(100);
                                progress++;
                                dialog.setProgress(progress);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //进度达到最大值后，窗口消失
                        dialog.cancel();
                    }
                }).start();
            }
        });
        //button4~button6水平进度条
        Button button4 = (Button) v.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pd1 = new ProgressDialog(getActivity());
                // 设置对话框的标题
                pd1.setTitle("向服务器发送数据");
                // 设置对话框显示的内容
                pd1.setMessage("任务正在执行中，敬请等待......");
                // 设置对话框能用“取消”按钮关闭
                pd1.setCancelable(true);
                // 设置对话框的进度条风格
                pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // 设置对话框的进度条是否显示进度
                pd1.setIndeterminate(true);
                pd1.show();
            }
        });
        Button button5 = (Button) v.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 将进度条的完成进度重设为0
                progressStatus = 0;
                // 重新开始填充数组。
                hasData = 0;
                pd2 = new ProgressDialog(getActivity());
                pd2.setMax(MAX_PROGRESS);
                // 设置对话框的标题
                pd2.setTitle("任务完成百分比");
                // 设置对话框 显示的内容
                pd2.setMessage("耗时任务的完成百分比");
                // 设置对话框不能用“取消”按钮关闭
                pd2.setCancelable(false);
                // 设置对话框的进度条风格
                pd2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // 设置对话框的进度条是否显示进度
                pd2.setIndeterminate(false);
                pd2.show();
                new Thread() {
                    public void run() {
                        while (progressStatus < MAX_PROGRESS) {
                            // 获取耗时操作的完成百分比
                            progressStatus = MAX_PROGRESS
                                    * doWork() / data.length;
                            // 发送空消息到Handler
                            handler.sendEmptyMessage(0x123);
                        }
                        // 如果任务已经完成
                        if (progressStatus >= MAX_PROGRESS) {
                            // 关闭对话框
                            pd2.dismiss();
                        }
                    }
                }.start();
            }
        });
        Button button6 = (Button) v.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pd1 = new ProgressDialog(getActivity());
                // 设置对话框的标题
                pd1.setTitle("向服务器发送数据");
                // 设置对话框显示的内容
                pd1.setMessage("任务正在执行中，敬请等待......");
                // 设置对话框能用“取消”按钮关闭
                pd1.setCancelable(true);
                // 设置对话框的进度条风格
                pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // 设置对话框的进度条是否显示进度
                pd1.setIndeterminate(true);
                pd1.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showMessage("点击了'确定！");
                            }
                        });
                pd1.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showMessage("点击了'取消！");
                            }
                        });
                pd1.setButton(DialogInterface.BUTTON_NEUTRAL, "中间",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showMessage("点击了'中间！");
                            }
                        });
                pd1.show();
            }
        });

        //通过自定义布局设置环形进度转轮的样式
        Button button7 = (Button) v.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProgressDialog mDialog = new ProgressDialog(getActivity());
                mDialog.setCancelable(true);  //是否可以被取消
                mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//圆环风格
                mDialog.show();
                mDialog.setContentView(R.layout.progressdialog_customize2);//自定义布局
                ((TextView) (mDialog.findViewById(R.id.tv))).setText("Loading......");//加载显示的信息
            }
        });

        //通过自定义控件设置水平进度条的样式和文字的样式
        Button button8 = (Button) v.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final MyProgressDialog mDialog = new MyProgressDialog(getActivity());
                mDialog.setMessage("正在下载");
                mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        showMessage("onCancel");
                    }
                });
                mDialog.setIndeterminate(false);
                mDialog.setCancelable(true);
                mDialog.setMax(MAX_PROGRESS);

                mDialog.show();
                mDialog.setTextColor(Color.RED);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int progress = 0;
                        while (progress < MAX_PROGRESS) {
                            try {
                                Thread.sleep(100);
                                progress++;
                                mDialog.setProgress(progress);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //进度达到最大值后，窗口消失
                        mDialog.cancel();
                    }
                }).start();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //用AlertDialog或Toast显示信息
    private void showMessage(final String msg) {
        if (TextUtils.isEmpty(msg)) return;
        new AlertDialog.Builder(getActivity())
                .setMessage(msg)
                .show();
        //Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    // 模拟一个耗时的操作。
    public int doWork() {
        // 为数组元素赋值
        data[hasData++] = (int) (Math.random() * 100);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hasData;
    }
}
