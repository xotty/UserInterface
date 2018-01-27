/**本例演示了PickerDialog和NumberPicker+AlertDialog的使用方法
 * 1)TimePicker，12小时时间选择对话框：四种显示模式
 * 2)TimePicker，24小时时间选择对话框：四种显示模式
 * 3)DatePicker，日期选择对话框：三种主题、五种显示方式、两种复合显示方式
 * 4)NumberPicker，数字选择器：数字方式、文本方式、自定义字体大小、颜色和分割线颜色
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.dialogdemo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.xottys.userinterface.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static org.xottys.userinterface.R.id.mnp;
import static org.xottys.userinterface.R.id.np1;

public class PickerDialogFragment extends Fragment {
    //省市县三级联动选择器数据结构
    HashMap[] citys = null;
    int selectedID;
    int mSeletedIndex1, mSeletedIndex2;
    //时间监听器
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
            showMessage("您选择了：" + hourOfDay + "时" + minute + "分");
        }
    };
    //日期监听器
    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker dp, int year,
                              int month, int dayOfMonth) {
            showMessage("您选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日");
        }
    };
    private String[] state = new String[31];
    private String[] city = new String[20];
    private String[] county = new String[20];
    private Integer[] stateid, cityid;



    public PickerDialogFragment() {

    }

    public static PickerDialogFragment newInstance() {
        return new PickerDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pickerdialog, container, false);

        //Button11~Button14,12小时时间选择对话框,四种不同主题显示
        Button button11 = (Button) v.findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new TimePickerDialog(getActivity(),
                        onTimeSetListener,          // 绑定监听器
                        c.get(Calendar.HOUR_OF_DAY),//设置初始时间:时
                        c.get(Calendar.MINUTE),     //设置初始时间：分
                        false).show();              //fasle表示采用12小时制
            }
        });
        Button button12 = (Button) v.findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new TimePickerDialog(getActivity(), android.R.style.Theme_Dialog,
                        onTimeSetListener,
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        false).show();
            }
        });
        Button button13 = (Button) v.findViewById(R.id.button13);
        button13.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                // 创建一个TimePickerDialog实例，并把它显示出来。
                new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Panel,
                        // 绑定监听器
                        onTimeSetListener,
                        //设置初始时间
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        //fasle表示采用12小时制
                        false).show();
            }
        });
        Button button14 = (Button) v.findViewById(R.id.button14);
        button14.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Panel,
                        onTimeSetListener,
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        false).show();
            }
        });

        //Button21~Button24,24小时时间选择对话框,四种不同主题显示
        Button button21 = (Button) v.findViewById(R.id.button21);
        button21.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                // 创建一个TimePickerDialog实例，并把它显示出来。
                new TimePickerDialog(getActivity(),
                        onTimeSetListener,          // 绑定监听器
                        c.get(Calendar.HOUR_OF_DAY),//设置初始时间：时
                        c.get(Calendar.MINUTE),     //设置初始时间：分
                        true).show();               //true表示采用24小时制
            }
        });
        Button button22 = (Button) v.findViewById(R.id.button22);
        button22.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new TimePickerDialog(getActivity(), android.R.style.Theme_Dialog,
                        onTimeSetListener,
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        true).show();
            }
        });
        Button button23 = (Button) v.findViewById(R.id.button23);
        button23.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Panel,
                        onTimeSetListener,
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        true).show();
            }
        });
        Button button24 = (Button) v.findViewById(R.id.button24);
        button24.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Panel,
                        onTimeSetListener,
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        true).show();
            }
        });
        //Button31~Button33,日期选择对话框,MaterialDesign风格三种不同主题显示
        Button button31 = (Button) v.findViewById(R.id.button31);
        button31.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(), onDateSetListener
                        , c.get(Calendar.YEAR)
                        , c.get(Calendar.MONTH)
                        , c.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        Button button32 = (Button) v.findViewById(R.id.button32);
        button32.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(),
                        android.R.style.Theme_DeviceDefault_Dialog_Alert, //旧版使用AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        onDateSetListener,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Button button33 = (Button) v.findViewById(R.id.button33);
        button33.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert, //旧版使用AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                        onDateSetListener,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }

        });

        //Button41~Button43,日期选择对话框,Holo风格三种种不同主题显示
        Button button41 = (Button) v.findViewById(R.id.button41);
        button41.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Dialog,   //旧版用AlertDialog.THEME_TRADITIONAL,
                        onDateSetListener,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        Button button42 = (Button) v.findViewById(R.id.button42);
        button42.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Panel,   //旧版本使用AlertDialog.THEME_HOLO_DARK,
                        onDateSetListener,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        Button button43 = (Button) v.findViewById(R.id.button43);
        button43.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Panel,  //旧版本使用AlertDialog.THEME_HOLO_LIGHT,
                        onDateSetListener,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();

            }

        });

        //Button51~Button53,复合日期选择对话框,Holo风格三种种不同主题显示
        Button button51 = (Button) v.findViewById(R.id.button51);
        button51.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dp = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Panel,  //旧版本使用AlertDialog.THEME_HOLO_LIGHT,
                        onDateSetListener,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setCalendarViewShown(true);
                dp.getDatePicker().setSpinnersShown(false);
                dp.show();
            }
        });
        Button button52 = (Button) v.findViewById(R.id.button52);
        button52.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dp = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Dialog, //旧版用AlertDialog.THEME_TRADITIONAL,
                        onDateSetListener,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setCalendarViewShown(true);
                dp.getDatePicker().setSpinnersShown(true);
                dp.show();
            }
        });
        Button button53 = (Button) v.findViewById(R.id.button53);
        button53.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dp = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Panel,  //旧版本使用AlertDialog.THEME_HOLO_LIGHT,
                        onDateSetListener,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setCalendarViewShown(true);
                dp.getDatePicker().setSpinnersShown(true);
                dp.show();
            }
        });

        //标准数字选择器，叠加到对话框上
        Button button6 = (Button) v.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.numberpicker1, null);
                final NumberPicker mNumberPicker = (NumberPicker) view.findViewById(np1);
                mNumberPicker.setMinValue(0);
                mNumberPicker.setMaxValue(30);
                mNumberPicker.setValue(15);
                new AlertDialog.Builder(getActivity())
                        .setTitle("请设置温度")
                        .setView(view)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showMessage("你设置了：" + mNumberPicker.getValue());
                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });

        //用数字选择器显示文本，且实现三轮联动，叠加到对话框上
        Button button7 = (Button) v.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.numberpicker2, null);
                final NumberPicker mNumberPicker1 = (NumberPicker) view.findViewById(R.id.picker1);
                final NumberPicker mNumberPicker2 = (NumberPicker) view.findViewById(R.id.picker2);
                final NumberPicker mNumberPicker3 = (NumberPicker) view.findViewById(R.id.picker3);

                //准备三轮的数据：省市县，Map数组中每一行是一个Map，其从属关系用parenetid来标示
                HashMap map1 = new HashMap();
                map1.put("id", 1);
                map1.put("parentid", 0);
                map1.put("name", "陕西");
                HashMap map2 = new HashMap();
                map2.put("id", 101);
                map2.put("parentid", 1);
                map2.put("name", "汉中");
                HashMap map3 = new HashMap();
                map3.put("id", 1001);
                map3.put("parentid", 101);
                map3.put("name", "汉台");
                HashMap map4 = new HashMap();
                map4.put("id", 1002);
                map4.put("parentid", 101);
                map4.put("name", "城固");
                HashMap map5 = new HashMap();
                map5.put("id", 1003);
                map5.put("parentid", 101);
                map5.put("name", "洋县");
                HashMap map6 = new HashMap();
                map6.put("id", 102);
                map6.put("parentid", 1);
                map6.put("name", "西安");
                HashMap map7 = new HashMap();
                map7.put("id", 1004);
                map7.put("parentid", 102);
                map7.put("name", "雁塔");
                HashMap map13 = new HashMap();
                map13.put("id", 1007);
                map13.put("parentid", 102);
                map13.put("name", "莲湖");

                HashMap map8 = new HashMap();
                map8.put("id", 2);
                map8.put("parentid", 0);
                map8.put("name", "广东");
                HashMap map9 = new HashMap();
                map9.put("id", 105);
                map9.put("parentid", 2);
                map9.put("name", "深圳");
                HashMap map10 = new HashMap();
                map10.put("id", 1005);
                map10.put("parentid", 105);
                map10.put("name", "南山");
                HashMap map11 = new HashMap();
                map11.put("id", 1006);
                map11.put("parentid", 105);
                map11.put("name", "宝安");
                HashMap map14 = new HashMap();
                map14.put("id", 1008);
                map14.put("parentid", 105);
                map14.put("name", "福田");

                HashMap map15 = new HashMap();
                map15.put("id", 106);
                map15.put("parentid", 2);
                map15.put("name", "广州");
                HashMap map16 = new HashMap();
                map16.put("id", 1009);
                map16.put("parentid", 106);
                map16.put("name", "白云");
                HashMap map17 = new HashMap();
                map17.put("id", 1010);
                map17.put("parentid", 106);
                map17.put("name", "天河");

                HashMap map12 = new HashMap();
                map12.put("id", 3);
                map12.put("parentid", 0);
                map12.put("name", "浙江");
                HashMap map18 = new HashMap();
                map18.put("id", 111);
                map18.put("parentid", 3);
                map18.put("name", "杭州");
                HashMap map20 = new HashMap();
                map20.put("id", 1110);
                map20.put("parentid", 111);
                map20.put("name", "西湖");
                HashMap map21 = new HashMap();
                map21.put("id", 1111);
                map21.put("parentid", 111);
                map21.put("name", "萧山");

                HashMap map19 = new HashMap();
                map19.put("id", 112);
                map19.put("parentid", 3);
                map19.put("name", "温州");
                HashMap map22 = new HashMap();
                map22.put("id", 1112);
                map22.put("parentid", 112);
                map22.put("name", "乐清");
                HashMap map23 = new HashMap();
                map23.put("id", 1113);
                map23.put("parentid", 112);
                map23.put("name", "瓯海");
                citys = new HashMap[]{map1, map2, map3, map4, map5, map6, map7, map8, map9, map10, map11, map12, map13, map14, map15, map16, map17, map18, map19, map20, map21, map22, map23};

                //设置数据源
                state = pickCities(0);
                mNumberPicker1.setDisplayedValues(state);
                //设置数据长度
                mNumberPicker1.setMinValue(0);
                mNumberPicker1.setMaxValue(state.length - 1);
                mNumberPicker1.setValue(0);
                //设置数据源
                city = pickCities(1);
                mNumberPicker2.setDisplayedValues(city);
                //设置数据长度
                mNumberPicker2.setMinValue(0);
                mNumberPicker2.setMaxValue(city.length - 1);
                mNumberPicker2.setValue(0);
                //设置数据源
                county = pickCities(101);
                mNumberPicker3.setDisplayedValues(county);
                //设置数据长度
                mNumberPicker3.setMinValue(0);
                mNumberPicker3.setMaxValue(county.length - 1);
                mNumberPicker3.setValue(0);

                //设置省数值改变监听
                mNumberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        mSeletedIndex1 = newVal;
                    }
                });

                //设置省滚动监听
                mNumberPicker1.setOnScrollListener(new NumberPicker.OnScrollListener() {
                    @Override
                    public void onScrollStateChange(NumberPicker view, int scrollState) {
                        switch (scrollState) {
                            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                                //惯性滑动
                                break;
                            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                                //手动滑动
                                break;
                            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                                //停止滑动
                                //根据当前省的数据设置市的数据
                                selectedID = stateid[mSeletedIndex1];
                                city = pickCities(selectedID);
                                mNumberPicker2.setDisplayedValues(city);
                                mNumberPicker2.setMinValue(0);
                                mNumberPicker2.setMaxValue(city.length - 1);
                                mNumberPicker2.setValue(0);
                                //根据第一个市的数据设置县的数据
                                county = pickCities(cityid[0]);
                                mNumberPicker3.setDisplayedValues(county);
                                mNumberPicker3.setMinValue(0);
                                mNumberPicker3.setMaxValue(county.length - 1);
                                mNumberPicker3.setValue(0);
                                break;
                        }
                    }
                });
                //设置市数值改变监听
                mNumberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        mSeletedIndex2 = newVal;
                    }
                });
                //设置市滚动监听
                mNumberPicker2.setOnScrollListener(new NumberPicker.OnScrollListener() {
                    @Override
                    public void onScrollStateChange(NumberPicker view, int scrollState) {
                        switch (scrollState) {
                            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                                //惯性滑动
                                break;
                            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                                //手动滑动
                                break;
                            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                                //停止滑动
                                //根据当前市的数据设置县的数据
                                selectedID = cityid[mSeletedIndex2];
                                county = pickCities(selectedID);
                                mNumberPicker3.setDisplayedValues(county);
                                mNumberPicker3.setMinValue(0);
                                mNumberPicker3.setMaxValue(county.length - 1);
                                mNumberPicker3.setValue(0);
                                break;
                        }

                    }
                });
                //将三级联动滚轮叠加到对话框上
                new AlertDialog.Builder(getActivity())
                        .setView(view)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showMessage("你设置了：" + state[mNumberPicker1.getValue()] + city[mNumberPicker2.getValue()] + county[mNumberPicker3.getValue()]);
                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });

        //自定义数字选择器，可以设置字体大小、颜色和分割线颜色、是否循环滚动
        Button button8 = (Button) v.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(
                        R.layout.numberpicker_cutomize, null);
                final MyNumberPicker mNumberPicker = (MyNumberPicker) view.findViewById(mnp);
                mNumberPicker.setMinValue(0);
                mNumberPicker.setMaxValue(30);
                mNumberPicker.setValue(15);
                mNumberPicker.setWrapSelectorWheel(false);    //设置是否循环滚动
                mNumberPicker.setNumberPickerDividerColor();  //利用反射设置分割线颜色

                //将对话框标题居中显示，并设置颜色
                TextView tv = new TextView(getActivity());
                tv.setText("请设置温度");
                tv.setTextSize(20);
                tv.setPadding(0, 40, 0, 0);
                tv.setTextColor(Color.BLUE);
                tv.setGravity(Gravity.CENTER);
                //将数字选择器叠加到对话框上
                new AlertDialog.Builder(getActivity())
                        .setCustomTitle(tv)
                        .setView(view)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showMessage("你设置了：" + mNumberPicker.getValue());
                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
        return v;
    }

    //根据传入的id参数在Map数组中遍历找出所有parentid与之相同的项目
    private String[] pickCities(int parentid) {
        ArrayList<String> al1 = new ArrayList<>();
        ArrayList<Integer> al2 = new ArrayList<>();
        for (HashMap mp : citys) {
            if ((int) mp.get("parentid") == parentid) {
                al1.add((String) mp.get("name"));
                al2.add((Integer) mp.get("id"));
            }
        }
        if (parentid == 0)
            stateid = al2.toArray(new Integer[al2.size()]);
        else if (parentid < 100)
            cityid = al2.toArray(new Integer[al2.size()]);

        return al1.toArray(new String[al1.size()]);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //用AlertDialog显示信息
    private void showMessage(final String msg) {
        if (TextUtils.isEmpty(msg)) return;
        new AlertDialog.Builder(getActivity())
                .setMessage(msg)
                .show();
    }
}
