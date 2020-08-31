/**
 * 本例在CARDVIEW中演示了V7 Support中的一个新控件CardView的用法，使用要点：
 * 1)设置卡片的圆角半径和阴影属性
 * 2)设置卡片的内容，此时通常用RelativeLayout布局
 * 3)设置相关元素的点击事件
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:MatrialWidgetsFragment
 * <br/>Date:Oct，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.userinterface.AdvancedViewGroup;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.xottys.userinterface.R;

public class CardViewFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private ImageView img_main_card2_share, img_main_card2_bookmark, img_main_card2_favorite;
    private boolean isBookmarkClicked, isFavoriteClicked, isBookmark41Clicked, isBookmark42Clicked, isFavorite41Clicked, isFavorite42Clicked;
    private LinearLayout ll_card_main3_rate;
    private Button btn_card_main1_action1, btn_card_main1_action2;
    private ImageView img_main_card_1, img_main_card_2, img_card_main_3, img_main_card_41, img_main_card_42,
            img_main_card41_favorite, img_main_card42_favorite, img_main_card41_bookmark, img_main_card42_bookmark,
            img_main_card41_share, img_main_card42_share;
    private CardView card_main_1_1, card_main_1_2, card_main_1_3, card_main_1_4_1, card_main_1_4_2;
    private AlphaAnimation alphaAnimation, alphaAnimationShowIcon;
    private CardView card_ad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NestedScrollView nestedScrollView = (NestedScrollView) inflater.inflate(R.layout.fragment_cards, container, false);

        btn_card_main1_action1 = (Button) nestedScrollView.findViewById(R.id.btn_card_main1_action1);
        btn_card_main1_action2 = (Button) nestedScrollView.findViewById(R.id.btn_card_main1_action2);
        img_main_card2_share = (ImageView) nestedScrollView.findViewById(R.id.img_main_card2_share);
        img_main_card2_bookmark = (ImageView) nestedScrollView.findViewById(R.id.img_main_card2_bookmark);
        img_main_card2_favorite = (ImageView) nestedScrollView.findViewById(R.id.img_main_card2_favorite);
        ll_card_main3_rate = (LinearLayout) nestedScrollView.findViewById(R.id.ll_card_main3_rate);

        img_main_card_1 = (ImageView) nestedScrollView.findViewById(R.id.img_main_card_1);
        img_main_card_2 = (ImageView) nestedScrollView.findViewById(R.id.img_main_card_2);
        img_card_main_3 = (ImageView) nestedScrollView.findViewById(R.id.img_card_main_3);
        img_main_card_41 = (ImageView) nestedScrollView.findViewById(R.id.img_main_card_41);
        img_main_card_42 = (ImageView) nestedScrollView.findViewById(R.id.img_main_card_42);

        img_main_card41_favorite = (ImageView) nestedScrollView.findViewById(R.id.img_main_card41_favorite);
        img_main_card42_favorite = (ImageView) nestedScrollView.findViewById(R.id.img_main_card42_favorite);
        img_main_card41_bookmark = (ImageView) nestedScrollView.findViewById(R.id.img_main_card41_bookmark);
        img_main_card42_bookmark = (ImageView) nestedScrollView.findViewById(R.id.img_main_card42_bookmark);
        img_main_card41_share = (ImageView) nestedScrollView.findViewById(R.id.img_main_card41_share);
        img_main_card42_share = (ImageView) nestedScrollView.findViewById(R.id.img_main_card42_share);

        card_main_1_1 = (CardView) nestedScrollView.findViewById(R.id.card_main_1_1);
        card_main_1_2 = (CardView) nestedScrollView.findViewById(R.id.card_main_1_2);
        card_main_1_3 = (CardView) nestedScrollView.findViewById(R.id.card_main_1_3);
        card_main_1_4_1 = (CardView) nestedScrollView.findViewById(R.id.card_main_1_4_1);
        card_main_1_4_2 = (CardView) nestedScrollView.findViewById(R.id.card_main_1_4_2);

        return nestedScrollView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_card_main1_action1.setOnClickListener(this);
        btn_card_main1_action2.setOnClickListener(this);
        img_main_card2_bookmark.setOnClickListener(this);
        img_main_card2_favorite.setOnClickListener(this);
        img_main_card2_share.setOnClickListener(this);
        ll_card_main3_rate.setOnClickListener(this);

        img_main_card41_favorite.setOnClickListener(this);
        img_main_card42_favorite.setOnClickListener(this);
        img_main_card41_bookmark.setOnClickListener(this);
        img_main_card42_bookmark.setOnClickListener(this);
        img_main_card41_share.setOnClickListener(this);
        img_main_card42_share.setOnClickListener(this);

        card_main_1_1.setOnClickListener(this);
        card_main_1_2.setOnClickListener(this);
        card_main_1_3.setOnClickListener(this);
        card_main_1_4_1.setOnClickListener(this);
        card_main_1_4_2.setOnClickListener(this);


        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(700);
        img_main_card_1.startAnimation(alphaAnimation);
        img_main_card_2.startAnimation(alphaAnimation);

        alphaAnimationShowIcon = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimationShowIcon.setDuration(500);

    }

    //点击卡片中各种元素的响应
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_card_main1_action1:
                Snackbar.make(view, getString(R.string.main_flat_button_1_clicked), Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.btn_card_main1_action2:
                Snackbar.make(view, getString(R.string.main_flat_button_2_clicked), Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.img_main_card2_bookmark:
                if (!isBookmarkClicked) {
                    img_main_card2_bookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    img_main_card2_bookmark.startAnimation(alphaAnimationShowIcon);
                    isBookmarkClicked = true;
                } else {
                    img_main_card2_bookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    img_main_card2_bookmark.startAnimation(alphaAnimationShowIcon);
                    isBookmarkClicked = false;
                }
                break;

            case R.id.img_main_card2_favorite:
                if (!isFavoriteClicked) {
                    img_main_card2_favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    img_main_card2_favorite.startAnimation(alphaAnimationShowIcon);
                    img_main_card2_favorite.startAnimation(alphaAnimationShowIcon);
                    isFavoriteClicked = true;
                } else {
                    img_main_card2_favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    img_main_card2_favorite.startAnimation(alphaAnimationShowIcon);
                    img_main_card2_favorite.startAnimation(alphaAnimationShowIcon);
                    isFavoriteClicked = false;
                }
                break;

            case R.id.img_main_card41_favorite:
                if (!isFavorite41Clicked) {
                    img_main_card41_favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    img_main_card41_favorite.startAnimation(alphaAnimationShowIcon);
                    isFavorite41Clicked = true;
                } else {
                    img_main_card41_favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    img_main_card41_favorite.startAnimation(alphaAnimationShowIcon);
                    isFavorite41Clicked = false;
                }
                break;

            case R.id.img_main_card42_favorite:
                if (!isFavorite42Clicked) {
                    img_main_card42_favorite.setImageResource(R.drawable.ic_favorite_black_24dp);
                    img_main_card42_favorite.startAnimation(alphaAnimationShowIcon);
                    isFavorite42Clicked = true;
                } else {
                    img_main_card42_favorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    img_main_card42_favorite.startAnimation(alphaAnimationShowIcon);
                    isFavorite42Clicked = false;
                }
                break;

            case R.id.img_main_card41_bookmark:
                if (!isBookmark41Clicked) {
                    img_main_card41_bookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    img_main_card41_bookmark.startAnimation(alphaAnimationShowIcon);
                    isBookmark41Clicked = true;
                } else {
                    img_main_card41_bookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    img_main_card41_bookmark.startAnimation(alphaAnimationShowIcon);
                    isBookmark41Clicked = false;
                }
                break;

            case R.id.img_main_card42_bookmark:
                if (!isBookmark42Clicked) {
                    img_main_card42_bookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    img_main_card42_bookmark.startAnimation(alphaAnimationShowIcon);
                    isBookmark42Clicked = true;
                } else {
                    img_main_card42_bookmark.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    img_main_card42_bookmark.startAnimation(alphaAnimationShowIcon);
                    isBookmark42Clicked = false;
                }
                break;

            case R.id.img_main_card2_share:
                break;

            case R.id.img_main_card41_share:
                break;

            case R.id.img_main_card42_share:
                break;

            case R.id.ll_card_main3_rate:
                break;

            case R.id.card_main_1_1:
                break;

            case R.id.card_main_1_2:
                break;

            case R.id.card_main_1_3:
                break;

            case R.id.card_main_1_4_1:
                break;

            case R.id.card_main_1_4_2:
                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator upAnim = ObjectAnimator.ofFloat(view, "translationZ", 16);
                upAnim.setDuration(150);
                upAnim.setInterpolator(new DecelerateInterpolator());
                upAnim.start();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                ObjectAnimator downAnim = ObjectAnimator.ofFloat(view, "translationZ", 0);
                downAnim.setDuration(150);
                downAnim.setInterpolator(new AccelerateInterpolator());
                downAnim.start();
                break;
        }
        return false;
    }


}
