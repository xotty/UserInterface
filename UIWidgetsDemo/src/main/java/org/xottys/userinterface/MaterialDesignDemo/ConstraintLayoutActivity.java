/**
 * 本例演示了AS2.3以后引入的一种全新的布局方式ConstraintLayout的下列主要特性：
 * 1)基本位置约束（线性、环形、文字基线）
 * 2)基本尺寸约束（Fixed、WRAP_CONTENT 、MATCH_CONSTRAINT）
 * 3)Margin、Barrier、Group
 * 4)Guidline位置约束
 * 5)ChainStyle位置约束
 * 6)Placeholder模版
 * 可以看成是一种升级版的RelativeLayout，主要是通过可视化工具拖拽各种控件来完成布局。
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:MaterialDesignDemo
 * <br/>Date:Oct，2017～Jan，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */

package org.xottys.userinterface.MaterialDesignDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xottys.userinterface.R;

public class ConstraintLayoutActivity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout constraintLayout;
    Button btn, aBtn, bBtn, cBtn, dBtn, eBtn, fBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraintlayout_main);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_main);
        aBtn = (Button) findViewById(R.id.buttonBasicArrange);
        bBtn = (Button) findViewById(R.id.buttonBasicSize);
        cBtn = (Button) findViewById(R.id.buttonAdvancedArrange);
        dBtn = (Button) findViewById(R.id.buttonGuideline);
        eBtn = (Button) findViewById(R.id.buttonChainStyle);
        fBtn = (Button) findViewById(R.id.buttonPlaceholder);
        View.OnFocusChangeListener theListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.performClick();
                }
            }
        };
        aBtn.setOnFocusChangeListener(theListener);
        bBtn.setOnFocusChangeListener(theListener);
        cBtn.setOnFocusChangeListener(theListener);
        dBtn.setOnFocusChangeListener(theListener);
        eBtn.setOnFocusChangeListener(theListener);
        fBtn.setOnFocusChangeListener(theListener);
    }

    @Override
    public void onClick(View button) {
        constraintLayout.removeAllViews();
        View view = null;
        if (!button.hasFocus()) {
            button.setFocusableInTouchMode(true);
            button.requestFocusFromTouch();
        }
        switch (button.getId()) {
            case R.id.buttonBasicArrange:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_basic_arrange, constraintLayout);
                break;
            case R.id.buttonBasicSize:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_basic_size, constraintLayout);
                break;
            case R.id.buttonAdvancedArrange:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_advanced_arrange, constraintLayout);
                btn = (Button) view.findViewById(R.id.btn_visibleswitch);
                btn.setTag(1);
                TextView textView = (TextView) view.findViewById(R.id.textView8);
                textView.setText(getResources().getString(R.string.singapore_description).substring(0, 250));
                final Button btn1 = (Button) view.findViewById(R.id.button1);
                final Group group = (Group) view.findViewById(R.id.group);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View source) {
                        switch ((int) (btn.getTag())) {
                            case 0:
                                btn1.setVisibility(View.VISIBLE);
                                group.setVisibility(View.VISIBLE);
                                btn.setText("Visible-->Invisible");
                                btn.setTag(1);
                                break;
                            case 1:
                                btn1.setVisibility(View.INVISIBLE);
                                group.setVisibility(View.INVISIBLE);
                                btn.setText("Invisible-->Gone");
                                btn.setTag(2);
                                break;
                            case 2:
                                btn1.setVisibility(View.GONE);
                                group.setVisibility(View.GONE);
                                btn.setText("Gone-->Visible");
                                btn.setTag(0);
                                break;
                        }
                    }
                });
                break;
            case R.id.buttonGuideline:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_guideline, constraintLayout);
                break;
            case R.id.buttonChainStyle:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_chainstyle, constraintLayout);
                break;
            case R.id.buttonPlaceholder:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_placeholder_content, constraintLayout);
                break;
        }
        view.setBackgroundColor(Color.LTGRAY);
    }
}
