package org.xottys.userinterface.animation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.ViewSwitcher;



public class AdapterViewAnimatorFragment1 extends Fragment {
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

        View view=inflater.inflate(R.layout.fragment_stackview, null);
        final StackView mStackView=view.findViewById(R.id.stackview);
        mStackView.setAdapter(new ImageAdapter(getContext()));

        Button btn_next =  view.findViewById(R.id.next);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
              mStackView.showNext();
            }
        });

        Button btn_prev =  view.findViewById(R.id.prev);
        btn_prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                mStackView.showPrevious();
            }
        });
        return view;
    }

    private class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return imageCount;
        }

        @Override
        public Object getItem(int position) {
            return imageIDs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView = new ImageView(mContext);
            mImageView.setImageResource(imageIDs[position]);
            return mImageView;
        }
    }

}