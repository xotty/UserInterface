package org.xottys.userinterface.animation;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.xottys.userinterface.animation.adapterview_animator.AdapterViewAnimatorActivity;
import org.xottys.userinterface.animation.advanced_property_animation.AdvancedPropertyAnimationActivity;
import org.xottys.userinterface.animation.page_transformer.PageTransformerActivity;
import org.xottys.userinterface.animation.physics.PhysicsAnimationActivity;
import org.xottys.userinterface.animation.transition.TransitionAnimationActivity;
import org.xottys.userinterface.animation.view_animator.ViewAnimatorActivity;


public class MainActivity extends LauncherActivity {

    //定义要跳转的各个Activity的名称
    String[] names = {"Tweened Animation Demo", "Frame Animation Demo", "Basic PropertyAnimation Demo", "Advanced PropertyAnimation Demo",
            "Physics Animation Demo", "Transition Animation Demo","ViewAnimator Demo","AdapterViewAnimator Demo","Anim Drawable Demo","SVG Animation Demo","FragmentTransaction Animation Demo","PageTransformer Demo","3D Animation Demo" };

    //定义各个Activity对应的实现类
    Class<?>[] clazzs = {TweenedAnimationActivity.class, FrameAnimationActivity.class, BasicPropertyAnimationActivity.class, AdvancedPropertyAnimationActivity.class,
           PhysicsAnimationActivity.class, TransitionAnimationActivity.class, ViewAnimatorActivity.class,AdapterViewAnimatorActivity.class,AnimDrawableActivity.class,CustomSVGActivity.class,FragmentTransactionActivity.class,PageTransformerActivity.class, Transition3dActivity.class};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //自定义Adapter以实现不同item显示颜色不同
        final BaseAdapter adapter = new BaseAdapter() {
            //返回有多少个item
            @Override
            public int getCount() {

                return names.length;

            }

            //获取item的数据对象
            @Override
            public Object getItem(int position) {
                return names[position];
            }

            //获取item的对应索引值
            @Override
            public long getItemId(int position) {
                return position;
            }

            //获取每个item对应的布局对象
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listitem_main, parent, false);

                TextView text = (TextView) convertView.findViewById(R.id.tv);
                text.setText(names[position]);
                if (position <= 5)
                    text.setTextColor(getColor(R.color.cpb_blue_dark));
                else if (position <= 7)
                     text.setTextColor(getColor(R.color.sample_green));
                else
                     text.setTextColor(getColor(R.color.sample_yellow));
                return convertView;
            }
        };
        setListAdapter(adapter);

    }

    //将clazzs数组直接放入，系统将按顺序对应listview上的每一行，行点击后将跳转相应Intent的Activity
    @Override
    public Intent intentForPosition(int position) {
        return new Intent(MainActivity.this, clazzs[position]);
    }

}
