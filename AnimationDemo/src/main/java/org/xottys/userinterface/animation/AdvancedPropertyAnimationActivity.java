package org.xottys.userinterface.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.xottys.userinterface.animation.views.AlipayFailureView;
import org.xottys.userinterface.animation.views.AlipaySuccessView;
import org.xottys.userinterface.animation.views.PVHolderKFrameView;
import org.xottys.userinterface.animation.views.PathView;
import org.xottys.userinterface.animation.views.PointAnimView;

import java.text.DecimalFormat;

public class AdvancedPropertyAnimationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AdvancedPropertyAnimationActivity";

    private Button mMenuButton;
    private Button mItemButton1;
    private Button mItemButton2;
    private Button mItemButton3;
    private Button mItemButton4;
    private Button mItemButton5;

    private boolean mIsMenuOpen, animationStartFlag;
    private FrameLayout rootview;
    private View contentView;
    private AlipaySuccessView alipaySuccessView;
    private AlipayFailureView alipayFailureView;
    private PointAnimView animView;
    private PVHolderKFrameView pvHolderKFrameView;
    private int clickedItemButtonID;

    private TextView tv = null, textView;
    private RelativeLayout animationContent1 = null;
    private LinearLayout animationContent2 = null, animationLayout, animationButton;
    ObjectAnimator pathAnimator;
    ImageView iv, iv1, iv2, iv3;

    private int screenWidth;

    private int screenHeight;

    private boolean isSearchBoxChecked = false;

    private boolean isTwitterChecked = false;

    DecimalFormat format = new DecimalFormat("#.00");
    ValueAnimator anim;

    private static final String[] LIST_STRINGS_EN = new String[] {
            "One",
            "Two",
            "Three Animation",
            "Four",
            "Five",
            "Six"
    };
    private static final String[] LIST_STRINGS_FR = new String[] {
            "Un",
            "Deux",
            "Trois",
            "Quatre",
            "Le Five",
            "Six"
    };

    ListView mEnglishList;
    ListView mFrenchList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancedpropertyanimation);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        tv = findViewById(R.id.title);
        initView();
    }

    private void initView() {
        animationStartFlag = false;
        rootview = findViewById(R.id.menuframe);
        mMenuButton = findViewById(R.id.menu);
        mMenuButton.setOnClickListener(this);

        mItemButton1 = findViewById(R.id.item1);
        mItemButton1.setOnClickListener(this);

        mItemButton2 = findViewById(R.id.item2);
        mItemButton2.setOnClickListener(this);

        mItemButton3 = findViewById(R.id.item3);
        mItemButton3.setOnClickListener(this);

        mItemButton4 = findViewById(R.id.item4);
        mItemButton4.setOnClickListener(this);

        mItemButton5 = findViewById(R.id.item5);
        mItemButton5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mMenuButton) {

            if (!mIsMenuOpen) {
                mIsMenuOpen = true;
                doAnimateOpen(mItemButton1, 0, 5, 600);
                doAnimateOpen(mItemButton2, 1, 5, 600);
                doAnimateOpen(mItemButton3, 2, 5, 600);
                doAnimateOpen(mItemButton4, 3, 5, 600);
                doAnimateOpen(mItemButton5, 4, 5, 600);

            } else {
                mIsMenuOpen = false;
                doAnimateClose(mItemButton1, 0, 5, 600);
                doAnimateClose(mItemButton2, 1, 5, 600);
                doAnimateClose(mItemButton3, 2, 5, 600);
                doAnimateClose(mItemButton4, 3, 5, 600);
                doAnimateClose(mItemButton5, 4, 5, 600);

                if (clickedItemButtonID != 0) {
                    if (clickedItemButtonID != 5) {
                        if (animationStartFlag)
                            contentView.findViewById(R.id.stop).performClick();
                        animationLayout.removeView(animationButton);
                    }


                    tv.setText("Property Animation Demo\nPress Button on bottom right To start");
                    tv.setTextSize(15);
                    animationLayout.removeView(animationContent1);
                }
                clickedItemButtonID = 0;

            }
        } else {

            ViewGroup.LayoutParams lp4 = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            RelativeLayout.LayoutParams lp7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp1.setMargins(0, 100, 0, 0);//左上右下


            rootview.removeView(contentView);
            contentView = getLayoutInflater().inflate(R.layout.property_animation2_layout, null);
            contentView.setLayoutParams(lp1);
            rootview.addView(contentView);
            animationLayout = contentView.findViewById(R.id.llay);
            animationButton = contentView.findViewById(R.id.btnzone);

            animationContent1 = contentView.findViewById(R.id.content);
            animationContent2 = new LinearLayout(this);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            animationContent2.setLayoutParams(lp2);
            animationContent2.setOrientation(LinearLayout.HORIZONTAL);
            animationContent1.removeAllViews();
            if (clickedItemButtonID != 0) {
                doZoomButtonAnimation(clickedItemButtonID, false);
                if (animationStartFlag)
                    contentView.findViewById(R.id.stop).performClick();
            }
            switch (v.getId()) {
                case R.id.item1:

                    animView = new PointAnimView(this);
                    alipaySuccessView = new AlipaySuccessView(this);
                    alipayFailureView = new AlipayFailureView(this);
                    tv.setText("ValueAnimator Demo");
                    animView.setLayoutParams(lp4);
                    animationContent1.addView(animView);

                    LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp5.setMargins(100, 20, 100, 20);
                    alipaySuccessView.setLayoutParams(lp5);
                    alipayFailureView.setLayoutParams(lp5);


                    animationContent2.addView(alipaySuccessView);

                    animationContent2.addView(alipayFailureView);
                    animationContent1.addView(animationContent2);
                    //添加相应的规则
                    //  lp6.addRule(RelativeLayout.BELOW, animView.getId());
                    //设置控件的位置
                    lp6.setMargins(0, 300, 0, 0);//左上右下
                    animationContent2.setLayoutParams(lp6);

                    animView.setInterpolatorType(2);

                    //   lp7.addRule(RelativeLayout.BELOW, animView.getId());
                    lp7.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                    lp7.setMargins(0, 900, 0, 0);

                    textView = new TextView(this);
                    textView.setText("0");
                    textView.setTextAppearance(this, R.style.myTextStyle);
                    textView.setLayoutParams(lp7);
                    animationContent1.addView(textView);

                    alipayFailureView.addCircleAnimatorEndListner(new AlipayFailureView.OnCircleFinishListener() {
                        @Override
                        public void onCircleDone() {
                            alipayFailureView.setPaintColor(getResources().getColor(R.color.cpb_red));
                        }
                    });

                    alipaySuccessView.addCircleAnimatorEndListner(new AlipaySuccessView.OnCircleFinishListener() {
                        @Override
                        public void onCircleDone() {
                            alipaySuccessView.setPaintColor(Color.GREEN);
                        }
                    });

                    clickedItemButtonID = 1;
                    break;
                case R.id.item2:
                    clickedItemButtonID = 2;
                    tv.setText("PropertyValueHolder Demo");
                    pvHolderKFrameView = new PVHolderKFrameView(this);
                    pvHolderKFrameView.setLayoutParams(lp4);
                    animationContent1.addView(pvHolderKFrameView);


                    break;
                case R.id.item3:
                    clickedItemButtonID = 3;
                    animationLayout.removeView(animationButton);
                    tv.setText("ObjectAnimator Demo");
                    View roatatingListView = getLayoutInflater().inflate(R.layout.rotating_list, null);
//
//                    View vetctorView = getLayoutInflater().inflate(R.layout.animated_vector, null);
//                    iv1 = vetctorView.findViewById(R.id.iv_1);
//                    iv2 = vetctorView.findViewById(R.id.iv_2);
//                    iv3 = vetctorView.findViewById(R.id.iv_3);
                    animationContent1.addView(roatatingListView);
                    //FrameLayout container = (LinearLayout) findViewById(R.id.container);
                    mEnglishList = (ListView) findViewById(R.id.list_en);
                    mFrenchList = (ListView) findViewById(R.id.list_fr);

                    // Prepare the ListView
                    final ArrayAdapter<String> adapterEn = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, LIST_STRINGS_EN);
                    // Prepare the ListView
                    final ArrayAdapter<String> adapterFr = new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, LIST_STRINGS_FR);

                    mEnglishList.setAdapter(adapterEn);
                    mFrenchList.setAdapter(adapterFr);
                    mFrenchList.setRotationY(-90f);

                    Button starter = (Button) findViewById(R.id.button);
                    starter.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            flipit();
                        }
                    });

                    break;
                case R.id.item4:
                    clickedItemButtonID = 4;
                    tv.setText("Path Animation Demo");
                    PathView pathView = new PathView(this);

                    iv = new ImageView(this);
                    LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(
                            100, 100);
                    lp3.setMargins(10, 10, 0, 0);//左上右下

                    iv.setLayoutParams(lp3);
                    iv.setBackgroundColor(Color.RED);
                    animationContent1.addView(iv);
                    animationContent1.addView(pathView);
                    break;
                case R.id.item5:
                    clickedItemButtonID = 5;
                    animationLayout.removeView(animationButton);
                    tv.setText("CircularReveal Demo\nClick Image to Start Animation");

                    lp7.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

                    View circularRevealView = getLayoutInflater().inflate(R.layout.circularreveal_animator, null);
                    circularRevealView.setLayoutParams(lp7);
                    animationContent1.addView(circularRevealView);
                    iv1 = circularRevealView.findViewById(R.id.oval);
                    iv2 = circularRevealView.findViewById(R.id.rect);

                    iv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View vv) {
                            Animator animator1 = ViewAnimationUtils.createCircularReveal(iv1, iv1.getWidth() / 2, iv1.getHeight() / 2, iv1.getWidth(), 0);
                            animator1.setDuration(2000);
                            animator1.start();
                        }
                    });
                    iv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View vv) {
                            Animator animator2 = ViewAnimationUtils.createCircularReveal(iv2, 0, 0, 0, (float) Math.hypot(iv2.getWidth(), iv2.getHeight()));
                            animator2.setDuration(2000);
                            animator2.start();
                        }
                    });
                    break;
            }
            if (clickedItemButtonID != 5 && clickedItemButtonID != 3) {
                contentView.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View vv) {
                        animationStartFlag = true;
                        switch (clickedItemButtonID) {
                            case 1:
                                animView.StartAnimation();
                                alipaySuccessView.loadCircle(200);
                                alipayFailureView.startAnim(200);
                                startNumberAnimator(textView);
                                break;
                            case 2:
                                pvHolderKFrameView.startAnimation(AdvancedPropertyAnimationActivity.this);
                                break;

                            case 4:
                                startPathAnim(iv);

                                break;
                        }
                    }
                });
                contentView.findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        switch (clickedItemButtonID) {
                            case 1:
                                animView.StopAnimation();
                                alipaySuccessView.stopAnimation();
                                alipayFailureView.stop();
                                anim.cancel();
                                break;
                            case 2:
                                pvHolderKFrameView.stopAnimation();
                                break;

                            case 4:
                                if (animationStartFlag)
                                    pathAnimator.cancel();
                                break;
                        }
                        animationStartFlag = false;
                    }
                });
            }
            if (clickedItemButtonID != 0) {
                doZoomButtonAnimation(clickedItemButtonID, true);
            }
        }
    }


    /**
     * 打开菜单的动画
     *
     * @param view   执行动画的view
     * @param index  view在动画序列中的顺序,从0开始
     * @param total  动画序列的个数
     * @param radius 动画半径
     *               <p>
     *               Math.sin(x):x -- 为number类型的弧度，角度乘以0.017(2π/360)可以转变为弧度
     */
    private void doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            view.setClickable(true);
        }
        //double degree = Math.toRadians(90)/(total - 1) * index;
        double degree = Math.PI / ((total - 1) * 2) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(500).start();
    }

    /**
     * 关闭菜单的动画
     *
     * @param view   执行动画的view
     * @param index  view在动画序列中的顺序
     * @param total  动画序列的个数
     * @param radius 动画半径
     */
    private void doAnimateClose(final View view, int index, int total,
                                int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));

        set.setDuration(500).start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setButtnGone();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void zoomButtonAnimation(View view, boolean isOut) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }

        AnimatorSet set = new AnimatorSet();
        //包含缩放和透明度动画
        if (isOut) {
            set.playTogether(

                    ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.5f),
                    ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.5f),
                    ObjectAnimator.ofFloat(view, "alpha", 1f, 0.3f));
        } else {
            set.playTogether(

                    ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1f),
                    ObjectAnimator.ofFloat(view, "scaleY", 1.5f, 1f),
                    ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f));

        }
        //动画周期为300ms
        set.setDuration(300).start();
    }

    private void doZoomButtonAnimation(int buttonID, boolean isOut) {
        switch (buttonID) {
            case 1:
                zoomButtonAnimation(mItemButton1, isOut);
                mItemButton1.setClickable(!isOut);
                break;
            case 2:
                zoomButtonAnimation(mItemButton2, isOut);
                mItemButton2.setClickable(!isOut);
                break;
            case 3:
                zoomButtonAnimation(mItemButton3, isOut);
                mItemButton3.setClickable(!isOut);
                break;
            case 4:
                zoomButtonAnimation(mItemButton4, isOut);
                mItemButton4.setClickable(!isOut);
                break;
            case 5:
                zoomButtonAnimation(mItemButton5, isOut);
                mItemButton5.setClickable(!isOut);
                break;
        }
    }

    public void startPathAnim(View view) {

        Path path = new Path();
        path.moveTo(10, 10);
        path.quadTo(screenWidth - 100, 400, screenWidth - 1200, screenHeight - 700);

        pathAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
        pathAnimator.setDuration(2000);
        pathAnimator.setRepeatCount(1);
        pathAnimator.setRepeatMode(ValueAnimator.REVERSE);
        pathAnimator.start();
    }

    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();
    private void flipit() {
        final ListView visibleList;
        final ListView invisibleList;
        if (mEnglishList.getVisibility() == View.GONE) {
            visibleList = mFrenchList;
            invisibleList = mEnglishList;
        } else {
            invisibleList = mFrenchList;
            visibleList = mEnglishList;
        }
        ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);
            }
        });
        visToInvis.start();
    }


    public void setButtnGone() {
        mItemButton1.setVisibility(View.GONE);
        mItemButton2.setVisibility(View.GONE);
        mItemButton3.setVisibility(View.GONE);
        mItemButton4.setVisibility(View.GONE);
        mItemButton5.setVisibility(View.GONE);
    }

    public void startNumberAnimator(final TextView view) {
        anim = ValueAnimator.ofFloat(0, 2000);
        anim.setDuration(2000);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                view.setText(format.format(value));
            }
        });
        anim.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (animationStartFlag)
            contentView.findViewById(R.id.stop).performClick();
    }
}
