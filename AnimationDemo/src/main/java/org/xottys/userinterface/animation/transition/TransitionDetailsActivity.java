/*
 * Copyright (C) 2014 The Android Open Source Project
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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;


import org.xottys.userinterface.animation.R;


public class TransitionDetailsActivity extends Activity {
    Transition transition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitiondetail);
        int flag = getIntent().getExtras().getInt("flag");
        switch (flag) {
            case 0:
                transition=new Explode();
                transition.setDuration(500);
                break;
            case 1:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.t_explode);
                break;
            case 2:
                transition=new Slide();
                ((Slide)transition).setSlideEdge(Gravity.END);
                transition.setDuration(500);
                break;
            case 3:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
                break;
            case 4:
                transition=new Fade();
                transition.setDuration(500);
            case 5:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.t_fade);
                break;

        }
        getWindow().setEnterTransition(transition);

    }
}
