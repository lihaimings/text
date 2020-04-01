package com.haiming.myapplication.recycleView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haiming.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BaseUserActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_use_activity);
        mRecyclerView = findViewById(R.id.recycler_view);
        initData();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        mRecyclerView.addItemDecoration(new GridLayoutItemDecoration(this,R.drawable.item_dirver));

        mRecyclerView.setAdapter(recyclerAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for(int i='A';i<='Z';i++){
            mDatas.add(""+(char)i);
        }
    }


    private class RecyclerItemDecoration  extends RecyclerView.ItemDecoration{

        public Paint mPaint;

        public RecyclerItemDecoration(){
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.BLACK);

        }

        /**
         * 增加分割线，10px的红色
         * @param outRect
         * @param view
         * @param parent
         * @param state
         */
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
           //代表流出10像素分割线,最后一个位置不需要分割线
            Log.d("分割线","count="+parent.getChildCount());
            int position = parent.getChildAdapterPosition(view);
            if (position != 0)
            outRect.top = 10;
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
                rect.top = rect.bottom-10;
                canvas.drawRect(rect,mPaint);

            }
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           //创建Viewholder

            View view = LayoutInflater.from(BaseUserActivity.this).inflate(R.layout.recycler_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            //绑定数据
            holder.mTextView.setText(mDatas.get(position));

        }

        @Override
        public int getItemCount() {
            //显示条目
            return mDatas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView mTextView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.id_num);
            }
        }
    }
}
