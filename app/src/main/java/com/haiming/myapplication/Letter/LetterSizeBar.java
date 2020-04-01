package com.haiming.myapplication.Letter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LetterSizeBar extends View {


    private Paint mPaint=new Paint();

    //当前触摸的位置
    private String mCurrentTouchLetter;



    //定义26个字母

    private static String[] letters={"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    public LetterSizeBar(Context context) {
        super(context,null);
    }

    public LetterSizeBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public LetterSizeBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //sp转px
    private float sp2px(int i) {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,i,getResources().getDisplayMetrics());
    }

    //复写onMeasure()：指定宽高

    private void init() {
        mPaint.setAntiAlias(true);
        //自定义属性，颜色 字体大小
        mPaint.setTextSize(sp2px(12)); //设置的是像素
        //默认颜色
        mPaint.setTextSize(Color.BLUE);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        init();
        //计算指定宽度=左右的padding+字母的宽度(取决于我们的画笔)
        Float textWidth = mPaint.measureText("A");// A字母的宽度
        int width = (int) (getPaddingLeft()+getPaddingRight()+textWidth);

        //高度可以直接获取
        int height= MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width,height);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        //画26个字母
        int itemHeight = (getHeight()-getPaddingTop()-getPaddingBottom())/letters.length;
        for (int i= 0;i<letters.length;i++){
            //知道每个字母的中心位置  1.字母的高度的一半  2.字母的一半+前面字符高度
            int letterCenterY = i*itemHeight+itemHeight/2+getPaddingTop();
            //基线中心位置
            Paint.FontMetrics fontMetrics= mPaint.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom);
            int baseLine = letterCenterY + dy;

            int textWidth = (int) mPaint.measureText(letters[i]);
            int x = getWidth()/2 -textWidth/2;

            //当前字母要高亮 用两个画笔 或改变颜色
            if(letters[i].equals(mCurrentTouchLetter)){
                mPaint.setColor(Color.RED);
                canvas.drawText(letters[i],x,baseLine,mPaint);
            }else {
                mPaint.setColor(Color.BLUE);
                canvas.drawText(letters[i],x,baseLine,mPaint);
            }

        }

    }

    //手指触摸高亮
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算出当前触摸的字母,获取当前位置
                float currentMoveY = event.getY();
                // 位置 = currentMoveY/高度


                int itemHeight = (getHeight()-getPaddingTop()-getPaddingBottom())/letters.length;
                int currentPostion = (int) (currentMoveY/itemHeight);

                if(currentPostion <0){
                    currentPostion=0;
                }

                if(currentPostion>letters.length-1){
                    currentPostion = letters.length-1;
            }

                mCurrentTouchLetter = letters[currentPostion];



                if(mListener != null){
                    mListener.touch(mCurrentTouchLetter,true);
                }

                //重新绘制
                invalidate();
                break;

            case  MotionEvent.ACTION_UP:

                if(mListener != null){
                    mListener.touch(mCurrentTouchLetter,true);
                }
                break;

        }

        return true;
    }


    private TouchLetterListener mListener;
    public void setOnLetterTouchListener(TouchLetterListener listener){
        this.mListener = listener;
    }
    public interface TouchLetterListener{

         void  touch(String letter,Boolean istouch);

    }
}
