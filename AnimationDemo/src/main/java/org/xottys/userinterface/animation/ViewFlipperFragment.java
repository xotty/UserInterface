package org.xottys.userinterface.animation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ViewFlipper;


public class ViewFlipperFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {
    private String[] mStrings = {
            "Push up", "Push left", "Cross fade","Hyperspace", "Zoom"};

    private ViewFlipper mFlipper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_viewflipper, null);
        mFlipper =  view.findViewById(R.id.flipper);
        mFlipper.startFlipping();

        Spinner s =  view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, mStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
        
        return view;
    }


    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {

            case 0:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.push_up_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.push_up_out));
                break;
            case 1:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.push_left_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.push_left_out));
                break;
            case 2:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        android.R.anim.fade_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        android.R.anim.fade_out));
                break;
            case 3:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.hyperspace_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.hyperspace_out));
                break;
            default:
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.zoomin));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(),
                        R.anim.zoomout));
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}