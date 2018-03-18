package org.xottys.userinterface.animation.transition;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import org.xottys.userinterface.animation.R;
public class SharedElementDetailsFragment extends Fragment {

    public static SharedElementDetailsFragment newInstance() {
        Bundle args = new Bundle();
        SharedElementDetailsFragment fragment = new SharedElementDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sharedelement_fragment2, container, false);

        ImageView squareBlue =  view.findViewById(R.id.square_blue);
        DrawableCompat.setTint(squareBlue.getDrawable(), Color.BLUE);

        return view;
    }

}
