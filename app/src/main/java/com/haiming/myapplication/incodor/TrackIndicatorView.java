package com.haiming.myapplication.incodor;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.haiming.myapplication.R;

import androidx.viewpager.widget.ViewPager;

/**
 * viewPager 滑动指示器
 */
public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    public IndicatorAdapter mAdapter;

    private IndicatorGroupView mIndicatorGroup;

    //一屏幕显示多少个
    private int mTabVisibleNums = 0;

    private int mItemWidth;

    //当前切换位置
    private int mCurrentPosition = 0;

    public TrackIndicatorView(Context context) {
        this(context,null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new IndicatorGroupView(context);
        addView(mIndicatorGroup);
        //4.指定item的宽度 自定义属性
        initAttrbute(context,attrs);

    }

    /**
     * 初始化
     * @param context
     * @param attrs
     */
    private void initAttrbute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);

         mTabVisibleNums = array.getInteger(R.styleable.TrackIndicatorView_tabVisibleNums,0);

        array.recycle();

    }

    public void setAdapter(IndicatorAdapter adapter){
        if(adapter == null){
            throw  new NullPointerException("adapter id is null");
        }

        mAdapter=adapter;

        int itemCount = mAdapter.getCount();

        for(int i=0 ; i<itemCount;i++){
            View itemView = mAdapter.getView(i,mIndicatorGroup,mAdapter.getDataList());
            mIndicatorGroup.addItemView(itemView);

            // 设置点击事件
            if(mViewPager != null){
                switchItemClick(itemView,i);
            }

        }

        mAdapter.heightLighIndicator(mIndicatorGroup.getItemAt(mCurrentPosition));




    }

    private void switchItemClick(View itemView, final int position) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
                SmoothScrollCurrentIndicator(position);
                mIndicatorGroup.scrollBottomIndicator(position);
            }
        });
    }

    private void SmoothScrollCurrentIndicator(int position) {
        //当前移出屏幕的位置
        float totalScroll = (position)*mItemWidth;
        //中间左边的位置
        int offsetScroll = (getWidth()- mItemWidth)/2;
        //减去中间左边的位置
        final int finalScroll = (int) (totalScroll-offsetScroll);
        smoothScrollTo(finalScroll,0);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
            //拿到item的宽度
//             int parentWidth = getWidth();

              mItemWidth = getItemWidth();
              requestLayout();

             //循环指定item的宽度
            for(int i=0;i<mAdapter.getCount();i++){
                Log.d("6666","count:"+mAdapter.getCount()+",width="+mItemWidth);
                mIndicatorGroup.getItemAt(i).getLayoutParams().width=mItemWidth;
            }

        mIndicatorGroup.addBottomTrackView(mAdapter.getBottomTrackView(),mItemWidth);

    }

    /**
     * 获取item的宽度
     * @return
     */
    private int getItemWidth() {
        //有没有指定
        int parentWidth = getWidth();

        if(mTabVisibleNums != 0){
            //取平均值
            return parentWidth/mTabVisibleNums;
        }

        //没有指定的
        int itemWidth = 0;
        //获取最宽的
        int maxItemWidth = 0;
        int allWidh = 0;
        Log.d("数量",""+mAdapter.getCount());
        for(int i=0 ; i< mAdapter.getCount();i++){

            int currentItemWidth = mIndicatorGroup.getItemAt(i).getMeasuredWidth();
            maxItemWidth = Math.max(currentItemWidth,maxItemWidth);
            allWidh+=currentItemWidth;

        }
         itemWidth = maxItemWidth;
        //最后算一次
        //如果全部加起来的宽度 小于 屏幕宽度
        if(itemWidth*mAdapter.getCount() < parentWidth){
            itemWidth = parentWidth/mAdapter.getCount();
        }

        return itemWidth;
    }


    /**
     * 设置适配器
     * @param adapter
     * @param viewPager
     */
    private ViewPager mViewPager;
    public void setAdapter(IndicatorAdapter adapter, ViewPager viewPager){
        this.mViewPager=viewPager;
        setAdapter(adapter);
        if(viewPager == null){
            throw  new NullPointerException("viewPager id is null");
        }

        mViewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(isExecuteScroll){
            //滚动的时候，会不断的调用
            scrollCurrentIndicator(position,positionOffset);
            mIndicatorGroup.scrollBottomTrack(position,positionOffset);
        }

    }

    //不断的滚动指示器
    private void scrollCurrentIndicator(int position, float positionOffset) {

        //当前移出屏幕的位置
        float totalScroll = (position+positionOffset)*mItemWidth;

        //中间左边的位置
        int offsetScroll = (getWidth()- mItemWidth)/2;

        //减去中间左边的位置
        final int finalScroll = (int) (totalScroll-offsetScroll);

        Log.d("移动位置",""+finalScroll);
        scrollTo(finalScroll,0);



    }

    @Override
    public void onPageSelected(int position) {

        //将上一个位置重置
        mAdapter.restoreIndicator(mIndicatorGroup.getItemAt(mCurrentPosition));

        //将当前位置点亮
        mCurrentPosition = position;
        mAdapter.heightLighIndicator(mIndicatorGroup.getItemAt(mCurrentPosition));


    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state == 1){
            isExecuteScroll =true;
        }

        if(state == 0){
            isExecuteScroll =false;
        }

    }

    private boolean isExecuteScroll = false;
}
