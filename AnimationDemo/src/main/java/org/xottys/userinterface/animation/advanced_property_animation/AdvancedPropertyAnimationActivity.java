/**
 * 本例演示了属性动画的一系列高级用法：
 * 1)ValueAnimator--startNumberAnimator()、PointAnimView、AlipaySucessView、AlipayFailureView
 * 构造时仅提供起止值或状态，在监听器AnimatorUpdateListener中自定义动画效果，最后启动动画
 *
 * 2)PropertValueHolder--PVHolderFrameView
 *
 * 3)ObjectAnimator--flipit()、zoomButtonAnimation()
 *  ObjectAnimator旋转动画和Visibility的变化组合，实现整个ListView立体翻转显示效果
 *
 * 4)PathAnimtion--startPathAnim()
 *   先定义好Path，然后用带path参数的构造器构建ObjectAnimator，启动动画即可
 *
 * 5)CicularReveal
 *   用ViewAnimationUtils.createCircularReveal()生成animator，然后启动动画即可
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:FrameAnimationActivity
 * <br/>Date:Mar，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.animation.advanced_property_animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xottys.userinterface.animation.R;

import java.text.DecimalFormat;

public class AdvancedPropertyAnimationActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "AdvancedPropertyAnimationActivity";
    private static final String[] LIST_STRINGS_EN = new String[]{
            "One",
            "Two",
            "Three Animation",
            "Four",
            "Five",
            "Six"
    };
    private static final String[] LIST_STRINGS_FR = new String[]{
            "Un",
            "Deux",
            "Trois",
            "Quatre",
            "Le Five",
            "Six"
    };
    ObjectAnimator pathAnimator;
    ImageView iv, iv1, iv2;
    DecimalFormat format = new DecimalFormat("#.00");
    ValueAnimator anim;
    ListView mEnglishList;
    ListView mFrenchList;
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
    private RelativeLayout animationContent = null;
    private LinearLayout alipayContent = null, animationLayout, animationButton;
    private int screenWidth;
    private int screenHeight;
    private Interpolator accelerator = new AccelerateInterpolator();
    private Interpolator decelerator = new DecelerateInterpolator();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancedpropertyanimation);
        
        animationStartFlag = false;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
      
        rootview = findViewById(R.id.menuframe);
        tv = findViewById(R.id.title);
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
        //点击主按钮
        if (v == mMenuButton) {
            //按钮动画扇形展开
            if (!mIsMenuOpen) {
                mIsMenuOpen = true;
                doAnimateOpen(mItemButton1, 0, 5, 600);
                doAnimateOpen(mItemButton2, 1, 5, 600);
                doAnimateOpen(mItemButton3, 2, 5, 600);
                doAnimateOpen(mItemButton4, 3, 5, 600);
                doAnimateOpen(mItemButton5, 4, 5, 600);
            //按钮动画扇形关闭
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
                            //停止动画
                            if (contentView!=null)
                            contentView.findViewById(R.id.stop).performClick();
                         
                        animationLayout.removeView(animationButton);
                    }
                    tv.setText("Property Animation Demo\nPress Button on bottom right To start");
                    tv.setTextSize(15);
                    animationLayout.removeView(animationContent);
                }
                clickedItemButtonID = 0;
            }
        }
        //点击子按钮
        else {
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp1.setMargins(0, 100, 0, 0);//左上右下
            //内容包括起停按钮和动画两部分
            if(contentView!=null) rootview.removeView(contentView);
            contentView = getLayoutInflater().inflate(R.layout.property_animation2_layout, null);
            contentView.setLayoutParams(lp1);
            rootview.addView(contentView);
            //内容整体
            animationLayout = contentView.findViewById(R.id.llay);
            //起停按钮
            animationButton = contentView.findViewById(R.id.btnzone);
            //具体动画
            animationContent = contentView.findViewById(R.id.content);
            animationContent.removeAllViews();

            //将上次点击的子按钮缩小
            if (clickedItemButtonID != 0) {
                doZoomButtonAnimation(clickedItemButtonID, false);
                if (animationStartFlag)
                    contentView.findViewById(R.id.stop).performClick();
            }
            
            ViewGroup.LayoutParams lp4 = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams lp6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            RelativeLayout.LayoutParams lp7 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            switch (v.getId()) {
                case R.id.item1:
                    tv.setText("ValueAnimator Demo");
                    //动画1：正弦移动的小球
                    animView = new PointAnimView(this);
                    animView.setLayoutParams(lp4);
                    animView.setInterpolatorType(8);
                    animationContent.addView(animView);

                    //动画2：成功失败示意图
                    alipaySuccessView = new AlipaySuccessView(this);
                    alipayFailureView = new AlipayFailureView(this);
                    LinearLayout.LayoutParams lp5 = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp5.setMargins(100, 20, 100, 20);
                    alipaySuccessView.setLayoutParams(lp5);
                    alipayFailureView.setLayoutParams(lp5);

                    alipayContent = new LinearLayout(this);
                    LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    alipayContent.setLayoutParams(lp2);
                    alipayContent.setOrientation(LinearLayout.HORIZONTAL);
                    alipayContent.addView(alipaySuccessView);
                    alipayContent.addView(alipayFailureView);
                    //设置控件的位置
                    lp6.setMargins(0, 300, 0, 0);//左上右下
                    alipayContent.setLayoutParams(lp6);
                    animationContent.addView(alipayContent);

                    //动画3：变化的数字文本
                    textView = new TextView(this);
                    textView.setText("0");
                    textView.setTextAppearance(R.style.myTextStyle);
                    //设置控件的位置
                    lp7.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                    lp7.setMargins(0, 900, 0, 0);
                    textView.setLayoutParams(lp7);
                    animationContent.addView(textView);

                    //动画结束时设置其最终呈现的颜色
                    alipayFailureView.addCircleAnimatorEndListner(()->
                            alipayFailureView.setPaintColor(getResources().getColor(R.color.cpb_red,null))
                    );
                    alipaySuccessView.addCircleAnimatorEndListner(()->alipaySuccessView.setPaintColor(Color.GREEN)
                    );

                    clickedItemButtonID = 1;
                    break;
                case R.id.item2:
                    clickedItemButtonID = 2;
                    tv.setText("PropertyValueHolder Demo");

                    pvHolderKFrameView = new PVHolderKFrameView(this);
                    pvHolderKFrameView.setLayoutParams(lp4);
                    animationContent.addView(pvHolderKFrameView);
                    break;
                case R.id.item3:
                    clickedItemButtonID = 3;
                    animationLayout.removeView(animationButton);
                    tv.setText("ObjectAnimator Demo");
                    //整个Listview作为动画对象
                    View roatatingListView = getLayoutInflater().inflate(R.layout.rotating_list, null);
                    mEnglishList = roatatingListView.findViewById(R.id.list_en);
                    mFrenchList =  roatatingListView.findViewById(R.id.list_fr);

                    final ArrayAdapter<String> adapterEn = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, LIST_STRINGS_EN);
                    final ArrayAdapter<String> adapterFr = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, LIST_STRINGS_FR);

                    mEnglishList.setAdapter(adapterEn);
                    mFrenchList.setAdapter(adapterFr);

                    //设置Listview的旋转角度
                    mFrenchList.setRotationY(-90f);
                    //将Listview加入内容中
                    animationContent.addView(roatatingListView);

                    //启动动画按钮监听器
                    Button starter = findViewById(R.id.button);
                    starter.setOnClickListener(vv->flipit());
                    break;
                case R.id.item4:
                    clickedItemButtonID = 4;
                    tv.setText("Path Animation Demo");
                    //定义动画对象Image的内容
                    iv = new ImageView(this);
                    LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(
                            100, 100);
                    lp3.setMargins(10, 10, 0, 0);//左上右下
                    iv.setLayoutParams(lp3);
                    iv.setBackgroundColor(Color.RED);
                    //添加Image到动画内容中
                    animationContent.addView(iv);
                    //添加自定义View：Path曲线到动画内容中
                    PathView pathView = new PathView(this);
                    animationContent.addView(pathView);
                    break;
                case R.id.item5:
                    clickedItemButtonID = 5;
                    tv.setText("CircularReveal Demo\nClick Image to Start Animation");
                    //删除起停按钮视图
                    animationLayout.removeView(animationButton);

                    //定义用来演示的ImageView
                    lp7.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                    View circularRevealView = getLayoutInflater().inflate(R.layout.circularreveal_animator, null);
                    circularRevealView.setLayoutParams(lp7);

                    iv1 = circularRevealView.findViewById(R.id.oval);
                    iv2 = circularRevealView.findViewById(R.id.rect);
                    //添加ImageView到动画内容中
                    animationContent.addView(circularRevealView);

                    //点击图片产生环形截图动画
                    iv1.setOnClickListener(vv-> {
                            Animator animator = ViewAnimationUtils.createCircularReveal(iv1, iv1.getWidth() / 2, iv1.getHeight() / 2, iv1.getWidth(), 0);
                            animator.setDuration(2000);
                            animator.start();
                    });
                    iv2.setOnClickListener( vv-> {
                            Animator animator= ViewAnimationUtils.createCircularReveal(iv2, 0, 0, 0, (float) Math.hypot(iv2.getWidth(), iv2.getHeight()));
                            animator.setDuration(2000);
                            animator.start();
                    });
                    break;
            }
            //启动动画
            if (clickedItemButtonID != 5 && clickedItemButtonID != 3) {
                contentView.findViewById(R.id.start).setOnClickListener(vv-> {
                        animationStartFlag = true;
                        switch (clickedItemButtonID) {
                            case 1:
                                //启动ValueAnimator定义的动画
                                animView.startAnimation();
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
                });
                //停止动画
                contentView.findViewById(R.id.stop).setOnClickListener(view-> {

                        switch (clickedItemButtonID) {
                            case 1:
                                animView.stopAnimation();
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

                });
            }

            //本次点击按钮放大
            if (clickedItemButtonID != 0) {
                doZoomButtonAnimation(clickedItemButtonID, true);
            }
        }
    }

    /**
     * 打开菜单的动画，子按钮从中心扇形散开
     * @param view   执行动画的view
     * @param index  view在动画序列中的顺序,从0开始
     * @param total  动画序列的个数
     * @param radius 动画半径
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
     * 关闭菜单的动画，子按钮回收到中心
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

        //子按钮收回动画完成后需Gone，否则原位置会响应点击事件
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setButtnGone();
            }
        });

    }

    //缩放子按钮，isOut：true--放大，false--缩小
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
        //设置动画时长为300ms并启动动画
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

    //通过ObjectAnimator和Path组合即可提供Path动画
    private void startPathAnim(View view) {
        //定义二次曲线
        Path path = new Path();
        path.moveTo(10, 10);
        path.quadTo(screenWidth - 100, 400, screenWidth - 1200, screenHeight - 700);
        //用带path参数的构造器构建ObjectAnimator
        pathAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);

        pathAnimator.setDuration(2000);
        pathAnimator.setRepeatCount(1);
        pathAnimator.setRepeatMode(ValueAnimator.REVERSE);
        pathAnimator.start();
    }

   //ObjectAnimator旋转动画和Visibility的变化组合，实现整个ListView立体翻转显示效果
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
        //定义可见ListView翻转0～90度动画
        final ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
        visToInvis.setDuration(500);
        visToInvis.setInterpolator(accelerator);
        //定义不可见ListView翻转-90～0度动画
        final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY",
                -90f, 0f);
        invisToVis.setDuration(500);
        invisToVis.setInterpolator(decelerator);

        //启动可见Listview动画
        visToInvis.start();

        //可见Listview动画结束时启动不可见Listview动画
        //AnimatorListenerAdapter可以自由选取动画过程的任意部分环节来处理
        visToInvis.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator anim) {
                visibleList.setVisibility(View.GONE);
                invisToVis.start();
                invisibleList.setVisibility(View.VISIBLE);
            }
        });
    }

    //子按钮收回到中心后需Gone，否则原位置会响应点击事件
    public void setButtnGone() {
        mItemButton1.setVisibility(View.GONE);
        mItemButton2.setVisibility(View.GONE);
        mItemButton3.setVisibility(View.GONE);
        mItemButton4.setVisibility(View.GONE);
        mItemButton5.setVisibility(View.GONE);
    }

    //数字文本递增动画，ValueAnimator的基本用法
    public void startNumberAnimator(final TextView view) {
        anim = ValueAnimator.ofFloat(0, 2000);
        anim.setDuration(5000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        //动画具体内容由此监听器提供

        anim.addUpdateListener(valueAnimator-> {
                float value = (float) valueAnimator.getAnimatedValue();
                view.setText(format.format(value));

        });
        /*只有一个方法的监听器可以用lambada方式替换，原方式如下：
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                view.setText(format.format(value));
            }
        });*/
        anim.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (animationStartFlag)
            contentView.findViewById(R.id.stop).performClick();
    }
}
