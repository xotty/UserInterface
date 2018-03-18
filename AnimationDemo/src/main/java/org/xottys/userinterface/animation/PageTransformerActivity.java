package org.xottys.userinterface.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import org.xottys.userinterface.animation.transition.LayoutTransitionActivity;

import java.util.List;


public class PageTransformerActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
   static private int[]  imageIDs;
    private int          imageCount;
    private static final String TAG = "VPAnimActivity";
    private List<String> pics;
    private Context mContext;
    enum BtnTransformer{btn_depth,btn_cube,btn_zoom} ;
    BtnTransformer btn_transformer=BtnTransformer.btn_depth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.activity_pagetransformer);
//        initDatas();
         imageIDs= new int[] { R.drawable.bird1, R.drawable.cat,
                 R.drawable.lion,R.drawable.dog,R.drawable.bird2 };;
        imageCount = imageIDs.length;
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_vp);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.btn_page_transformer);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.depth:
                        mViewPager.setPageTransformer(true, new DepthPageTransformer());

                        break;
                    case R.id.mycube:
                        mViewPager.setPageTransformer(true, new MyCubeTransformer(90));

                        break;
                    case R.id.zoom_out:
                        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

                        break;
                }
            }
        });


    }

//    private void initDatas() {
//        String data = Tools.readStrFromAssets("pics.data", mContext);
//        pics = new Gson().fromJson(data, new TypeToken<List<String>>() {
//        }.getType());
//        pics = pics.subList(0, 8);
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_vpanim, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.file_path.
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.anim1:
//                mViewPager.setPageTransformer(true, new DepthPageTransformer());
//                break;
//            case R.id.anim2:
//                mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//                break;
//            case R.id.anim3:
//                mViewPager.setPageTransformer(true, new MyCubeTransformer(90));
//                break;
//            default:
//                break;
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         *
         * @param sectionNumber
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.flipper_cell, container, false);
            ImageView section_label = (ImageView) rootView.findViewById(R.id.img);
           int number = getArguments().getInt(ARG_SECTION_NUMBER);
            section_label.setImageResource(imageIDs[number]);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return imageCount;
        }
    }
}
