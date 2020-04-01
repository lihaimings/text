package com.haiming.myapplication.incodor;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiming.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerActivity extends AppCompatActivity {

    private String[] items ={"直播视频","推荐段子你","精华","直播","推荐","视频","图片","段子你好的","精华","直播","推荐"};
    private TrackIndicatorView mTrackIndicatorView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        init();
    }

    private void init() {
        mTrackIndicatorView = findViewById(R.id.indicator_view);
        mViewPager = findViewById(R.id.view_page);
        initIndicator();
        initViewPager();
    }

    private void initViewPager() {
        //缓存当前页面的左右各两面
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });

       mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           }


           @Override
           public void onPageSelected(int position) {

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });

    }

    private void initIndicator() {
        IndicatorAdapter<String> mAdapter = new IndicatorAdapter<String>(Arrays.asList(items)) {
            @Override
            public View getView(int position, ViewGroup parent, List<String> dataList) {
                TextView textView = new TextView(ViewPagerActivity.this);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(12);
                textView.setText(dataList.get(position));
                return textView;
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void heightLighIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.RED);
            }

            @Override
            public void restoreIndicator(View view) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.BLACK);
            }
        };

         mTrackIndicatorView.setAdapter(mAdapter,mViewPager);

    }
    
    
}
