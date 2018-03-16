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

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * This application demonstrates some of the capabilities and uses of the
 * {@link android.transition transitions} APIs. Scenes and a TransitionManager
 * are loaded from resource files and transitions are run between those scenes
 * as well as a dynamically-configured scene.
 */
public class TransitionManagerFragment1 extends Fragment implements View.OnClickListener {

    ViewGroup mSceneRoot;
    View view;
    protected Scene scene1;
    protected Scene scene2;
    protected boolean isScene2[];

    private CircleImageView cuteboy,cutegirl,hxy,lly;
    private boolean isImageBigger;
    private int primarySize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_transitionmanager1, null);
      //  mSceneRoot =  view.findViewById(R.id.sceneRoot);
        isScene2=new boolean[5];
        initScene(R.id.change_bounds,R.layout.scene_1_changebounds,R.layout.scene_2_changebounds,true,0);

        initScene(R.id.change_transform,R.layout.scene_1_changetransform,R.layout.scene_2_changetransform,true,1);
        initScene(R.id.change_image_transform,R.layout.scene_1_changeimagetransform,R.layout.scene_2_changeimagetransform,true,2);

        initScene(R.id.change_clip_bounds,R.layout.scene_1_changeclipbounds,R.layout.scene_2_changeclipbounds,true,3);
        initScene(R.id.begin_delayed_transition,R.layout.scene_begindelayedtransition,R.layout.scene_begindelayedtransition,true,4);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        view.findViewById(R.id.btn_changebounds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                initScene(R.id.change_bounds,R.layout.scene_1_changebounds,R.layout.scene_2_changebounds,false,0);
                switchScene(new ChangeBounds(),0);
            }
        });

        view.findViewById(R.id.btn_changetransform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                initScene(R.id.change_transform,R.layout.scene_1_changetransform,R.layout.scene_2_changetransform,false,1);
                switchScene(new ChangeTransform(),1);
            }
        });

        view.findViewById(R.id.btn_changeimagetransform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                initScene(R.id.change_image_transform,R.layout.scene_1_changeimagetransform,R.layout.scene_2_changeimagetransform,false,2);
                switchScene(new ChangeImageTransform(),2);
            }
        });

        view.findViewById(R.id.btn_changeclipbounds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                initScene(R.id.change_clip_bounds,R.layout.scene_1_changeclipbounds,R.layout.scene_2_changeclipbounds,false,3);
                switchScene(new ChangeClipBounds(),3);
            }
        });

        mSceneRoot = (ViewGroup) view.findViewById(R.id.scene_root);
        cuteboy= (CircleImageView) view.findViewById(R.id.xcuteboy);
        cutegirl= (CircleImageView) view.findViewById(R.id.xcutegirl);
        hxy= (CircleImageView) view.findViewById(R.id.hxy);
        lly= (CircleImageView) view.findViewById(R.id.lly);
        primarySize=cuteboy.getLayoutParams().width;
        cuteboy.setOnClickListener(this);
        cutegirl.setOnClickListener(this);
        hxy.setOnClickListener(this);
        lly.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //start scene 是当前的scene
        TransitionManager.beginDelayedTransition(mSceneRoot, TransitionInflater.from(getContext()).inflateTransition(R.transition.explode_and_changebounds));
        //next scene 此时通过代码已改变了scene statue
        changeScene(v);
    }

    protected void initScene(@IdRes int rootView, @LayoutRes int scene1_layout, @LayoutRes int scene2_layout,boolean isInitial,int group) {
        ViewGroup sceneRoot=  view.findViewById(rootView);
        if (group!=3) {
            scene1 = Scene.getSceneForLayout(sceneRoot, scene1_layout, getContext());
            scene2 = Scene.getSceneForLayout(sceneRoot, scene2_layout, getContext());
        }else {
            View inflate1 = LayoutInflater.from(getContext()).inflate(R.layout.scene_1_changeclipbounds, null);
            View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.scene_2_changeclipbounds, null);


            ImageView imageView = (ImageView) inflate1.findViewById(R.id.cutegirl);
            ImageView imageView2 = (ImageView) inflate2.findViewById(R.id.cutegirl);

            imageView2.setClipBounds(new Rect(200, 200, 400, 400));
            scene1=new Scene(sceneRoot,inflate1);
            scene2=new Scene(sceneRoot,inflate2);

        }
        if (isInitial)
            TransitionManager.go(scene1);
    }


    protected void switchScene(Transition transition,int group){
        TransitionManager.go(isScene2[group]?scene1:scene2,transition);
        isScene2[group]=!isScene2[group];
    }


    private void changeScene(View view) {
        changeSize(view);
        changeVisibility(cuteboy,cutegirl,hxy,lly);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * view的宽高1.5倍和原尺寸大小切换
     * 配合ChangeBounds实现缩放效果
     * @param view
     */
    private void changeSize(View view) {
        isImageBigger=!isImageBigger;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(isImageBigger){
            layoutParams.width=(int)(1.5*primarySize);
            layoutParams.height=(int)(1.5*primarySize);
        }else {
            layoutParams.width=primarySize;
            layoutParams.height=primarySize;
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * VISIBLE和INVISIBLE状态切换
     * @param views
     */
    private void changeVisibility(View ...views){
        for (View view:views){
            view.setVisibility(view.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
        }
    }
}
