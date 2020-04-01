package com.haiming.myapplication.recycleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;

    public GridLayoutItemDecoration(Context context,int drawableResource){
        //获取drawable
        mDrawable = ContextCompat.getDrawable(context,drawableResource);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        //留出分割线
        outRect.bottom = mDrawable.getIntrinsicHeight();
        outRect.right = mDrawable.getIntrinsicWidth();
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        drawHorizontal(canvas,parent);

        drawVertical(canvas,parent);

    }


    /**
     *绘制垂直方向的分割线
     * @param canvas
     * @param parent
     */

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int ChildCount = parent.getChildCount();

        for(int i = 0;i<ChildCount;i++){
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getTop()-params.topMargin;
            int bottom = childView.getBottom()+params.bottomMargin;
            int left = childView.getRight()+params.rightMargin;
            int right = left + mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(left,top,right,bottom);
            mDrawable.draw(canvas);
        }
    }


    /**
     * 绘制水平方向
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int ChildCount = parent.getChildCount();

        for(int i = 0;i<ChildCount;i++){
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft()-params.leftMargin;
            int right = childView.getRight()+childView.getWidth()+params.rightMargin;
            int top = childView.getBottom()+params.bottomMargin;
            int bottom = top+mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left,top,right,bottom);
            mDrawable.draw(canvas);
        }
    }
}
