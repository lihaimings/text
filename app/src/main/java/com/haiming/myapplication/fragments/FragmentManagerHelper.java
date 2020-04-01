package com.haiming.myapplication.fragments;

import com.haiming.myapplication.R;

import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentManagerHelper {

    private FragmentManager mFragmentManager;
    private int mContainerViewId;

    /**
     *
     * @param fragmentManager 管理类
     * @param containerViewId 容器布局id
     */
    public FragmentManagerHelper(@NonNull FragmentManager fragmentManager,@IdRes int containerViewId){
        mFragmentManager = fragmentManager;
        mContainerViewId = containerViewId;
    }


    /**
     * 添加fragment方法
     * @param fragment
     */
    public  void  add(Fragment fragment){

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //第一个参数是fragment容器的id，需要添加的fragment
        fragmentTransaction.add(mContainerViewId,fragment);
        //一定要commit
        fragmentTransaction.commit();
    }

    /**
     * 切换显示fragment
     * @param fragment
     */
    public void switchFragment(Fragment fragment){
        //开启事务
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        //先隐藏当前所有的fragment
        List<Fragment> childFragments = mFragmentManager.getFragments();
        for(Fragment childFragment:childFragments){
            fragmentTransaction.hide(childFragment);
        }

        //如果这个容器里面没有我们就添加，否则显示
        if(!childFragments.contains(fragment)){
            fragmentTransaction.add(mContainerViewId,fragment);
        }else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();
    }
}
