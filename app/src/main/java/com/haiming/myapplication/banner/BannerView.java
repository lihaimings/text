package com.haiming.myapplication.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haiming.myapplication.R;

import java.util.List;

import androidx.viewpager.widget.ViewPager;

/**
 * if(mBannerAdapter == null){
 *     mBannerAdapter = new BannerAdapter<String>(Arrays.asList(url)) {
 *         @Override
 *         public View getView(int position, View convertView, List<String> dataList) {
 *             ImageView bannerIv = null;
 *             if(convertView == null){
 *                 bannerIv = new ImageView(MainActivity.this);
 *             }else {
 *                 bannerIv= (ImageView) convertView;
 *             }
 *             int curPosition = position%getCount();
 *            //利用第三方的工具加载图片 glide
 *             String  path = dataList.get(curPosition);
 *             Glide.with(MainActivity.this)
 *                     .load(path)
 *                     .placeholder(R.mipmap.ic_launcher)
 *                     .into(bannerIv);
 *             return bannerIv;
 *         }
 *         @Override
 *         public int getCount() {
 *             return url.length;
 *         }
 *     };
 * }
 * mBannerView.setAdapter(mBannerAdapter);
 */
public class BannerView extends RelativeLayout {

    private Context mContext;
    //轮播viewpager
    private BannerViewPager mBannerViewPager;
    //轮播的描述
    private TextView descTextView;
    //点的容器，里面addView圆点
    private LinearLayout mDotContainerView;
    //从外界拿到BannerAdapter，传给viewPager
    private  BannerAdapter mAdapter;
    //描述
    private List<String> desc ;

    //底部的控件
    private RelativeLayout relativeLayout;
    //底部容器颜色默认透明
    private int mButtomColor = Color.TRANSPARENT;

    //自定义圆点的颜色
    private Drawable mFocusDrawable = new ColorDrawable(Color.RED);
    private Drawable mNormalDrawable = new ColorDrawable(Color.WHITE);
    //自定义圆点的显示位置，默认中间
    private int mDotGravity = 0;
    //自定义圆点的大小
    private int mDotSize = 8;
    //自定义圆点的间据
    private int mDotDistance = 8;

    //获取宽高比例
    private float mWidthProportion,mHeightProportion ;
    //接口实例
    private BannerItemClickListener mClickListener;


    public BannerView(Context context) {
        super(context,null);
        initView(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        initView(context);
        initAttribute(attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttribute(attrs);
    }


    private void initView(Context context) {
        //将这个布局加载这个view中
        inflate(context, R.layout.ui_banner,this);
        mBannerViewPager=findViewById(R.id.banner_vp);
        descTextView = findViewById(R.id.banner_desc_tv);
        mDotContainerView = findViewById(R.id.dot_container);
        relativeLayout= findViewById(R.id.banner_bottom_view);
        relativeLayout.post(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setBackgroundColor(mButtomColor);
            }
        });
        this.mContext=context;
    }

    private void initAttribute(AttributeSet attrs) {
       TypedArray array = mContext.obtainStyledAttributes(attrs,R.styleable.BannerView);

       //获取点的位置
       mDotGravity = array.getInt(R.styleable.BannerView_dotGravity,0);
       //获取选择点的颜色
       mFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
       if(mFocusDrawable == null){
           mFocusDrawable = new ColorDrawable(Color.RED);
       }
       Log.d("颜色","1");
       //获取未选中的颜色
        mNormalDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if(mNormalDrawable == null){
            mNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        //获取点的大小
        mDotSize = (int) array.getDimension(R.styleable.BannerView_dotSize,dip2px(mDotSize));
        //获取点的间距
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance,dip2px(mDotDistance));
        //获取底部颜色
        mButtomColor = array.getColor(R.styleable.BannerView_bottomColor,mButtomColor);

        //获取宽高比例
        mWidthProportion = array.getFloat(R.styleable.BannerView_widthProportion,mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heigthProportion,mHeightProportion);

        array.recycle();
    }

