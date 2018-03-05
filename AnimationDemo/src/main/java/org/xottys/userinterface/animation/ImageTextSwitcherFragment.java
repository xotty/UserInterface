package org.xottys.userinterface.animation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;


public class ImageTextSwitcherFragment extends Fragment {
    private int[]        imageIDs;
    private int          imageCount,currenImageNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIDs = new int[] { R.drawable.bird1, R.drawable.cat,
                R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;
        currenImageNumber=0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_imagetextswitcher, null);
        final ImageSwitcher mImageSwitcher=view.findViewById(R.id.imageswitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                //创建ImageView对象
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);//设置图片居中显示
                //imageView.setLayoutParams(new ImageSwitcher(getApplicationContext().))
                return imageView; }
        });
        mImageSwitcher.setImageResource(imageIDs[0]);

        final TextSwitcher mTextSwitcher=view.findViewById(R.id.textswitcher);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                //创建ImageView对象
                TextView textView = new TextView(getActivity());
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(80);
                return textView;
            }
        });
        mTextSwitcher.setText("1");
        Button btn_next = (Button) view.findViewById(R.id.next);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (currenImageNumber < imageCount - 1) {
                currenImageNumber++;
               // 切换视图
                mImageSwitcher.setImageResource(imageIDs[currenImageNumber]);
                mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(),

                            android.R.anim.slide_in_left));
                mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),

                            android.R.anim.slide_out_right));
                mTextSwitcher.setText(currenImageNumber+1+"");
            }
            }
        });

        Button btn_prev = (Button) view.findViewById(R.id.prev);
        btn_prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (currenImageNumber >0) {
                    currenImageNumber--;
                    // 切换视图
                    mImageSwitcher.setImageResource(imageIDs[currenImageNumber]);
                    mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(getActivity(),

                            R.anim.slide_in_right));
                    mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getActivity(),

                            R.anim.slide_out_left));
                    mTextSwitcher.setText(currenImageNumber+1+"");
                }

            }
        });

        return view;
    }

}