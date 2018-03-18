package org.xottys.userinterface.animation.transition;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import org.xottys.userinterface.animation.R;

public class SharedElementFragment extends Fragment {


    public static SharedElementFragment newInstance() {

        Bundle args = new Bundle();

        SharedElementFragment fragment = new SharedElementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sharedelement_fragment1, container, false);

        final ImageView squareBlue =  view.findViewById(R.id.square_blue);
        DrawableCompat.setTint(squareBlue.getDrawable(), Color.BLUE);

        view.findViewById(R.id.sample2_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNextFragment(squareBlue, false);
            }
        });

        view.findViewById(R.id.sample2_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNextFragment( squareBlue, true);
            }
        });

        return view;
    }

    private void addNextFragment( ImageView squareBlue, boolean overlap) {
        SharedElementDetailsFragment sharedElementDetailsFragment = SharedElementDetailsFragment.newInstance();

        Slide slideTransition = new Slide(Gravity.END);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));

        sharedElementDetailsFragment.setEnterTransition(slideTransition);
        sharedElementDetailsFragment.setAllowEnterTransitionOverlap(overlap);
        sharedElementDetailsFragment.setAllowReturnTransitionOverlap(overlap);
        sharedElementDetailsFragment.setSharedElementEnterTransition(changeBoundsTransition);

        getFragmentManager().beginTransaction()
                .replace(R.id.sample2_content, sharedElementDetailsFragment)
                .addToBackStack(null)
                .addSharedElement(squareBlue, getString(R.string.square_blue_name))
                .commit();
    }

}
