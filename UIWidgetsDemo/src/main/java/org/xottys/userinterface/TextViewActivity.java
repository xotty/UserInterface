package org.xottys.userinterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TextViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);
        Button disabledButton = (Button) findViewById(R.id.button_disabled);
        disabledButton.setEnabled(false);

        Switch mySwitch=(Switch) findViewById(R.id.mySwitch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    showMessage("电视机点击了：'开'");
                } else {
                    showMessage("电视机点击了：'关'");
                }
            }
        });

        ToggleButton myToggleButton=(ToggleButton) findViewById(R.id.myToggle);
        myToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    showMessage("ToggleButton点击了：'打开'");
                } else {
                    showMessage("ToggleButton点击了：'关闭'");
                }
            }
        });

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setSelected(true);



        final CheckedTextView checkedTextView=(CheckedTextView)findViewById(R.id.check_textview1);

        //点击状态后变更相反，即下三角转化为上三角符号
        checkedTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                checkedTextView.toggle();
            }

        });
    }


    private void showMessage(final String msg) {
        Toast.makeText(TextViewActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}
