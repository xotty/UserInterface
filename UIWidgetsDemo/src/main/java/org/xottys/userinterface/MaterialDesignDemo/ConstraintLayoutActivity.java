// Copyright 2016 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.xottys.userinterface.MaterialDesignDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
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
//        button.setFocusable(true);
//        button.requestFocus();
            button.setFocusableInTouchMode(true);
            button.requestFocusFromTouch();
        }
        switch (button.getId()) {
            case R.id.buttonBasicArrange:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_basic_arrange, null);
                break;
            case R.id.buttonBasicSize:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_basic_size, null);
                break;
            case R.id.buttonAdvancedArrange:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_advanced_arrange, null);
                btn = view.findViewById(R.id.btn_visibleswitch);
                btn.setTag(1);
                TextView textView = view.findViewById(R.id.textView8);
                textView.setText(getResources().getString(R.string.singapore_description).substring(0, 250));
                final Button btn1 = view.findViewById(R.id.button1);
                final Group group = view.findViewById(R.id.group);
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
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_guideline, null);
                break;
            case R.id.buttonChainStyle:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_chainstyle, null);
                break;
            case R.id.buttonPlaceholder:
                view = getLayoutInflater().inflate(R.layout.activity_constraintlayout_placeholder_content, null);
                break;

        }
        view.setBackgroundColor(Color.LTGRAY);
        constraintLayout.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

}
