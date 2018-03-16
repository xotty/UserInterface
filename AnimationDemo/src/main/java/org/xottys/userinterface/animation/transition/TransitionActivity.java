/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xottys.userinterface.animation.transition;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class TransitionActivity extends AppCompatActivity {

    private Intent intent;
    View squareBlue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        Fade enterTransition = new Fade();
        enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setEnterTransition(enterTransition);
        squareBlue = findViewById(R.id.imgicon);
        ((ImageView)squareBlue).setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

    }

    public void onClick(View v) {
        intent = new Intent(this, TransitionDetailsActivity.class);

        switch (v.getId()) {
            case R.id.explode_code:
                intent.putExtra("flag", 0);
                break;
            case R.id.explode_xml:
                intent.putExtra("flag", 1);
                break;
            case R.id.slide_code:
                intent.putExtra("flag", 2);
                break;
            case R.id.slide_xml:
                intent.putExtra("flag", 3);
                break;
            case R.id.fade_code:
                intent.putExtra("flag", 4);
                break;
            case R.id.fade_xml:
                intent.putExtra("flag", 5);
                break;
            case R.id.share_activity:
                intent.putExtra("flag", 6);
                View cat = findViewById(R.id.img);
                View title = findViewById(R.id.title_name);
                //创建单个共享元素
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view, "share").toBundle());
                //创建多个共享单元
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create(v, "share"),
                        Pair.create(cat, "cat"),
                        Pair.create(title, "title_name"))
                        .toBundle());
                break;
            case R.id.share_fragment:
                intent = new Intent(this, SharedElementActivity.class);

//                final View squareBlue = findViewById(R.id.imgicon);
                final View tname = findViewById(R.id.share_fragment);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create(tname, "title"),
                        Pair.create(squareBlue, "square_blue"))
                        .toBundle());
//                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

                break;
            case R.id.exit:
                Transition returnTransition = new Slide(Gravity.BOTTOM);
                returnTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
                getWindow().setReturnTransition(returnTransition);
                finishAfterTransition();
                break;
        }
       if  (v.getId()!=R.id.share_activity && v.getId()!=R.id.exit &&v.getId()!=R.id.share_fragment )
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}
