package org.xottys.userinterface.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.widget.ImageView;

public class PhysicsAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physics_animation);
        final ImageView image = findViewById(R.id.image);

        (findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener() {
            float velocity = 500f;
            @Override
            public void onClick(View v) {
                FlingAnimation flingAnimation = new FlingAnimation(image, DynamicAnimation.X);
                flingAnimation.setStartVelocity(velocity);
                flingAnimation.setFriction(0.2f);
                flingAnimation.start();
                velocity = -velocity;
            }
        });

        (findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener() {
            float velocity = 500f;
            @Override
            public void onClick(View v) {
                SpringAnimation springAnimation = new SpringAnimation(image, DynamicAnimation.Y);
                springAnimation.setStartValue(velocity);
                SpringForce springForce = new SpringForce();
                springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
                springForce.setStiffness(SpringForce.STIFFNESS_LOW);
                springForce.setFinalPosition(image.getPivotY()+400f);
                springAnimation.setSpring(springForce);
                springAnimation.start();
            }
        });

    }
}
