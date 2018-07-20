/**
 * 本例演示场景动画TransitionManager的用法之二：
 * 1)在xml中用标签<transitionManager>定义一系列场景动画，然后加载生成TransitionManager对象
 *    TransitionInflater transitionInflater = TransitionInflater.from(getContext());
 *    mTransitionManager = transitionInflater.inflateTransitionManager(R.transition.transitions_mgr,
 * 2)使用TransitionManager启动转换动画
 *  mTransitionManager.transitionTo(mScene2)会直接使用xml中的动画
 *  也可以在代码中定义transition动画，它优先于xml中的定义：mTransitionManager.setTransition(scene,transition);
 * <p>
 * <br/>Copyright (C), 2017-2018, Google
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author Google
 * @version 1.0
 */
package org.xottys.userinterface.animation.transition;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.xottys.userinterface.animation.R;

public class TransitionManagerFragment2 extends Fragment {

    Scene mScene1, mScene2, mScene3;
    ViewGroup mSceneRoot;
    TransitionManager mTransitionManager;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transitionmanager2, null);
        mSceneRoot = view.findViewById(R.id.sceneRoot);
        //定义场景
        mScene1 = Scene.getSceneForLayout(mSceneRoot, R.layout.transition_manager_scene1, getContext());
        mScene2 = Scene.getSceneForLayout(mSceneRoot, R.layout.transition_manager_scene2, getContext());
        mScene3 = Scene.getSceneForLayout(mSceneRoot, R.layout.transition_manager_scene3, getContext());
        //加载xml方式的TransitionManager
        TransitionInflater transitionInflater = TransitionInflater.from(getContext());
        mTransitionManager = transitionInflater.inflateTransitionManager(R.transition.transitions_mgr,
                mSceneRoot);
        return view;
    }

    //onCreateView之后设置RadioButton点击事件
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RadioGroup radiogroup = view.findViewById(R.id.scenes);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.scene1:
                        //使用xml中定义的过渡动画转换到mScene1
                        mTransitionManager.transitionTo(mScene1);
                        break;
                    case R.id.scene2:
                        //设置到mScene2的过渡动画
                        mTransitionManager.setTransition(mScene2, new ChangeBounds());
                        //转换到mScene2
                        mTransitionManager.transitionTo(mScene2);
                        break;
                    case R.id.scene3:
                        //设置从mScene2到mScene3的过渡动画
                        mTransitionManager.setTransition(mScene2, mScene3, new Slide(Gravity.TOP));
                        //转换到mScene3
                        mTransitionManager.transitionTo(mScene3);
                        break;
                    case R.id.scene4:
                        //缩小view的尺寸，生成一个虚拟的Scene
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

    //改变视图大小
    private void setNewSize(int id, int width, int height) {
        View v = view.findViewById(id);
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = width;
        params.height = height;
        v.setLayoutParams(params);
    }
}
