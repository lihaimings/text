package com.haiming.myapplication.incodor;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.animation.ValueAnimator.ofFloat;

/**
 * 底部的指示器
 */
public class IndicatorGroupView extends FrameLayout {

    //动态的添加view--指示器条目的容器
    private LinearLayout mIndicatorGroup;
    //底部
    private View mBottomTrackView;
    //底部宽度
    private int mItemWidth;

    private FrameLayout.LayoutParams params;


    //初始值
    private int mInitMargin;


    public IndicatorGroupView(@NonNull Context context) {
        this(context,null);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new LinearLayout(context);
        addView(mIndicatorGroup);
    }


    /**
     * 添加itemVeiw
     * @param itemView
     */
    public void addItemView(View itemView) {
        mIndicatorGroup.addView(itemView);
    }

    public View getItemAt(int i) {
        return mIndicatorGroup.getChildAt(i);
    }

    public void addBottomTrackView(View bottomTrackView, int itemWidth) {
        if(bottomTrackView == null){
            return;
        }
        this.mBottomTrackView = bottomTrackView;
        this.mItemWidth = itemWidth;
        addView(mBottomTrackView);
        //让它在底部，一个条目的宽度
        params = (LayoutParams) mBottomTrackView.getLayoutParams();
        params.gravity= Gravity.BOTTOM;

        int trackWidth = params.width;

        if(params.width == ViewGroup.LayoutParams.MATCH_PARENT){
            trackWidth=mItemWidth;
        }

        //设置的宽度过大
        if(params.width>mItemWidth){
            trackWidth = mItemWidth;
        }

        params.width=trackWidth;

        mInitMargin = (mItemWidth-trackWidth)/2;
        params.leftMargin = mInitMargin;
        requestLayout();

        mBottomTrackView.setLayoutParams(params);

    }

    /**
     * 滚动底部的显示器
     * @param position
     * @param positionOffset
     */
    public void scrollBottomTrack(int position, float positionOffset) {
        if(mBottomTrackView == null){
            return;
        }

        int leftMargin = (int) ((position+positionOffset)*mItemWidth);
        params.leftMargin=leftMargin+mInitMargin;
        mBottomTrackView.setLayoutParams(params);
        Log.d("底部","已经滑动"+leftMargin);
    }

    public void scrollBottomIndicator(int position) {

        if(mBottomTrackView == null){
            return;
        }

        int leftMargin = (int) ((position)*mItemWidth)+mInitMargin;
        int current = params.leftMargin;
        int distance = leftMargin-current;

        //带动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(current,leftMargin).setDuration((long) (Math.abs(distance)*0.5f));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentLeft = (float) animation.getAnimatedValue();
                params.leftMargin= (int) currentLeft;
                mBottomTrackView.setLayoutParams(params);
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
        Log.d("底部","已经跳转"+distance);
    }
}
