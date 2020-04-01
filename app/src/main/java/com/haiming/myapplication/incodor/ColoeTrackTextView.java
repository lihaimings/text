package com.haiming.myapplication.incodor;

import android.app.PendingIntent;
        import android.content.Context;
        import android.content.res.TypedArray;
        import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
        import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
        import android.util.Log;
        import android.util.TypedValue;
        import android.widget.TextView;

        import com.haiming.myapplication.R;

        import androidx.annotation.Nullable;

public class ColoeTrackTextView extends TextView{

    // 1.实现一个文字两种颜色-绘制不变色字体的画笔
    private Paint mOriginPaint;

    //1. 实现一个文字两种颜色- 绘制变色字体的画笔
    private Paint mChangePaint;

    private String mtext;

    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction{
        LEFT_TO_RIGHT,RIGHT_TO_LEFT
    }


    private float mCurrentProgress = 0.0f;

    public ColoeTrackTextView(Context context) {
        super(context,null);
        initPaint(context,null);
    }

    public ColoeTrackTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint(context,attrs);
    }

    public ColoeTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context,attrs);
    }

    public ColoeTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void initPaint(Context context,AttributeSet attrs) {

        Log.d("我执行了",""+1);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColoeTrackTextView);

        int originColor = array.getColor(R.styleable.ColoeTrackTextView_originColor,context.getColor(R.color.colorAccent));
        int changeColor = array.getColor(R.styleable.ColoeTrackTextView_changeColor,context.getColor(R.color.colorPrimaryDark));

        mOriginPaint=getPaintByColor(Color.BLACK);
        mChangePaint=getPaintByColor(Color.RED);

        array.recycle();
    }

    public Paint getPaintByColor(int color){
        Paint paint = new Paint();
        //设置颜色
        paint.setColor(color);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        //设置字体的大小 就是textView的字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    //一个文字两种颜色
    //利用clipRect api不断的改变中间值 左边一个画笔去画，右边一个画笔去画，不断的改变中间值
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.clipRect() 裁剪区域

        mtext = getText().toString();

        int width = getWidth();

        if (!TextUtils.isEmpty(mtext)){
            //算出当前相对宽度的滑动进度
            int middle = (int)(mCurrentProgress*width);
            if(mDirection == Direction.LEFT_TO_RIGHT) {
                //变色
                darwText(canvas, mChangePaint, 0, middle);
                //默认
                darwText(canvas, mOriginPaint, middle, width);
            }else {
                //变色
                darwText(canvas,  mChangePaint, width-middle, width);
                //默认
                darwText(canvas, mOriginPaint , 0, width-middle);
            }

        }

    }

    public void darwText(Canvas canvas,Paint paint,int start,int end){
        canvas.save();

        //绘制不变色的
        Rect rect = new Rect(start,0,end,getHeight());
        canvas.clipRect(rect);
        Rect bounds = new Rect();
        paint.getTextBounds(mtext,0,mtext.length(),bounds);
        //获取自己的宽度
        int x = getWidth()/2 - bounds.width()/2;
//        int x= 0;
        //基线baseLine
        Paint.FontMetricsInt fontMetricsInt =  paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseLine = getHeight()/2+dy;
        canvas.drawText(mtext,x,baseLine,paint);
        canvas.restore();
    }

    public void setDirection(Direction direction){
        this.mDirection=direction;
    }

    public void setCurrentProgress(float currentProgress){
        this.mCurrentProgress = currentProgress;
        //重新绘制
        invalidate();
    }

    public void setChangeColor(int changeColor){
        this.mChangePaint.setColor(changeColor);
    }

    public void setOriginColor(int changeColor){
        this.mOriginPaint.setColor(changeColor);
    }

    public void setTextSize(int size,int moreSize){
        mChangePaint.setTextSize(size);
        mOriginPaint.setTextSize(moreSize);
    }

    @Override
    public void setTextColor(int color) {
        this.mOriginPaint.setColor(color);
        super.setTextColor(color);
    }
}
