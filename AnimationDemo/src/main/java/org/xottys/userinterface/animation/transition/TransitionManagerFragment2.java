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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.xottys.userinterface.animation.R;

/**
 * This application demonstrates some of the capabilities and uses of the
 * {@link android.transition transitions} APIs. Scenes and a TransitionManager
 * are loaded from resource files and transitions are run between those scenes
 * as well as a dynamically-configured scene.
 */
public class TransitionManagerFragment2 extends Fragment {

    Scene mScene1, mScene2, mScene3;
    ViewGroup mSceneRoot;
    TransitionManager mTransitionManager;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_transitionmanager2, null);

        mSceneRoot =  view.findViewById(R.id.sceneRoot);

        TransitionInflater transitionInflater= TransitionInflater.from(getContext());

        // Note that this is not the only way to create a Scene object, but that
        // loading them from layout resources cooperates with the
        // TransitionManager that we are also loading from resources, and which
        // uses the same layout resource files to determine the scenes to transition
        // from/to.
        mScene1 = Scene.getSceneForLayout(mSceneRoot, R.layout.transition_manager_scene1, getContext());
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.transition_manager_scene2, getContext());
        mScene3 = Scene.getSceneForLayout(mSceneRoot, R.layout.transition_manager_scene3, getContext());
        mTransitionManager = transitionInflater.inflateTransitionManager(R.transition.transitions_mgr,
                mSceneRoot);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RadioGroup radiogroup =  view.findViewById(R.id.scenes);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.scene1:
                mTransitionManager.transitionTo(mScene1);
                break;
            case R.id.scene2:
                mTransitionManager.setTransition(mScene2,new Explode());
                mTransitionManager.transitionTo(mScene2);
                break;
            case R.id.scene3:
                mTransitionManager.setTransition(mScene3,new Fade());
                mTransitionManager.transitionTo(mScene3);
                break;
            case R.id.scene4:
                // scene4 is not an actual 'Scene', but rather a dynamic change in the UI,
                // transitioned to using beginDelayedTransition() to tell the TransitionManager
                // to get ready to run a transition at the next frame
                mTransitionManager.setTransition(mScene2,mScene3,new Slide());
                TransitionManager.beginDelayedTransition(mSceneRoot);
                setNewSize(R.id.view1, 150, 25);
                setNewSize(R.id.view2, 150, 25);
                setNewSize(R.id.view3, 150, 25);
                setNewSize(R.id.view4, 150, 25);
                break;
        }

            }
        });
    }

    private void setNewSize(int id, int width, int height) {
        View v = view.findViewById(id);
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = width;
        params.height = height;
        v.setLayoutParams(params);
    }
}