    public void setAdapter(final BannerAdapter adapter){
        this.mAdapter=adapter;
        this.desc = adapter.getDescription();
        final int count = mAdapter.getCount();
        mBannerViewPager.setDataList(mAdapter.getDataList());
        mBannerViewPager.setAdapter(adapter);
        //初始化点的指示器,默认不显示
            relativeLayout.setVisibility(VISIBLE);
            initDotIndicator();
        mBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int curPosition = position%mAdapter.getCount();
                //改变圆点颜色
                    relativeLayout.setVisibility(VISIBLE);
                    pageSelect(curPosition);

                //显示描述，默认不显示
                    relativeLayout.setVisibility(VISIBLE);
                    if(desc != null && desc.size()==count){
                        descTextView.setText(desc.get(curPosition));
                    }

                if(mClickListener != null){
                    mClickListener.click(curPosition);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //动态设置高度
        setPicHeight();
        //开始通过handler发送消息
        startRoll();
    }

    /**
     * 动态设置高度
     */
    private void setPicHeight() {
        //动态指定宽高，计算高度
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        if(mWidthProportion == 0 || mHeightProportion ==0){
            return;
        }
        int heigth = (int) ((width*mHeightProportion)/mWidthProportion);

        Log.d("高度","heigth="+heigth+" width="+width+" mWidthProportion="+mWidthProportion+" mHeightProportion"+mHeightProportion);
        getLayoutParams().height=heigth;
    }

    /**
     * 页面切换的回调
     * @param position
     */
    private int mCurrentPosition =0;
    private void pageSelect(int position) {
        //把原来的位置颜色设置默认
        DotIndicatorView oldIndicatorView= (DotIndicatorView) mDotContainerView.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(mNormalDrawable);
        //当前位置的圆点变色
        mCurrentPosition = position; //从0-2的32次方
        DotIndicatorView currentIndicatorView= (DotIndicatorView) mDotContainerView.getChildAt(position%mAdapter.getCount());
        currentIndicatorView.setDrawable(mFocusDrawable);

    }


    private boolean showDesc =false;
    public void setshowDesc(boolean showDesc){
        this.showDesc=showDesc;
    }

//    public boolean showDesc(){
//        return showDesc;
//    }


    private boolean showDotIndicator =false;
    public void showDotIndicator(boolean showDotIndicator){
        this.showDotIndicator=showDotIndicator;
    }

//    public boolean showDotIndicator(){
//        return showDotIndicator;
//    }

    /**
     * 根据适配器的count创建圆点并增加至容器
     */
    private void initDotIndicator() {
        int count = mAdapter.getCount();
        mDotContainerView.setGravity(getDotGravity());
        for(int i=0;i<count;i++){
            //不断的往点的指示器添加圆点
            DotIndicatorView dotIndicatorView = new DotIndicatorView(mContext);
            //给圆点设置LayoutParams
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSize,mDotSize);
            params.leftMargin=params.rightMargin= mDotDistance;
            dotIndicatorView.setLayoutParams(params);
            //默认选择第一张
            if(i==0){
                dotIndicatorView.setDrawable(mFocusDrawable);
            }else {
                dotIndicatorView.setDrawable(mNormalDrawable);
            }
            //往容器里增加圆点
            Log.d("添加","1");
            mDotContainerView.addView(dotIndicatorView);
        }
    }


    /**
     * 根据mDotGravity值判断Gravity的位置
     * @return
     */
    private int getDotGravity() {
        switch (mDotGravity){
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.RIGHT;
            case 1:
                return Gravity.LEFT;
        }
        return Gravity.CENTER;
    }

    public void startRoll() {
        mBannerViewPager.startRoll();
    }

    /**
     * 把dip转成px
     * @param dip
     * @return
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
        ,dip,getResources().getDisplayMetrics());
    }


    public void setBannerItemClickListener(BannerItemClickListener clickListener){
        this.mClickListener = clickListener;

    }
    //点击回调监听
    public interface BannerItemClickListener{
        void click(int position);
    }
}
