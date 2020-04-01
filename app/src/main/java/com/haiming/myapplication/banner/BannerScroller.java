package com.haiming.myapplication.banner;

import android.content.Context;
import android.graphics.Color;
import android.view.animation.Interpolator;
import android.widget.Scroller;


public class BannerScroller extends Scroller {

    //动画持续的时间
    private int mDurationTime = 850;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);

    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // 把持续时间传我们的方法
        super.startScroll(startX, startY, dx, dy, mDurationTime);
    }

    public void setDurationTime(int time){
        this.mDurationTime=time;
    }
}
