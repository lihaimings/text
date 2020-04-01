package com.haiming.myapplication.incodor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.haiming.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activitys extends AppCompatActivity {


    private String[] items ={"直播","推荐","视频","图片","段子","精华"};
    private TrackIndicatorView mTrackIndicatorView;
    private LinearLayout mIndicatorContainer;
    private List<ColoeTrackTextView> mIndicators;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_layout);
//        mTrackIndicatorView = findViewById(R.id.srclloc_view);
//        mIndicators = new ArrayList<>();
        //initIndicator();
    }

}
