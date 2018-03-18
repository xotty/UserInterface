package org.xottys.userinterface.animation.transition;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;

import org.xottys.userinterface.animation.R;

public class SharedElementActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedelement);
        getWindow().getEnterTransition().setDuration(getResources().getInteger(R.integer.anim_duration_long));

        Slide slideTransition = new Slide(Gravity.START);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        // Create fragment and define some of it transitions
        SharedElementFragment sharedElementFragment = SharedElementFragment.newInstance();
        sharedElementFragment.setReenterTransition(slideTransition);
        sharedElementFragment.setExitTransition(slideTransition);
        sharedElementFragment.setSharedElementEnterTransition(new ChangeBounds());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sample2_content, sharedElementFragment)
                .commit();

       // setupToolbar();
    }


    void setupToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
    }

}
