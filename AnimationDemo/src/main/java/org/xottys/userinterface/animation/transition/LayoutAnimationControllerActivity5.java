/**
 * 本例演示GridView也可以使用LayoutAnimationController产生动画
 * <p>
 * <br/>Copyright (C), 2017-2018, Google
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutAnimationControllerActivity4
 * <br/>Date:Mar，2018
 *
 * @author Google
 * @version 1.0
 */
package org.xottys.userinterface.animation.transition;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;

import java.util.List;

public class LayoutAnimationControllerActivity5 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadApps();

        setContentView(R.layout.activity_layoutanimationcontroller5);
        GridView grid =  findViewById(R.id.grid);
        grid.setAdapter(new AppsAdapter());

        /*也可以用代码加载LayoutAnimationController
        LayoutAnimationController mController =  AnimationUtils.loadLayoutAnimation(this,R.anim.layout_random_fade);
        grid.setLayoutAnimation(mController);*/
    }

    private List<ResolveInfo> mApps;

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    public class AppsAdapter extends BaseAdapter {
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(LayoutAnimationControllerActivity5.this);

            ResolveInfo info = mApps.get(position % mApps.size());

            i.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
            i.setScaleType(ImageView.ScaleType.FIT_CENTER);
            final int w = (int) (36 * getResources().getDisplayMetrics().density + 0.5f);
            i.setLayoutParams(new GridView.LayoutParams(w, w));
            return i;
        }


        public final int getCount() {
            return Math.min(32, mApps.size());
        }

        public final Object getItem(int position) {
            return mApps.get(position % mApps.size());
        }

        public final long getItemId(int position) {
            return position;
        }
    }
}
