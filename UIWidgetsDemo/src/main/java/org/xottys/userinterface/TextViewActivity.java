/**本例演示了TextView即其子类的基本用法
 * 1)LinearLayout
 * 2)RelativeLayout
 * 3)TableLayout
 * 4)GridLayout
 * 5)FrameLayout
 * 6)AbsoluteLayout
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static org.xottys.userinterface.R.id.textView;

public class TextViewActivity extends Activity {
    //存放单选CheckedTextView的id
    int[] singleCheckedTextViewId = new int[3];

    //存放计时器的秒数
    int countSecond=0;

    //多选checkedTextView点击方法，将现有状态反转
    View.OnClickListener multiListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckedTextView checkedTextView = (CheckedTextView) findViewById(v.getId());
            checkedTextView.toggle();    // 反转状态
        }
    };

    //单选checkedTextView点击方法，每次点击都要将全部checkedTextView一一处理
    View.OnClickListener singleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < singleCheckedTextViewId.length; i++) {
                if (singleCheckedTextViewId[i] != v.getId()) {
                    ((CheckedTextView) findViewById(singleCheckedTextViewId[i])).setChecked(false);
                } else {
                    ((CheckedTextView) findViewById(singleCheckedTextViewId[i])).setChecked(true);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);
        Button disabledButton = (Button) findViewById(R.id.button_disabled);
        disabledButton.setEnabled(false);

        Switch mySwitch = (Switch) findViewById(R.id.mySwitch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showMessage("电视机点击了：'开'");
                } else {
                    showMessage("电视机点击了：'关'");
                }
            }
        });

        ToggleButton myToggleButton = (ToggleButton) findViewById(R.id.myToggle);
        myToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showMessage("ToggleButton点击了：'打开'");
                } else {
                    showMessage("ToggleButton点击了：'关闭'");
                }
            }
        });

        TextView tv = (TextView) findViewById(textView);
        tv.setSelected(true);

        //多选checkedTextView
        CheckedTextView checkedTextView1 = (CheckedTextView) findViewById(R.id.check_textview1);
        checkedTextView1.setOnClickListener(multiListener);
        CheckedTextView checkedTextView2 = (CheckedTextView) findViewById(R.id.check_textview2);
        checkedTextView2.setOnClickListener(multiListener);
        CheckedTextView checkedTextView3 = (CheckedTextView) findViewById(R.id.check_textview3);
        checkedTextView3.setOnClickListener(multiListener);

        //单选checkedTextView，将id加入数组
        CheckedTextView checkedTextView4 = (CheckedTextView) findViewById(R.id.check_textview4);
        singleCheckedTextViewId[0] = checkedTextView4.getId();
        checkedTextView4.setOnClickListener(singleListener);
        CheckedTextView checkedTextView5 = (CheckedTextView) findViewById(R.id.check_textview5);
        singleCheckedTextViewId[1] = checkedTextView5.getId();
        checkedTextView5.setOnClickListener(singleListener);
        CheckedTextView checkedTextView6 = (CheckedTextView) findViewById(R.id.check_textview6);
        singleCheckedTextViewId[2] = checkedTextView6.getId();
        checkedTextView6.setOnClickListener(singleListener);

        final Chronometer mChronometer1 = (Chronometer) findViewById(R.id.chronometer1);
        final Chronometer mChronometer2 = (Chronometer) findViewById(R.id.chronometer2);
        final Chronometer mChronometer3 = (Chronometer) findViewById(R.id.chronometer3);

        //设置为倒计时器
        mChronometer3.setCountDown(true);

        //每1秒回调一次本方法
        mChronometer2.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //处理小时的显示
                int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60 / 60);
                chronometer.setFormat("计时器:"+(hour<10?"0":"")+String.valueOf(hour)+":%s");

                /*只显示秒则用下列方法
                countSecond++;

                chronometer.setFormat(""+countSecond);
                或者
                chronometer.setText(""+countSecond);*/
            }
        });

        //启动计时器
        ((Button) findViewById(R.id.start)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mChronometer1.start();
                countSecond=0;
                mChronometer2.start();
                mChronometer3.start();
            }
        });

        //计时器清零，回到初始计时基准时间
        ((Button) findViewById(R.id.reset)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //设置基准时间为当前时间
                mChronometer1.setBase(SystemClock.elapsedRealtime());

                //设置基准时间为当前时间，秒数计数清零
                mChronometer2.setBase(SystemClock.elapsedRealtime());
                countSecond=0;

                //设置10分钟倒计时，基准时间(base)+10分钟
                mChronometer3.setBase(SystemClock.elapsedRealtime()+10*60*1000);

            }
        });

        //暂停计时器
        ((Button) findViewById(R.id.stop)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mChronometer1.stop();
                mChronometer2.stop();
                mChronometer3.stop();
            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        AutoCompleteTextView textView1 = (AutoCompleteTextView) findViewById(R.id.autoedit);
        textView1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                R.layout.autocompletetvpop, COUNTRIES);

        MultiAutoCompleteTextView textView2 = (MultiAutoCompleteTextView) findViewById(R.id.multautoedit);
        textView2.setAdapter(adapter2);
        textView2.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    static final String[] COUNTRIES = new String[] {
            "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
            "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
            "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
            "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
            "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
            "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
            "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
            "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde",
            "Cayman Islands", "Central African Republic", "Chad", "Chile", "China",
            "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo",
            "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic",
            "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
            "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
            "Estonia", "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji", "Finland",
            "Former Yugoslav Republic of Macedonia", "France", "French Guiana", "French Polynesia",
            "French Southern Territories", "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar",
            "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau",
            "Guyana", "Haiti", "Heard Island and McDonald Islands", "Honduras", "Hong Kong", "Hungary",
            "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
            "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
            "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
            "Macau", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
            "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova",
            "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
            "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand",
            "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Marianas",
            "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru",
            "Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
            "Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe", "Saint Helena",
            "Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon",
            "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Saudi Arabia", "Senegal",
            "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
            "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Korea",
            "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden",
            "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "The Bahamas",
            "The Gambia", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
            "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda",
            "Ukraine", "United Arab Emirates", "United Kingdom",
            "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan",
            "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara",
            "Yemen", "Yugoslavia", "Zambia", "Zimbabwe","中国","中非"
    };
    private void showMessage(final String msg) {
        Toast.makeText(TextViewActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}
