/**
 * 本例演示场景动画TransitionManager的用法之一：
 * 1）定义初始场景Scene1和结束场景Scene2
 *   Scene.getSceneForLayout(sceneRoot, R.layout.scene, context)
 *或 new Scene(sceneRoot, findViewById(R.id.scene))
 * 2)动画转换场景
 *  TransitionManager.go(toScene, transition)
 *或TransitionManager.beginDelayedTransition(sceneRoot, transition）
 *
 * <p>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.transition;

import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
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


public class TransitionManagerFragment1 extends Fragment implements View.OnClickListener {

    private ViewGroup mSceneRoot;
    private View view;
    protected Scene scene1,scene2;
    protected Scene Ascene,Bscene;
    protected boolean isScene2[];

    private CircleImageView cuteboy,cutegirl,hxy,lly;
    private boolean isImageBigger;
    private int primarySize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_transitionmanager1, null);
        //记录当前状态是否是Scene2
        isScene2=new boolean[5];

        //初始化场景，这是Scene1
        initScene(R.id.change_bounds,R.layout.scene_1_changebounds,R.layout.scene_2_changebounds,true,0);
        initScene(R.id.change_transform,R.layout.scene_1_changetransform,R.layout.scene_2_changetransform,true,1);
        initScene(R.id.change_image_transform,R.layout.scene_1_changeimagetransform,R.layout.scene_2_changeimagetransform,true,2);
        initScene(R.id.change_clip_bounds,R.layout.scene_1_changeclipbounds,R.layout.scene_2_changeclipbounds,true,3);

        initScene(R.id.begin_delayed_transition,R.layout.scene_begindelayedtransition,R.layout.scene_begindelayedtransition,true,4);

        //点击按钮操作
        view.findViewById(R.id.btn_changebounds).setOnClickListener(view->{
            //重置scene1、scene2
            initScene(R.id.change_bounds,R.layout.scene_1_changebounds,R.layout.scene_2_changebounds,false,0);
            //切换scene
            switchScene(new ChangeBounds(),0);
        });

        view.findViewById(R.id.btn_changetransform).setOnClickListener(view->{
            initScene(R.id.change_transform,R.layout.scene_1_changetransform,R.layout.scene_2_changetransform,false,1);
            switchScene(new ChangeTransform(),1);
        });

        view.findViewById(R.id.btn_changeimagetransform).setOnClickListener(view->{
            initScene(R.id.change_image_transform,R.layout.scene_1_changeimagetransform,R.layout.scene_2_changeimagetransform,false,2);
            switchScene(new ChangeImageTransform(),2);
        });

        view.findViewById(R.id.btn_changeclipbounds).setOnClickListener(view->{
            initScene(R.id.change_clip_bounds,R.layout.scene_1_changeclipbounds,R.layout.scene_2_changeclipbounds,false,3);
            switchScene(new ChangeClipBounds(),3);
        });

        mSceneRoot =  view.findViewById(R.id.scene_root);
        cuteboy= view.findViewById(R.id.xcuteboy);
        cutegirl=  view.findViewById(R.id.xcutegirl);
        hxy= view.findViewById(R.id.hxy);
        lly= view.findViewById(R.id.lly);
        primarySize=cuteboy.getLayoutParams().width;

        //点击图片后执行onClick
        cuteboy.setOnClickListener(this);
        cutegirl.setOnClickListener(this);
        hxy.setOnClickListener(this);
        lly.setOnClickListener(this);

        return view;
    }

    //点击图片变换
    @Override
    public void onClick(View v) {
        //动画转换启动，从mSceneRoot当前状态使用动画过渡到下面的状态
        TransitionManager.beginDelayedTransition(mSceneRoot, TransitionInflater.from(getContext()).inflateTransition(R.transition.explode_and_changebounds));
        //改变其中元素的位置、大小和可见性
        changeScene(v);
    }

    //设置所有初始场景为Scene1
    private void initScene(@IdRes int rootView, @LayoutRes int scene1_layout, @LayoutRes int scene2_layout,boolean isInitial,int group) {
        ViewGroup sceneRoot=  view.findViewById(rootView);
        //生成场景方法一
        scene1 = Scene.getSceneForLayout(sceneRoot, scene1_layout, getContext());
        scene2 = Scene.getSceneForLayout(sceneRoot, scene2_layout, getContext());
        if (group==3) {
            View inflate2 = LayoutInflater.from(getContext()).inflate(R.layout.scene_2_changeclipbounds, null);
            //设置裁剪边界
            ImageView imageView = inflate2.findViewById(R.id.cutegirl);
            imageView.setClipBounds(new Rect(200, 200, 400, 400));
            //生成场景方法二
            scene2=new Scene(sceneRoot,inflate2);
        }
        if (isInitial) {
            //初始化显示Scene1
            TransitionManager.go(scene1);
            //给状态数组赋初值
            isScene2[group]=false;
        }
    }


    private void switchScene(Transition transition,int group){
        //动画转换启动
        TransitionManager.go(isScene2[group]?scene1:scene2,transition);

        isScene2[group]=!isScene2[group];
    }


    private void changeScene(View view) {
        changeSize(view);
        changeVisibility(cuteboy,cutegirl,hxy,lly);
        view.setVisibility(View.VISIBLE);
    }

    // view的宽高1.5倍和原尺寸大小切换，配合ChangeBounds实现缩放效果
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

    // VISIBLE和INVISIBLE状态切换
    private void changeVisibility(View ...views){
        for (View view:views){
            view.setVisibility(view.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
        }
    }
}
