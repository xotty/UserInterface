/**
 * 本例演示了属性动画之ObjectAnimator的基本用法，具体使用步骤如下：
 * 1）用代码或xml定义动画或动画集合
 * 2）xml动画需要先加载：(ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.mAnimator);
 *    然后指定动画对象：mObjectAnimator.setTarget(mView)
 * 3）补充或定义相关动画参数：duration、repeatCount、repeatMode、interpolator等
 * 4）启动动画：mObjectAnimator.start();
 * 对于动画集合的各动画可以依次顺序播放也可以同时一起播放
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class BasicPropertyAnimationActivity extends Activity implements OnClickListener {

    private static final String TAG = "BasicPropertyAnimation";
    private ImageView myView;
    private ObjectAnimator anim;
    Button btn_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basicpropertyanimation);
        initView();
    }

    public void initView() {
        findViewById(R.id.alpha).setOnClickListener(this);
        findViewById(R.id.scale).setOnClickListener(this);
        findViewById(R.id.translate).setOnClickListener(this);
        findViewById(R.id.rotate).setOnClickListener(this);
        findViewById(R.id.set).setOnClickListener(this);
        findViewById(R.id.background).setOnClickListener(this);
        btn_cancel=findViewById(R.id.cancel);
        btn_cancel.setOnClickListener(this);
        myView = findViewById(R.id.myView);
        myView.setOnClickListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        btn_cancel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (anim != null && myView != null && v.getId()!=R.id.cancel) {
            anim.end();
            anim.cancel();
            myView.clearAnimation();
            anim = null;
        }
        switch (v.getId()) {
            case R.id.set:
                btn_cancel.setVisibility(View.INVISIBLE);
                //代码定义动画集合
                AnimatorSet set = new AnimatorSet();

                ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(myView, "alpha", 1.0f, 0.6f, 0.3f, 0.1f, 1.0f);
                ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(myView, "scaleX", 0.0f, 1.0f);
                ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(myView, "scaleY", 0.0f, 2.0f, 1.0f);
                ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(myView, "rotation", 0, 180, 0);
                ObjectAnimator transXAnim = ObjectAnimator.ofFloat(myView, "translationX", 100, 400, 100);
                ObjectAnimator transYAnim = ObjectAnimator.ofFloat(myView, "translationY", 100, 500, 100);

                //给动画集合添加动画，并设置其播放方式为同时一起播放
                set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);

                set.setDuration(3000);
                set.start();
                break;
            case R.id.alpha:
                btn_cancel.setVisibility(View.VISIBLE);
                AlpahAnimation();

                break;
            case R.id.translate:
                btn_cancel.setVisibility(View.VISIBLE);
                TranslationAnimation();
                break;
            case R.id.scale:
                btn_cancel.setVisibility(View.VISIBLE);
                ScaleAnimation();
                break;
            case R.id.rotate:
                btn_cancel.setVisibility(View.VISIBLE);
                RotateAnimation();
                break;
            case R.id.myView:
                btn_cancel.setVisibility(View.INVISIBLE);
                //xml定义动画集合
                set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                        R.animator.basic_object_animator_set);
                set.setTarget(myView);
                /*等价代码如下：
                alphaAnim = ObjectAnimator.ofFloat(myView, "alpha", 0.1f, 0.5f, 0.8f, 1.0f);
                scaleXAnim = ObjectAnimator.ofFloat(myView, "scaleX", 1.0f, 2.0f,1.0f);
                scaleYAnim = ObjectAnimator.ofFloat(myView, "scaleY", 2.0f, 1.0f);
                rotateAnim = ObjectAnimator.ofFloat(myView, "rotation", 180, 360);
                transXAnim = ObjectAnimator.ofFloat(myView, "translationX", 400, 0);
                transYAnim = ObjectAnimator.ofFloat(myView, "translationY", 750, 0);
                //给动画集合添加动画，并设置其播放方式为逐个依次播放
                set.playSequentially(transXAnim, transYAnim, alphaAnim, scaleXAnim, scaleYAnim, rotateAnim);*/

                set.setDuration(1000);
                set.start();
                break;
            case R.id.background:
                btn_cancel.setVisibility(View.INVISIBLE);
                Button btn = findViewById(R.id.background);
                //ArgbEvaluator的典型用法
                ObjectAnimator objectAnimator = ObjectAnimator.ofArgb(btn, "backgroundColor", 0xfff10f0f, 0xff0f94f1, 0xffeaf804, 0xfff92a0f, 0xffcfc6c9);
                /*等价替换写法如下：
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(btn, "backgroundColor", 0xfff10f0f, 0xff0f94f1, 0xffeaf804, 0xfff92a0f, 0xffcfc6c9);
                objectAnimator.setEvaluator(new ArgbEvaluator());*/

                objectAnimator.setDuration(2000);
                objectAnimator.start();
                break;
            case R.id.cancel:
                btn_cancel.setVisibility(View.INVISIBLE);
                anim.cancel();
                break;
            default:
                break;
        }
    }

    private void AlpahAnimation() {
        //用代码定义动画及其属性，这里使用四种构造器中最常见的ofFloat
        anim = ObjectAnimator.ofFloat(myView, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f);
        anim.setRepeatCount(-1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setDuration(2000);
        //启动动画
        anim.start();
    }

    private void TranslationAnimation() {
        //加载xml定义的动画
        anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.basic_object_animator);
        //设定动画对象
        anim.setTarget(myView);
        /*上述动画定义与下列代码方式等价
        anim = ObjectAnimator.ofFloat(myView, "translationX", -200, 400);
        anim.setRepeatCount(-1);
        anim.setDuration(2000);*/
        //补充设定动画属性
        anim.setRepeatMode(ObjectAnimator.REVERSE);

        anim.start();

       //设置动画过程跟踪监听器
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG, "onAnimationUpdate");
            }
        });

    }

    private void ScaleAnimation() {

        anim = ObjectAnimator.ofFloat(myView, "scaleX", 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f);
        anim.setDuration(1000);
        anim.setRepeatCount(10);
        //为属性动画设置插值器
        anim.setInterpolator(new AccelerateDecelerateInterpolator());

        anim.start();

        //设置动画状态监听器
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG, "onAnimationRepeat");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i(TAG, "onAnimationCancel");
            }
        });
    }


    private void RotateAnimation() {
        anim = ObjectAnimator.ofFloat(myView, "rotation", 0f, 360f);
        anim.setDuration(1000);
        anim.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //动画移除所有监听器
        if (anim != null && myView != null){
            anim.removeAllListeners();
            anim.removeAllUpdateListeners();
            anim.end();
            anim.cancel();
            myView.clearAnimation();
            anim = null;
        }
    }
}
