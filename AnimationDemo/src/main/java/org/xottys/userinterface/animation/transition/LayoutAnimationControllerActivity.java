package org.xottys.userinterface.animation.transition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.xottys.userinterface.animation.R;

public class LayoutAnimationControllerActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutanimationcontroller);
    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.List_Cascade:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimation1.class);
                break;
            case R.id.Reverse_Order:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimation2.class);
                break;
            case R.id.Wave_Scale:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimation3.class);
                break;
            case R.id.Nested_Animation:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimation4.class);
                break;
            case R.id.Randomize:
                intent = new Intent(LayoutAnimationControllerActivity.this
                        , LayoutAnimation5.class);
                break;
        }
        startActivity(intent);
    }
}
