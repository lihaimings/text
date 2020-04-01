package com.haiming.myapplication.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class BannerViewPager<T> extends ViewPager {

    //数据的资源
    private List<T> mDataList;
    //自定义adapter，主要是能自定义view
    private BannerAdapter mAdapter;

    //发送消息的Msg
    private final int MSG = 0X0010;
    //页面切换间隔时间
    private int mCurDownTime = 3500;
    //切换到指定的item，并重新发送消息
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //每隔多少秒切换下一张图片
            setCurrentItem((getCurrentItem()+1));
            startRoll();
        }
    };

    //继承Scroller，修改滑动时间
    private BannerScroller mScroller;
    //存放被销毁的View
    private List<View> mCurrentView;

    private Activity mActivity;

    //管理activity的生命周期
    Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new DefaultActivityLifecycleCallbacks() {

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            //是不是监听当前的activity的生命周期
            if(activity == getContext()) {
                //开启轮播
                handler.sendEmptyMessageDelayed(mCurDownTime, MSG);
            }
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            //是不是监听当前的activity的生命周期
            if(activity == getContext()) {
                //停止轮播
                handler.removeMessages(MSG);
            }
        }

    };

    public BannerViewPager(@NonNull Context context) {
       this(context, (AttributeSet) null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public void setDataList(List<T> mDataList) {
        this.mDataList = mDataList;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    private void initView(Context context) {
        this.mActivity = (Activity) context;
        //通过反射设置Scroller成另一个BannerScroller
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            mScroller = new BannerScroller(context);
            field.set(this,mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCurrentView = new ArrayList<>();
    }

    //设置切换页面动画的持续时间
    public void setScrollerDuration(int scrollerDuration){
        mScroller.setDurationTime(scrollerDuration);
    }

    public void setAdapter(@Nullable BannerAdapter adapter) {
        //自定义加载View的adpter
        this.mAdapter=adapter;
        // BannerPagerAdapter继承PagerAdapter，可以实现自定义加载view
        setAdapter(new BannerPagerAdapter<T>(mDataList));
        //管理activity的生命周期
        mActivity.getApplication().registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }


    /**
     * 实现自动切换，发送消息
     */
    public void  startRoll(){
        //清除消息
        handler.removeMessages(MSG);
        //消息 延迟发送
        handler.sendEmptyMessageDelayed(MSG,mCurDownTime);
    }

    /**
     * 销毁handler，避免内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeMessages(MSG);
        handler=null;
        mActivity.getApplication().unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }



    /**
     * veiwpager设置适配器
     */
    private class BannerPagerAdapter<T> extends PagerAdapter{



        //数据的资源
        private List<T> mDataList;

        public BannerPagerAdapter(List<T> dataList){
            this.mDataList = dataList;
        }

        @Override
        public int getCount() {
            //为了实现无线循环 0-2的32次方
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        /**
         * 创建VeiwPager条目回调的方法
         * @param container
         * @param position
         * @return
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            // Adapter设计模式 为了完全让用户自定义
           View bannerItemVeiw =mAdapter.getView(position,getConverst(),mDataList);
           //添加ViewPager里面
            container.addView(bannerItemVeiw);
            return bannerItemVeiw;
        }


        /**
         * 销毁条目回调的方法
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mCurrentView.add((View) object);
        }

        //获取复用界面
        private View getConverst() {
            for(int i=0;i<mCurrentView.size();i++){
                if(mCurrentView.get(i).getParent() == null){
                    return mCurrentView.get(i);
                }
            }
            return null;
        }

    }

}
