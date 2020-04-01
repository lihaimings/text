package com.haiming.myapplication.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 画圆点
 */
public class DotIndicatorView extends View {

    private Drawable drawable;

    public DotIndicatorView(Context context) {
        super(context);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       if(drawable != null){
           //画图
           Bitmap bitmap = drawableToBitmap(drawable);

           //把bitmap变为圆的
           Bitmap circleBitmap = getCircleBitmap(bitmap);

           canvas.drawBitmap(circleBitmap,0,0,null);

       }
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建一个bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        //在画布上面画个圆，圆点，半径
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,
                getMeasuredWidth()/2,paint);

        //取圆和bitmap矩形的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //把原来的bitmap绘制到圆上面
        canvas.drawBitmap(bitmap,0,0,paint);

        bitmap.recycle();
        bitmap=null;
        return circleBitmap;

    }

    /**
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        //如果是bitmapDrawable类型
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }

        //其他类型如 colorDrawable
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas = new Canvas(outBitmap);
        //把drawable写道bitmap
        drawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        drawable.draw(canvas);
        return outBitmap;
    }


    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        invalidate(); //刷新视图
    }
}
