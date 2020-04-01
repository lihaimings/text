package com.haiming.myapplication.build;

import android.view.View;
import android.view.ViewGroup;

/**
 * 导航栏的规范
 */
public interface INavigation {

    void createNavigationBar();


    /**
     * 绑定参数
     */
    void attachNavigationParams();

    /**
     * 将navigation添加到父布局
     * @param mNavigationBar
     * @param mParent
     */
    void attachParent(View mNavigationBar, ViewGroup mParent);

}
