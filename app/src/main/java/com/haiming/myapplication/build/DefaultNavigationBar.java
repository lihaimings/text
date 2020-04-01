package com.haiming.myapplication.build;

import android.content.Context;
import android.opengl.Visibility;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haiming.myapplication.R;

/**
 * 可以拿过来直接用
 */
public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder> {

    protected DefaultNavigationBar(Builder builder) {
        super(builder);
    }

    @Override
    public void attachNavigationParams() {
        super.attachNavigationParams();

        TextView textView = findViewById(R.id.back_tv);
        textView.setVisibility(getmBuildr().mLeftVisible);

    }

    public static class Builder extends AbsNavigationBar.Builder<DefaultNavigationBar.Builder>{

        private int mLeftVisible = View.VISIBLE;

        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.defualt_header, parent);
        }

        @Override
        public AbsNavigationBar create() {
            return new DefaultNavigationBar(this);
        }

        public DefaultNavigationBar.Builder setLeftText(String text){
            setText(R.id.back_tv,text);
            return this;
        }

        public DefaultNavigationBar.Builder setLeftClickListener(View.OnClickListener clickListener){
            setOnClickListener(R.id.back_tv,clickListener);
            return this;
        }

        public Builder setHideLeftText(){
            mLeftVisible=View.GONE;
            return this;
        }
    }
}
