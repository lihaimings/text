package com.haiming.myapplication.build;

import android.content.Context;
import android.view.ViewGroup;

/**
 * 这是导航栏的基类
 */
public class NavigationBar extends  AbsNavigationBar {

    protected NavigationBar(Builder builder) {
        super(builder);
    }


    public static class Builder extends AbsNavigationBar.Builder<NavigationBar.Builder>{

        public Builder(Context context, int layoutId, ViewGroup parent) {
            super(context, layoutId, parent);
        }

        @Override
        public AbsNavigationBar create() {
            return new NavigationBar(this);
        }
    }


}
