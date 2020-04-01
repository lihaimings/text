package com.haiming.myapplication.build.defaults;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.NavigationParams> implements INavigation{

    private P params;

    private View view;

    public AbsNavigationBar(P params) {
        this.params = params;
        createAndBind();
    }

    public void createAndBind() {
        if (params == null) {
            return;
        }
        view = LayoutInflater.from(params.context).inflate(bindLayoutId(), params.parent, false);
        params.parent.addView(view, 0);
        applyView();
    }

    protected String getString(int id) {
        return this.params.context.getResources().getString(id);
    }

    protected int getColor(int id) {
        return ContextCompat.getColor(this.params.context, id);
    }

    protected P getParams() {
        return params;
    }


    /**
     * 设置文本
     * @param viewId
     * @param text
     */
    protected void setText(int viewId, CharSequence text) {
        TextView tv = findViewById(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    /**
     * 设置点击事件
     * @param viewId
     * @param listener
     */
    protected void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }


    /**
     * 设置背景资源
     * @param viewId
     * @param resourceId
     */
    protected void setImageResource(int viewId, int resourceId) {
        ImageView imageView = findViewById(viewId);
        if (imageView != null) {
            imageView.setImageResource(resourceId);
        }
    }

    protected <T extends View> T findViewById(int id) {
        return (T) view.findViewById(id);
    }


    public abstract static class  Builder{

        //构建导航条方法
        public abstract AbsNavigationBar create();

        public static class  NavigationParams{
            public Context context;
            public ViewGroup parent;

            public NavigationParams(Context context, ViewGroup parent) {
                this.context = context;
                this.parent = parent;
            }
        }

    }
}
