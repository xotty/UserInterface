package org.xottys.userinterface.animation;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


public class VectorDrawableActivity extends AppCompatActivity {

    private ImageView imgBtn;

    private ImageView iv1;

    private ImageView iv2;

    private ImageView iv3;

    private boolean isSearchBoxChecked = false;

    private boolean isTwitterChecked = false;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vectordrawable);

        iv1 = (ImageView)findViewById(R.id.iv_1);
        iv2 = (ImageView)findViewById(R.id.iv_2);
        iv3 = (ImageView)findViewById(R.id.iv_3);
    }


    public void onArrowClick(View view) {
        Drawable drawable = iv1.getDrawable();
        ((Animatable) drawable).start();
    }

    public void onSearchBoxClick(View view) {
        isSearchBoxChecked = !isSearchBoxChecked;
        final int[] stateSet = {android.R.attr.state_checked * (isSearchBoxChecked ? 1 : -1)};
        iv2.setImageState(stateSet, true);
    }

    public void onTwitterClick(View view) {
        isTwitterChecked = !isTwitterChecked;
        final int[] stateSet = {android.R.attr.state_checked * (isTwitterChecked ? 1 : -1)};
        iv3.setImageState(stateSet, true);
    }
}
