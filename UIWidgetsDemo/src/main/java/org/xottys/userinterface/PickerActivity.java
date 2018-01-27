/**
 * 本例演示的Picker类型的视图，主要用于形象和快速输入时间和少量数字(文本)
 * 1)DatePicker
 * 2)TimePicker
 * 3)CalendarView
 * 4)NumberPicker
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class PickerActivity extends Activity {
    TextView tv;
    // 定义5个记录当前时间的变量
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        tv = (TextView) findViewById(R.id.tv);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        CalendarView cv = (CalendarView) findViewById(R.id.calendarView);
        NumberPicker np1 = (NumberPicker) findViewById(R.id.np1);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.np2);

        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        //DatePicker-----------------------------------------------------------------------------
        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day) {
                PickerActivity.this.year = year;
                PickerActivity.this.month = month;
                PickerActivity.this.day = day;
                // 显示当前日期、时间
                showMessage
                        ("您的选择的日期为：" + year + "年"
                                + (month + 1) + "月" + day + "日  ");
            }
        });
        //设置星期的第一天，缺省是周日
        datePicker.setFirstDayOfWeek(Calendar.MONDAY);
        //TimePicker-----------------------------------------------------------------------------
        timePicker.setEnabled(true);
        //缺省是12小时显示
        timePicker.setIs24HourView(true);
        // 为TimePicker指定监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view
                    , int hourOfDay, int minute) {
                PickerActivity.this.hour = hourOfDay;
                PickerActivity.this.minute = minute;
                // 显示当前日期、时间
                showMessage("您的选择的时间为：" + hour + "时" + minute + "分");
            }
        });

        //CalendarView-----------------------------------------------------------------------------
        cv.setFirstDayOfWeek(Calendar.SUNDAY);
        // 为CalendarView组件的日期改变事件添加事件监听器
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year,
                                            int month, int dayOfMonth) {
                // 使用Toast显示用户选择的日期
                showMessage("你选择的是" + year + "年" + (month + 1) + "月"
                        + dayOfMonth + "日");
            }
        });

        //NumberPicker-----------------------------------------------------------------------------
        // 设置np1的最小值和最大值
        np1.setMinValue(10);
        np1.setMaxValue(50);
        // 设置np1的当前值
        np1.setValue(20);

        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            // 当NumberPicker的值发生改变时，将会激发该方法
            @Override
            public void onValueChange(NumberPicker picker,
                                      int oldVal, int newVal) {
                showMessage("你选择的是:" + newVal);
            }
        });

        final String[] city = {"北京", "上海", "广州", "深圳", "成都", "天津"};
        //设置显示的文字
        np2.setDisplayedValues(city);
        //设置是否循环显示，缺省是循环显示
        np2.setWrapSelectorWheel(false);
        np2.setMinValue(0);
        np2.setMaxValue(city.length - 1);
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            // 当NumberPicker的值发生改变时，将会激发该方法
            @Override
            public void onValueChange(NumberPicker picker,
                                      int oldVal, int newVal) {
                showMessage("你选择的是:" + city[newVal]);
            }
        });
    }

    private void showMessage(String msg) {
        tv.setText(msg);
    }
}
