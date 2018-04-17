/**
 * 本例演示GridLayoutAnimationController的用法：
 * 1）在xml中用标签<gridLayoutAnimation>定义的GridLayoutAnimationController
 * 2) 加载到GridView中
 * xml方式，直接在GridView的xml中用属性android:layoutAnimation引入上述GridLayoutAnimationController即可
 * 代码方式加载的方法：
 *  GridLayoutAnimationController mController = (GridLayoutAnimationController)AnimationUtils.
 *                                              loadLayoutAnimation(this,R.anim.layout_anim);
 *  gridView.setLayoutAnimation(mController)
 * 3)GridLayoutAnimationController也可以用代码生成
 *  GridLayoutAnimationController mController = new GridLayoutAnimationController(animation);
 *  mController.setColumnDelay(0.5f);
 *  mController.setRowDelay(0.5f);
 *  mController.setDirection(GridLayoutAnimationController.DIRECTION_LEFT_TO_RIGHT|GridLayoutAnimationController.DIRECTION_TOP_TO_BOTTOM);
 *  mController.setDirectionPriority(GridLayoutAnimationController.PRIORITY_ROW);
 *  mController.setAnimation(AnimationUtils.loadAnimation(this,R.anim.grid_animation));
 *  mController.setInterpolator(new CycleInterpolator(1.0f));
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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.xottys.userinterface.animation.R;

import java.util.List;

public class LayoutAnimationControllerActivity4 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadApps();
        setContentView(R.layout.activity_layoutanimationcontroller4);
        GridView grid =  findViewById(R.id.grid);

        /*也可以用代码加载GridLayoutAnimationController
        GridLayoutAnimationController mController = (GridLayoutAnimationController)AnimationUtils.loadLayoutAnimation(this,R.anim.layout_wave_scale);
        grid.setLayoutAnimation(mController);*/

        grid.setAdapter(new AppsAdapter());
    }

    private List<ResolveInfo> mApps;

    //将系统中的App应用放入mApps数组
    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    public class AppsAdapter extends BaseAdapter {
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView img = new ImageView(LayoutAnimationControllerActivity4.this);
            ResolveInfo info = mApps.get(position % mApps.size());
            img.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            final int w = (int) (36 * getResources().getDisplayMetrics().density + 0.5f);
            img.setLayoutParams(new GridView.LayoutParams(w, w));
            return img;
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
