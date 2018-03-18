package org.xottys.userinterface.animation.transition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;

import org.xottys.userinterface.animation.R;

public class SharedElementActivity extends AppCompatActivity {

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

        setupToolbar();
    }
    void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


//    private void setupWindowAnimations() {
//        // We are not interested in defining a new Enter Transition. Instead we change default transition duration
//        getWindow().getEnterTransition().setDuration(getResources().getInteger(R.integer.anim_duration_long));
//    }
//
//    private void setupLayout(Sample sample) {
//        // Transition for fragment1
//        Slide slideTransition = new Slide(Gravity.LEFT);
//        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
//        // Create fragment and define some of it transitions
//        SharedElementFragment sharedElementFragment1 = SharedElementFragment.newInstance(sample);
//        sharedElementFragment1.setReenterTransition(slideTransition);
//        sharedElementFragment1.setExitTransition(slideTransition);
//        sharedElementFragment1.setSharedElementEnterTransition(new ChangeBounds());
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.sample2_content, sharedElementFragment1)
//                .commit();
//    }
}
