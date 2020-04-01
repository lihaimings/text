package com.haiming.myapplication.incodor;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class IndicatorAdapter<T> {

    private List<T> mDataList;

    public IndicatorAdapter(List<T> dataList){
        this.mDataList=dataList;
    }

    public abstract View getView(int position, ViewGroup parent,List<T> dataList);

    public abstract int getCount();

    // 高亮当前位置
    public abstract void heightLighIndicator(View view);

    // 重置上一位置
    public abstract void restoreIndicator(View view);

    //底部指示器
    public  View getBottomTrackView(){
        return null;
    }

    public void setDataList(List<T> dataList) {
        mDataList = dataList;
    }


    public List<T> getDataList() {
        return mDataList;
    }
}
