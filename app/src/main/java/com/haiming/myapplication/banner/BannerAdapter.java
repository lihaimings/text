package com.haiming.myapplication.banner;

import android.view.View;

import java.util.List;

public abstract class BannerAdapter<T> {

    private List<T> mDataList;

    private List<String> mDescription;


    public BannerAdapter(List<T> dataList){
        this.mDataList = dataList;
    }

    /**
     * 根据位置获取viewpager的子view
     * @param position
     * @return
     */
    public abstract View getView(int position,View convertView,List<T> dataList) ;

    /**
     * 返回所有子view的数量
     * @return
     */
    public abstract int getCount();

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDescription(List<String> mDescription) {
        this.mDescription = mDescription;
    }

    public List<String> getDescription() {
        return mDescription;
    }


}
