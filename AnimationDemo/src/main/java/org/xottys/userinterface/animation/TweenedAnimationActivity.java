/**本例演示了View Animation补间动画之Animation及其子类的基本用法
 * 1)AlphaAnimation
 * 2)RotateAnimation
 * 3)ScaleAnimation
 * 4)TranslateAnimation
 * 5)AnimationSet
 * 具体使用步骤如下：
 * 1）用代码或xml（在res/anim目录下）定义动画或动画集合
 * 2）xml定义的动画先加载：Animation  myAnimation=AnimationUtils.loadAnimation(this, R.anim.myanim）
 * 3）设置动画或动画集合的相关属性，这也可以在xml中事先定义好
 * 4）启动动画：myView.startAnimation(myAnimation)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:TweenedAnimationActivity
 * <br/>Date:Mar，2018
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class TweenedAnimationActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String[] INTERPOLATORS = {
            "Linear", "Accelerate", "Decelerate", "AccelerateDecelerate",
            "Anticipate", "Overshoot", "AnticipateOvershoot",
            "Bounce"
    };
    private Context mContext;
    private ImageView img;
    private CheckBox keep;
    private CheckBox loop;
    private CheckBox reverse;
    private RadioButton rb1, rb2, rb3;

    private SeekBar pivotX, pivotY, degree, time;
    private float pxValue, pyValue, deValue;
    private int timeValue;
    private TextView xValue, yValue, dValue, tValue;

    private Animation animation;
    private Interpolator interpolator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_tween_animation);
        initView();
    }

    private void initView() {
        findViewById(R.id.alpha).setOnClickListener(this);
        findViewById(R.id.scale).setOnClickListener(this);
        findViewById(R.id.translate1).setOnClickListener(this);
        findViewById(R.id.translate2).setOnClickListener(this);
        findViewById(R.id.rotate).setOnClickListener(this);
        findViewById(R.id.stopAnim).setOnClickListener(this);
        findViewById(R.id.animation_set).setOnClickListener(this);
        findViewById(R.id.img1).setOnClickListener(this);
        keep = findViewById(R.id.keep);
        loop = findViewById(R.id.loop);
        reverse =  findViewById(R.id.reverse);
        rb1 =  findViewById(R.id.rb1);
        rb2 =  findViewById(R.id.rb2);
        rb3 =  findViewById(R.id.rb3);
        pivotX =  findViewById(R.id.pivotX);
        pivotY =  findViewById(R.id.pivotY);
        degree =  findViewById(R.id.degree);
        time = findViewById(R.id.time);
        degree.setProgress(25);
        deValue = 360 * 25 / 100.0f;
        time.setProgress(10);
        timeValue = 100 * 10;
        xValue =  findViewById(R.id.xvalue);
        yValue =  findViewById(R.id.yvalue);
        dValue =  findViewById(R.id.dvalue);
        dValue.setText(String.valueOf(deValue));
        tValue =  findViewById(R.id.tValue);
        tValue.setText(String.valueOf(timeValue) + " ms");

        img =  findViewById(R.id.img1);
        animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha_anim);

        //用Spinner选择插值器interpolator
        Spinner s =  findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, INTERPOLATORS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);

        //旋转基点X坐标
        pivotX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pxValue = progress / 100.0f;
                xValue.setText(String.valueOf(pxValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //旋转基点Y坐标
        pivotY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pyValue = progress / 100.0f;
                yValue.setText(String.valueOf(pyValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //旋转度数
        degree.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                deValue = 360 * progress / 100.0f;
                dValue.setText(String.valueOf(deValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //旋转时间
        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= 10) {
                    progress = 10;
                } else if (progress >= 100) {
                    progress = 100;
                }
                timeValue = 100 * progress;
                tValue.setText(String.valueOf(timeValue) + " ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alpha:
                AlpahAnimation();
                break;
            case R.id.translate1:
                TranslationAnimation(1);
                break;
            case R.id.translate2:
                TranslationAnimation(2);
                break;
            case R.id.scale:
                ScaleAnimation();
                break;
            case R.id.rotate:
                RotateAnimation();
                break;
            case R.id.animation_set:
                AnimationSet();
                break;
            case R.id.stopAnim:
                //停止动画
                img.clearAnimation();
                if (animation != null) {
                    animation.cancel();
                    animation.reset();
                }
                //为各选项设初值
                loop.setChecked(false);
                reverse.setChecked(false);
                keep.setChecked(false);
                pivotX.setProgress(0);
                pivotY.setProgress(0);
                degree.setProgress(25);
                time.setProgress(10);
                break;
        }
    }

    private void TranslationAnimation(int kind) {
        //加载xml动画
        if (kind == 1) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.translate);
        } else if (kind == 2) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.shake);
        }
        //为动画设置结束时状态属性
        if (keep.isChecked()) {
            animation.setFillAfter(true);   //保持动画结束时状态
        } else {
            animation.setFillAfter(false);  //回到动画开始前状态
        }
        //为动画设置重复次数属性
        if (loop.isChecked()) {
            animation.setRepeatCount(-1);   //无限重复
        } else {
            animation.setRepeatCount(0);    //不重复
        }
        //为动画设置是否重复方式属性
        if (reverse.isChecked()) {
            animation.setRepeatMode(Animation.REVERSE);     //动画重复时倒序回放
        } else {
            animation.setRepeatMode(Animation.RESTART);     //动画重复时顺序启动
        }

        //为动画设置插值器,若未设置，缺省使用linear_interpolator
        if (kind == 1)
            animation.setInterpolator(interpolator);

        //启动动画
        img.startAnimation(animation);
    }


    private void RotateAnimation() {
        //用代码定义动画
        animation = new RotateAnimation(-deValue, deValue, Animation.RELATIVE_TO_SELF,
                pxValue, Animation.RELATIVE_TO_SELF, pyValue);
        //设置动画持续时长
        animation.setDuration(timeValue);

        //设置Animation公共属性
        if (keep.isChecked()) {
            animation.setFillAfter(true);
        } else {
            animation.setFillAfter(false);
        }
        if (loop.isChecked()) {
            animation.setRepeatCount(-1);
        } else {
            animation.setRepeatCount(0);
        }

        if (reverse.isChecked()) {
            animation.setRepeatMode(Animation.REVERSE);
        } else {
            animation.setRepeatMode(Animation.RESTART);
        }
        animation.setInterpolator(interpolator);

        //启动动画
        img.startAnimation(animation);
    }


    private void AlpahAnimation() {
        animation = AnimationUtils.loadAnimation(mContext, R.anim.alpha_anim);

        if (keep.isChecked()) {
            animation.setFillAfter(true);
        } else {
            animation.setFillAfter(false);
        }
        if (loop.isChecked()) {
            animation.setRepeatCount(-1);
        } else {
            animation.setRepeatCount(0);
        }

        if (reverse.isChecked()) {
            animation.setRepeatMode(Animation.REVERSE);
        } else {
            animation.setRepeatMode(Animation.RESTART);
        }
        animation.setInterpolator(interpolator);

        img.startAnimation(animation);
    }

    private void ScaleAnimation() {
        if (rb1.isChecked()) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_anim1);
        } else if (rb2.isChecked()) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_anim2);
        } else if (rb3.isChecked()) {
            animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_anim3);
        }

        if (keep.isChecked()) {
            animation.setFillAfter(true);
        } else {
            animation.setFillAfter(false);
        }

        if (loop.isChecked()) {
            animation.setRepeatCount(-1);
        } else {
            animation.setRepeatCount(0);
        }
        animation.setInterpolator(interpolator);

        img.startAnimation(animation);
    }

    private void AnimationSet() {
        //代码定义AnimationSet，也可以用xml定义
        //参数true指在AnimationSet中定义一个插值器（interpolater），它下面的所有动画共同使用
        //startOffset, shareInterpolator属性仅用于AnimationSet自身，
        AnimationSet animationSet=new AnimationSet(true);

        Animation animation1 = AnimationUtils.loadAnimation(mContext, R.anim.translate);
        Animation animation2 = AnimationUtils.loadAnimation(mContext, R.anim.alpha_anim);

        //为AnimationSet添加动画
        animationSet.addAnimation(animation1);
        animationSet.addAnimation(animation2);

        //duration, repeatMode, fillBefore, fillAfter属性将会被延伸到集合中各动画
        if (keep.isChecked()) {
            animationSet.setFillAfter(true);
        } else {
            animationSet.setFillAfter(false);
        }
        if (reverse.isChecked()) {
            animationSet.setRepeatMode(Animation.REVERSE);
        } else {
            animationSet.setRepeatMode(Animation.RESTART);
        }
        //repeatCount, fillEnabled属性在AnimationSet中无效，需直接定义在集合中各动画上
        if (loop.isChecked()) {
            animation1.setRepeatCount(-1);
            animation2.setRepeatCount(-1);
        } else {
            animation1.setRepeatCount(0);
            animation2.setRepeatCount(0);
        }

        animationSet.setInterpolator(interpolator);

        img.startAnimation(animationSet);
    }

    //选择确定使用的插值器
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0:
                interpolator = AnimationUtils.loadInterpolator(this,
                        android.R.anim.linear_interpolator);
                break;
            case 1:
                interpolator = AnimationUtils.loadInterpolator(this,
                        android.R.anim.accelerate_interpolator);
                break;
            case 2:
                interpolator = AnimationUtils.loadInterpolator(this,
                        android.R.anim.decelerate_interpolator);
                break;
            case 3:
                interpolator = AnimationUtils.loadInterpolator(this,
                        android.R.anim.accelerate_decelerate_interpolator);
                break;
            case 4:
                interpolator = AnimationUtils.loadInterpolator(this,
                        android.R.anim.anticipate_interpolator);
                break;
            case 5:
                interpolator = AnimationUtils.loadInterpolator(this,
                        android.R.anim.overshoot_interpolator);
                break;
            case 6:
                interpolator = AnimationUtils.loadInterpolator(this,
                        android.R.anim.anticipate_overshoot_interpolator);
                break;
            case 7:
                interpolator = AnimationUtils.loadInterpolator(this,
                        android.R.anim.bounce_interpolator);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

}
