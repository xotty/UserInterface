/**本例演示了TextView及其子类的基本用法
 * 1)Button、ToggleButton
 * 2)EditText
 * 3)CheckBox
 * 4)RadioButton
 * 5)TextView
 * 6)AnalogClock/TextClock
 * 7)Chronometer
 * 8)AutoCompleteTextView/MultiAutoCompleteTextView
 * 9)EmojiTextView/EmojiEditText/EmojiButton，具体用法如下：
 * a)在xml中用<EmojiTextView>或<EmojiAppCompatTextView>标签定义
 * b)初始化 EmojiCompat:EmojiCompat.init(config)
 * c)显示和读写Emoji
 *
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutDemo
 * <br/>Date:Sept，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface;

import android.content.Context;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.widget.EmojiAppCompatEditText;
import android.support.text.emoji.widget.EmojiAppCompatTextView;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.ref.WeakReference;

import static android.text.Html.FROM_HTML_MODE_LEGACY;
import static android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT;
import static org.xottys.userinterface.R.string.styled_12_hour_clock2;
import static org.xottys.userinterface.R.string.styled_24_hour_clock2;

public class TextViewActivity extends AppCompatActivity {
    /** Change this to {@code false} when you want to use the downloadable Emoji font. */
    private static final boolean USE_BUNDLED_EMOJI = true;

    // [U+1F469] (WOMAN) + [U+200D] (ZERO WIDTH JOINER) + [U+1F4BB] (PERSONAL COMPUTER)
    private static final String WOMAN_TECHNOLOGIST = "\uD83D\uDC69\u200D\uD83D\uDCBB";

    // [U+1F469] (WOMAN) + [U+200D] (ZERO WIDTH JOINER) + [U+1F3A4] (MICROPHONE)
    private static final String WOMAN_SINGER = "\uD83D\uDC69\u200D\uD83C\uDFA4";

    private  static String EMOJI;

    //供AutoCompleteTextView使用的国家名称
    static final String[] COUNTRIES = new String[]{
            "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
            "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
            "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
            "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
            "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
            "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory",
            "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
            "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde",
            "Cayman Islands", "Central African Republic", "Cemtral African Republic of ", "Chad", "Chile", "China",
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
            "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "中国", "中非"
    };
    private static final String TAG = "TextViewActivity";
    //存放单选CheckedTextView的id
    int[] singleCheckedTextViewId = new int[3];
    //存放计时器的秒数
    int countSecond=0;
    //多选checkedTextView点击方法，将现有图标状态反转
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

        //从左到右依次是正常Button、小号不可点击Button、带图片带背景样式Button和圆角矩形Button
        Button iconButton = (Button) findViewById(R.id.iconButton);
        iconButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                showMessage("你击了：iconButton");
            }
        });

        //右边的自定义样式Switch改变了文字、滑道和滑块样式
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

        //右边的自定义样式ToggleButton改变了文字、背景样式和点击时改变颜色
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



        //第一行从左到右依次是简单密码EditText、电话号码EditText、带边框EditTex，其中主要是改变的背景、加了输入限制，更改键盘样式等
        //第二行是完全自定义EditText，增加右侧删除按钮，增加为空时摇晃动画
        final EditText edittext1=(EditText)findViewById(R.id.edit1);

        //将背景LinearLayout点击事件设为剥夺edittext1的焦点，以便关闭软键盘
        LinearLayout bg=(LinearLayout)findViewById(R.id.bg);
        bg.setOnClickListener(new View.OnClickListener() {//给背景一个id，并对它的点击事件进行监听
            @Override
            public void onClick(View v) {
                //点击背景的时候，让这个editText失去焦点
                edittext1.setFocusable(false);
                Log.i(TAG, "LinearLayout Background Clicked!");
            }
        });

        //点击edittext1时，使其立即获取焦点
        edittext1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //EditText 立即获得焦点
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                Log.i(TAG, "edittext1 Clicked！");
            }
        });

        //edittext1焦点改变事件处理，在失去焦点时关闭软键盘
        edittext1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                if(hasFocus) {
                    //弹出显示软键盘
                    imm.showSoftInput(v, SHOW_IMPLICIT);

                    showMessage("EditText获得焦点");
                    Log.i(TAG, "edittext1焦点改变：获得检点");
                } else {
                    //关闭软键盘
                   // InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    boolean isOpen = imm.isActive();
                    if (isOpen) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        showMessage("EditText失去焦点");
                        Log.i(TAG, "edittext1焦点改变：失去检点");
                    }
                }
            }
        });

        //这个事件可以用来校验输入的数据格式和长度
        edittext1.addTextChangedListener(new TextWatcher() {
            @Override
            //text  输入框中改变后的字符串信息
            //start 输入框中改变后的字符串的起始位置
            //before 输入框中改变前的字符串的位置 默认为0
            //count 输入框中改变后的一共输入字符串的数量
            public void onTextChanged(CharSequence text, int start, int before, int count) {

                Log.i(TAG, "onTextChanged:EditText正在改变");
            }

            @Override
            //text  输入框中改变前的字符串信息
            //start 输入框中改变前的字符串的起始位置
            //count 输入框中改变前后的字符串改变数量一般为0
            //after 输入框中改变后的字符串与起始位置的偏移量
            public void beforeTextChanged(CharSequence text, int start, int count,int after) {
                Log.i(TAG, "beforeTextChanged:EditText改变前");
            }

            @Override
            //edit  输入结束呈现在输入框中的信息
            public void afterTextChanged(Editable edit) {
                Log.i(TAG, "afterTextChanged:EditText改变后");
            }
       });

        //从上到下依次是普通CheckBox、星形CheckBox、自定义样式CheckBox
        CheckBox checkBox = (CheckBox) findViewById(R.id.check3);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked)
                   showMessage("选择了 checkBox3");
                else
                   showMessage("取消了 checkBox3");
            }
        });

        //从上到下依次是普通RadioButton、改变了位置和大小的RadioButton、自定义样式RadioButton
        //利用其多选一的特性及其图片可以放在文字的上面或下面，可以用它来实现Tab功能
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //根据checkedId获取选中项RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(checkedId);
                //显示选中项RadioButton的的文本
                showMessage("当前选择了：" + rb.getText());
            }
        });
        TextView text = (TextView) findViewById(R.id.text);

        //在TextView上下左右设置图片
        Drawable drawable = getResources().getDrawable(R.drawable.ic_favorite_black_24dp, null);
        drawable.setBounds(0, 0, 50, 50);   //必须设置图片大小，否则不显示
        text.setCompoundDrawables(null, null, drawable, null);

        //左侧除最下面的test3外，皆为普通TextView，属性主要是字体大小、颜色和背景
        //跑马灯效果也可以在这个点击事件里强制获取焦点而呈现
        final TextView textView_marquee=(TextView)findViewById(R.id.textView_marquee);
        textView_marquee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setSelected(true);
                Log.i(TAG, "onClick: marquee");
            }
        });

        //test1～test4是4种不同的链接文本实现方式
        //1)设置自动识别属性，其中的网址、电话、email等会自动标识并可点击跳转
        //2)在文本中用<a>链接文本</a>，其中tel：为电话，http://为网址
        TextView t2 = (TextView) findViewById(R.id.text2);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        //3)在程序中用代码实现，将2）中格式的文本用Html.fromHtml解析后使用
        TextView t3 = (TextView) findViewById(R.id.text3);
        t3.setText(
                Html.fromHtml(
                        "<b>text3: Constructed from HTML programmatically.</b>  Text with a " +
                                "<a href=\"http://www.google.com\">link</a> " +
                                "created in the Java source code using HTML.",FROM_HTML_MODE_LEGACY));
        t3.setMovementMethod(LinkMovementMethod.getInstance());

        //4)使用SpannableString构造链接文本
        SpannableString ss = new SpannableString(
                "text4: Manually created spans. Click here to dial the phone.");
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, 30,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new URLSpan("tel:4155551212"), 31+6, 31+10,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView t4 = (TextView) findViewById(R.id.text4);
        t4.setText(ss);
        t4.setMovementMethod(LinkMovementMethod.getInstance());

        //checkedTextView的点选图标切换需要在OnClickListener中手动实现，系统没有提供现成的
        //左侧为多选checkedTextView，从上到下依次为标准、选择框在右侧和自定义选择框样式
        CheckedTextView checkedTextView1 = (CheckedTextView) findViewById(R.id.check_textview1);
        checkedTextView1.setOnClickListener(multiListener);
        CheckedTextView checkedTextView2 = (CheckedTextView) findViewById(R.id.check_textview2);
        checkedTextView2.setOnClickListener(multiListener);
        CheckedTextView checkedTextView3 = (CheckedTextView) findViewById(R.id.check_textview3);
        checkedTextView3.setOnClickListener(multiListener);

        //右侧为单选checkedTextView，将id加入数组，从上到下依次为标准、选择框在左侧和自定义选择框样式
        CheckedTextView checkedTextView4 = (CheckedTextView) findViewById(R.id.check_textview4);
        singleCheckedTextViewId[0] = checkedTextView4.getId();
        checkedTextView4.setOnClickListener(singleListener);
        CheckedTextView checkedTextView5 = (CheckedTextView) findViewById(R.id.check_textview5);
        singleCheckedTextViewId[1] = checkedTextView5.getId();
        checkedTextView5.setOnClickListener(singleListener);
        CheckedTextView checkedTextView6 = (CheckedTextView) findViewById(R.id.check_textview6);
        singleCheckedTextViewId[2] = checkedTextView6.getId();
        checkedTextView6.setOnClickListener(singleListener);

        //显示时间的两种方式：模拟钟表方式和文本方式，前者可以自定义表盘和指针，但没有秒针
        //后者可以显示年月日时分秒、星期、特定时区等。字体、颜色、样式均可以在布局文件或代码中自由设定
        TextClock textClock=(TextClock)findViewById(R.id.textclock);
        textClock.setFormat12Hour(getString(styled_12_hour_clock2));
        textClock.setFormat24Hour(getString(styled_24_hour_clock2));

        //计时器，从左到右依次为标准样式、自定义样式和自定义样式倒计时
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
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mChronometer1.start();
                countSecond=0;
                mChronometer2.start();
                mChronometer3.start();
            }
        });

        //暂停计时器
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mChronometer1.stop();
                mChronometer2.stop();
                mChronometer3.stop();
            }
        });

        //计时器清零，回到初始计时基准时间
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
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

        //单选自动搜索和填充TextView，设置标准Adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView textView1 = (AutoCompleteTextView) findViewById(R.id.autoedit);
        textView1.setAdapter(adapter1);

        //多选自动搜索和填充TextView，设置自定义Adapter,设定弹出框的样式和其中文本的样式
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                R.layout.autocompletetvpop, COUNTRIES);
        MultiAutoCompleteTextView textView2 = (MultiAutoCompleteTextView) findViewById(R.id.multautoedit);
        textView2.setAdapter(adapter2);
        textView2.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //Emoji字符显示和读写
        EMOJI = WOMAN_TECHNOLOGIST + " " + WOMAN_SINGER;
        // TextView variant provided by Emoji library
        final TextView emojiTextView = findViewById(R.id.emoji_text_view);
        // final EmojiTextView emojiTextView = findViewById(R.id.emoji_text_view);

        emojiTextView.setText(getString(R.string.emoji_text_view, EMOJI));

        // EditText variant provided by EmojiCompat library
        final TextView emojiEditText= findViewById(R.id.emoji_edit_text);
        //final EmojiAppCompatEditText emojiEditText = findViewById(R.id.emoji_edit_text);

        emojiEditText.setText(getString(R.string.emoji_edit_text, EMOJI));

        // Button variant provided by EmojiCompat library
        final TextView emojiButton = findViewById(R.id.emoji_button);
        emojiButton.setText(getString(R.string.emoji_button, EMOJI));

        // Regular TextView without EmojiCompat support; you have to manually process the text
        final TextView regularTextView = findViewById(R.id.regular_text_view);
        //将Emoji的unicode转为字符串
        EMOJI=String.valueOf(Character.toChars(Integer.parseInt("1F92A", 16)))
        +String.valueOf(Character.toChars(Integer.parseInt("1F9E5", 16)));

        EmojiCompat.get().registerInitCallback(new InitCallback(regularTextView));
        //regularTextView.setText(getString(R.string.regular_text_view, EMOJI));

        final TextView customTextView = findViewById(R.id.emoji_custom_text_view);
        customTextView.setText(getString(R.string.custom_text_view, EMOJI));

    }

    private void showMessage(final String msg) {
        Toast.makeText(TextViewActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    private static class InitCallback extends EmojiCompat.InitCallback {
        private final WeakReference<TextView> mRegularTextViewRef;

        InitCallback(TextView regularTextView) {
            mRegularTextViewRef = new WeakReference<>(regularTextView);
        }

        @Override
        public void onInitialized() {
            final TextView regularTextView = mRegularTextViewRef.get();
            if (regularTextView != null) {
                final EmojiCompat compat = EmojiCompat.get();
                final Context context = regularTextView.getContext();
                regularTextView.setText(
                        compat.process(context.getString(R.string.regular_text_view, EMOJI)));
            }
        }

    }
}
