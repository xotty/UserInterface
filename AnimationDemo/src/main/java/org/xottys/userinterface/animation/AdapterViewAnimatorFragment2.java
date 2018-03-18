package org.xottys.userinterface.animation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class AdapterViewAnimatorFragment2 extends Fragment {
    private int[]        imageIDs;
    private int          imageCount;
    private AdapterViewFlipper flipper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIDs = new int[] { R.drawable.bird1, R.drawable.cat,
                R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_adapterviewflipper, null);

        flipper=view.findViewById(R.id.flipper);
        flipper.setInAnimation(getContext(),android.R.animator.fade_in);
        flipper.setOutAnimation(getContext(),android.R.animator.fade_out);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.autoplay:
                         flipper.startFlipping();
                         break;
                    case R.id.prev:
                        flipper.showPrevious();
                        flipper.stopFlipping();
                    break;
                    case R.id.next:
                        flipper.showNext();
                        flipper.stopFlipping();
                    break;
                }

            }
        };
        view.findViewById(R.id.next).setOnClickListener(listener);
        view.findViewById(R.id.prev).setOnClickListener(listener);
        view.findViewById(R.id.autoplay).setOnClickListener(listener);

        BaseAdapter adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return imageCount;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_vpanim, null);
                    ImageView imgview=convertView.findViewById(R.id.section_label);
                    imgview.setImageResource(imageIDs[position]);
                }
                return convertView;
            }
        };
        flipper.setAdapter(adapter);
        return view;
    }

}