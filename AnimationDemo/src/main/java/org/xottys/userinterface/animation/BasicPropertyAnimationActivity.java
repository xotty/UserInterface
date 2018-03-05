package org.xottys.userinterface.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class BasicPropertyAnimationActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = "PropertyAnimation1";
    private Context mContext;
    private ImageView myView;
    private int ScreenWidth;
    private ObjectAnimator anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_basicpropertyanimation);
    }

    @Override
    public void initView() {
        findViewById(R.id.alpha).setOnClickListener(this);
        findViewById(R.id.scale).setOnClickListener(this);
        findViewById(R.id.translate).setOnClickListener(this);
        findViewById(R.id.rotate).setOnClickListener(this);
        findViewById(R.id.set).setOnClickListener(this);
        findViewById(R.id.background).setOnClickListener(this);
        myView = findViewById(R.id.myView);
        myView.setOnClickListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ScreenWidth = displayMetrics.widthPixels;
    }

    @Override
    public void onClick(View v) {
        if (anim != null && myView != null) {
            anim.end();
            anim.cancel();
            myView.clearAnimation();
            anim = null;
        }
        switch (v.getId()) {
            case R.id.set:
                ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(myView, "alpha", 1.0f, 0.6f, 0.3f, 0.1f);
                ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(myView, "scaleX", 0.0f, 1.0f);
                ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(myView, "scaleY", 0.0f, 2.0f);
                ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(myView, "rotation", 0, 180);
                ObjectAnimator transXAnim = ObjectAnimator.ofFloat(myView, "translationX", 100, 400);
                ObjectAnimator transYAnim = ObjectAnimator.ofFloat(myView, "translationY", 100, 500);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
                set.setDuration(3000);
                set.start();
                break;
            case R.id.alpha:
                AlpahAnimation();
                break;
            case R.id.translate:
                TranslationAnimation();
                break;
            case R.id.scale:
                ScaleAnimation();
                break;
            case R.id.rotate:
                RotateAnimation();
                break;
            case R.id.myView:
                Toast.makeText(mContext, "我被点击了", Toast.LENGTH_SHORT).show();
                 alphaAnim = ObjectAnimator.ofFloat(myView, "alpha", 0.1f, 0.5f, 0.8f, 1.0f);
                 scaleXAnim = ObjectAnimator.ofFloat(myView, "scaleX", 1.0f, 1.0f);
                 scaleYAnim = ObjectAnimator.ofFloat(myView, "scaleY", 2.0f, 1.0f);
                 rotateAnim = ObjectAnimator.ofFloat(myView, "rotation", 180, 360);
                 transXAnim = ObjectAnimator.ofFloat(myView, "translationX", 400, 0);
                 transYAnim = ObjectAnimator.ofFloat(myView, "translationY", 750, 0);

                set = new AnimatorSet();
                set.playSequentially(transXAnim, transYAnim,alphaAnim, scaleXAnim, scaleYAnim, rotateAnim );
                set.setDuration(1000);
                set.start();
                break;
            case R.id.background:
                Button btn=findViewById(R.id.background);
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(btn,"backgroundColor",0xfff10f0f,0xff0f94f1,0xffeaf804,0xfff92a0f,0xffcfc6c9);
                objectAnimator.setDuration(2000);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                objectAnimator.start();
                break;
            default:
                break;
        }
    }

    private void ScaleAnimation() {

        anim = ObjectAnimator.ofFloat(myView, "scaleX", 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f);
        anim.setDuration(1000);
        anim.setRepeatCount(-1);
        anim.start();

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
            public void onAnimationCancel(Animator animation) {
                Log.i(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i(TAG, "onAnimationRepeat");
            }
        });
    }

    private void TranslationAnimation() {

        anim = ObjectAnimator.ofFloat(myView, "translationX", -200, 0, ScreenWidth, 0);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(-1);
        anim.setDuration(2000);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i(TAG, "onAnimationUpdate");
            }
        });
    }

    private void RotateAnimation() {
        anim = ObjectAnimator.ofFloat(myView, "rotation", 0f, 360f);
        anim.setDuration(1000);
        anim.start();
    }

    private void AlpahAnimation() {
        anim = ObjectAnimator.ofFloat(myView, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f);
        anim.setRepeatCount(-1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setDuration(2000);
        anim.start();
    }


}
