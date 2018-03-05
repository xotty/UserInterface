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
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;


public class ViewSwitcherFragment extends Fragment {
    private int[]        imageIDs;
    private int          imageCount,currentImageNumber;
   private ViewSwitcher mViewSwitcher;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIDs = new int[] { R.drawable.bird1, R.drawable.cat,
                R.drawable.lion,R.drawable.dog,R.drawable.bird2 };
        imageCount = imageIDs.length;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_viewswitcher, null);
        mViewSwitcher =  view.findViewById(R.id.view_switcher);
        mViewSwitcher.setFactory(new myViewFactory(this.getLayoutInflater()));
        currentImageNumber=-1;
        nextView();
        Button btn_next =  view.findViewById(R.id.next);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
              nextView();
            }
        });

        Button btn_prev =  view.findViewById(R.id.prev);
        btn_prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
              previousView();
            }
        });
        return view;
    }

    class myViewFactory implements ViewSwitcher.ViewFactory
    {
        private LayoutInflater inflater;

        public myViewFactory(LayoutInflater inf)
        {
            inflater = inf;
        }

        @Override
        public View makeView()
        {
            // 提供下一个视图的实例
            return inflater.inflate(R.layout.viewswitcher_layout, null);
        }

    }

    private void nextView()
    {
        if (currentImageNumber < imageCount - 1)
        {
            currentImageNumber++;
            // 设置视图切换的动画效果
            mViewSwitcher.setInAnimation(getContext(), android.R.anim.slide_in_left);
            mViewSwitcher.setOutAnimation(getContext(), android.R.anim.slide_out_right);
            // 获取下一个视图的实例
            LinearLayout ll = (LinearLayout) mViewSwitcher.getNextView();
            ImageView img = (ImageView) ll.findViewById(R.id.img);
            img.setImageResource(imageIDs[currentImageNumber]);
            // 切换视图
            mViewSwitcher.showNext();
        }
    }

    private void previousView()
    {
        if (currentImageNumber > 0)
        {
            currentImageNumber--;
            // 设置视图切换的动画效果
            mViewSwitcher.setInAnimation(getContext(), R.anim.slide_in_right);
            mViewSwitcher.setOutAnimation(getContext(), R.anim.slide_out_left);
            // 获取下一个视图的实例
            LinearLayout ll = (LinearLayout) mViewSwitcher.getNextView();
            ImageView img = (ImageView) ll.findViewById(R.id.img);
            img.setImageResource(imageIDs[currentImageNumber]);
            // 切换视图
            mViewSwitcher.showPrevious();

        }
    }
}