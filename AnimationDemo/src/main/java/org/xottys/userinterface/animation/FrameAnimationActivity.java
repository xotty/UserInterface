package org.xottys.userinterface.animation;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class FrameAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);

        ImageView animationImg1 =  findViewById(R.id.animation1);
        animationImg1.setImageResource(R.drawable.frame_anim1);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) animationImg1.getDrawable();
        animationDrawable1.start();

        ImageView animationImg2 =  findViewById(R.id.animation2);
        animationImg2.setImageResource(R.drawable.frame_anim2);
        AnimationDrawable animationDrawable2 = (AnimationDrawable) animationImg2.getDrawable();
        animationDrawable2.start();

        ImageView animationImg3 =  findViewById(R.id.animation3);
        animationImg3.setImageResource(R.drawable.frame_anim3);
        AnimationDrawable animationDrawable3 = (AnimationDrawable) animationImg3.getDrawable();
        animationDrawable3.start();

        ImageView animationImg4 =  findViewById(R.id.animation4);
        animationImg4.setImageResource(R.drawable.frame_anim4);
        AnimationDrawable animationDrawable4 = (AnimationDrawable) animationImg4.getDrawable();
        animationDrawable4.start();

        ImageView animationImg5 =  findViewById(R.id.animation5);
        animationImg5.setImageResource(R.drawable.frame_anim5);
        AnimationDrawable animationDrawable5 = (AnimationDrawable) animationImg5.getDrawable();
        animationDrawable5.start();
    }

}