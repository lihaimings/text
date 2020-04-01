package com.haiming.myapplication.recycleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class LinearLayoutItemDecoration  extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;
    private Context mContext;

    public LinearLayoutItemDecoration(Context context,int drawableResource){
        //获取drawable
        mDrawable = ContextCompat.getDrawable(context,drawableResource);

    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //代表流出10像素分割线,最后一个位置不需要分割线
        Log.d("分割线","count="+parent.getChildCount());
        int position = parent.getChildAdapterPosition(view);
        if (position != 0)
            outRect.top = mDrawable.getIntrinsicHeight();
    }

    /**
     * 绘制分割线
     * @param canvas
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        //利用cnavas想绘制什么就绘制什么
        //在每一个itemDe 头部绘制
        int childCount = parent.getChildCount();

        //指定绘制区域
        Rect rect = new Rect();

        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth()-parent.getPaddingRight();



        for(int i=1;i<childCount;i++){

            rect.bottom = parent.getChildAt(i).getTop();
            rect.top = rect.bottom-mDrawable.getIntrinsicHeight();

            mDrawable.setBounds(rect);
            mDrawable.draw(canvas);

        }
    }
}
