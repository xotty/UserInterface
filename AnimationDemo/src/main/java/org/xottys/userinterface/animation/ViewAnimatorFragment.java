package org.xottys.userinterface.animation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewAnimator;


public class ViewAnimatorFragment extends Fragment {
    private int[]        imageIDs;
    private int          imageCount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIDs = new int[] { R.drawable.bird1, R.drawable.cat,
                R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_viewanimator, null);
        final ViewAnimator mViewAnimator =  view.findViewById(R.id.view_animator);

        for (int i = 0; i < imageCount; i++) {
            ImageView imageView = new ImageView(getContext()); // create a new object  for ImageView
            imageView.setImageResource(imageIDs[i]); // set image resource for ImageView
            mViewAnimator.addView(imageView); // add child view in ViewAnimator
        }


        mViewAnimator.setAnimateFirstView(true);


        Button btn_next =  view.findViewById(R.id.next);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // Declare in and out animations and load them using AnimationUtils class
                Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
                Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);

                // set the animation type to ViewAnimator
                mViewAnimator.setInAnimation(in);
                mViewAnimator.setOutAnimation(out);

                mViewAnimator.showNext();
            }
        });

        Button btn_prev =  view.findViewById(R.id.prev);
        btn_prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // Declare in and out animations and load them using AnimationUtils class
                Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
                Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);

                // set the animation type to ViewAnimator
                mViewAnimator.setInAnimation(in);
                mViewAnimator.setOutAnimation(out);

                mViewAnimator.showPrevious();

            }
        });
        return view;
    }
}