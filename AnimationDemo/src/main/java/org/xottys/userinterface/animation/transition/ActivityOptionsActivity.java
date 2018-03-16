
package org.xottys.userinterface.animation.transition;

import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xottys.userinterface.animation.R;

import java.util.List;
import java.util.Map;


public class ActivityOptionsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int[] DRAWABLES = {
            R.drawable.ball,
            R.drawable.ducky,
            R.drawable.mug,
            R.drawable.scissors,
    };
    public static final int[] IDS = {
            R.id.ball,
            R.id.ducky,
            R.id.mug,
            R.id.scissors,
    };
    public static final String[] NAMES = {
            "ball",
            "ducky",
            "mug",
            "scissors",
    };
    private static final String TAG = "ActivityTransition";
    private static final String KEY_ID = "ViewTransitionValues:id";
    ActivityOptions opts;
    Intent intent;
    private ImageView mHero;

    public static int getIdForKey(String id) {
        return IDS[getIndexForKey(id)];
    }

    public static int getDrawableIdForKey(String id) {
        return DRAWABLES[getIndexForKey(id)];
    }

    public static int getIndexForKey(String id) {
        for (int i = 0; i < NAMES.length; i++) {
            String name = NAMES[i];
            if (name.equals(id)) {
                return i;
            }
        }
        return 2;
    }

    private static @ColorInt
    int randomColor() {
        int red = (int) (Math.random() * 128);
        int green = (int) (Math.random() * 128);
        int blue = (int) (Math.random() * 128);
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int color = randomColor();
        getWindow().setBackgroundDrawable(new ColorDrawable(color));
        setContentView(R.layout.optionsactivity);
        Button btn = findViewById(R.id.make_custom);
        int tColor = getComplementaryColor(btn.getDrawingCacheBackgroundColor());
        btn.setTextColor(tColor);
        ((Button) findViewById(R.id.make_basic)).setTextColor(tColor);
        ((Button) findViewById(R.id.make_clip_reveal)).setTextColor(tColor);
        ((Button) findViewById(R.id.make_scale_up)).setTextColor(tColor);
        ((Button) findViewById(R.id.make_thumbnail_scaleup)).setTextColor(tColor);
        ((Button) findViewById(R.id.no_animation)).setTextColor(tColor);
        TextView tv = findViewById(R.id.make_scene_transition);
        tv.setTextColor(getComplementaryColor(color));
        setupHero();
    }

    private void setupHero() {
        String name = getIntent().getStringExtra(KEY_ID);
        mHero = null;
        if (name != null) {
            mHero = findViewById(getIdForKey(name));
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names,
                                                Map<String, View> sharedElements) {
                    sharedElements.put("hero", mHero);
                }
            });
        }
        intent = new Intent(ActivityOptionsActivity.this, ActivityOptionsDetailsActivity.class);
    }

    public void clicked(View v) {
        mHero = (ImageView) v;
        intent.putExtra(KEY_ID, v.getTransitionName());
        ActivityOptions activityOptions
                = ActivityOptions.makeSceneTransitionAnimation(this, mHero, "hero");
        startActivity(intent, activityOptions.toBundle());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.make_custom:
                opts = ActivityOptions.makeCustomAnimation(ActivityOptionsActivity.this,
                        R.anim.zoom_enter, R.anim.zoom_enter);
                Log.i(TAG, "Starting zoom-in animation(makeCustomAnimation)...");
                break;
            case R.id.make_scale_up:
                opts = ActivityOptions.makeScaleUpAnimation(
                        v, 0, 0, v.getWidth(), v.getHeight());
                Log.i(TAG, "Starting scale-up animation(makeScaleUpAnimation)...");
                break;
            case R.id.make_thumbnail_scaleup:
                Log.i(TAG, "Starting thumbnail-zoom animation...");
                // Create a thumbnail animation.  We are going to build our thumbnail
                // just from the view that was pressed.  We make sure the view is
                // not selected, because by the time the animation starts we will
                // have finished with the selection of the tap.
                v.setDrawingCacheEnabled(true);
                v.setPressed(false);
                v.refreshDrawableState();
                Bitmap bm = v.getDrawingCache();
                Canvas c = new Canvas(bm);
                c.drawARGB(255, 255, 0, 0);
                opts = ActivityOptions.makeThumbnailScaleUpAnimation(
                        v, bm, 0, 0);
                break;
            case R.id.make_clip_reveal:
                Log.i(TAG, "Starting clip reveal animation(makeClipRevealAnimation)...");

                opts = ActivityOptions.makeClipRevealAnimation(v, 0, 0, v.getWidth(), v.getHeight());
                break;
            case R.id.make_basic:
                Log.i(TAG, "Starting basic animation(makeBasic)...");

                opts = ActivityOptions.makeBasic();
                break;
            case R.id.no_animation:
                Log.i(TAG, "Starting no animation transition...");
                break;

        }

        if (v.getId() == R.id.no_animation) {
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            startActivity(intent, opts.toBundle());
            if (v.getId() == R.id.make_thumbnail_scaleup) {
                v.setDrawingCacheEnabled(false);
            }
        }
    }

    private int getComplementaryColor(@ColorInt int mColor) {
        int red = 255 - Color.red(mColor);
        int green = 255 - Color.green(mColor);
        int blue = 255 - Color.blue(mColor);
        return 0xFF000000 | (red << 16) | (green << 8) | blue;
    }
}
