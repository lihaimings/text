package com.haiming.myapplication.build;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class AbsNavigationBar<B extends AbsNavigationBar.Builder> implements INavigation{

    private B mBuildr;
    private View mNavigationBar;

    protected AbsNavigationBar(B builder){
        this.mBuildr=builder;
        createNavigationBar();
    }

    @Override
    public void createNavigationBar() {
        mNavigationBar = LayoutInflater.from(mBuildr.mContext).inflate(
                mBuildr.mLayoutID,mBuildr.mParent,false
        );

        //添加
        attachParent(mNavigationBar,mBuildr.mParent);

        attachNavigationParams();
    }


    /**
     * 绑定参数
     */
    @Override
    public void attachNavigationParams() {
        //设置文本
        Map<Integer,CharSequence> mTextMaps = mBuildr.mTextMaps;
        for (Map.Entry<Integer,CharSequence> entry:mTextMaps.entrySet()){
            TextView textView = findViewById(entry.getKey());
            textView.setText(entry.getValue());
        }

        //设置点击事件
        Map<Integer,View.OnClickListener> mClickListenerMap = mBuildr.mClickListenerMap;
        for (Map.Entry<Integer,View.OnClickListener> entry:mClickListenerMap.entrySet()){
            View view=findViewById(entry.getKey());
            view.setOnClickListener(entry.getValue());
        }
    }

    public  <T extends View> T findViewById(int viewId) {
        return  mNavigationBar.findViewById(viewId);
    }

    /**
     * 将navigation添加到父布局
     * @param mNavigationBar
     * @param mParent
     */
    @Override
    public void attachParent(View mNavigationBar, ViewGroup mParent) {

        mParent.addView(mNavigationBar,0);
    }


    public B getmBuildr(){
        return mBuildr;
    }
    /**
     * builder构建类
     * 构建NavigationBar
     */
    public static abstract class Builder<B extends Builder>{
        public Context mContext;
        public int mLayoutID;
        public ViewGroup mParent;

        public Map<Integer,CharSequence> mTextMaps;
        public Map<Integer,View.OnClickListener> mClickListenerMap;

        public Builder(Context context,int layoutId, ViewGroup parent){
            this.mContext=context;
            this.mLayoutID=layoutId;
            this.mParent=parent;
            mTextMaps = new HashMap<>();
            mClickListenerMap = new HashMap<>();
        }

        /**
         * 用来创建navationBar
         * @return
         */
        public abstract AbsNavigationBar create();

        /**
         * 设置文本
         * @param viewId
         * @param text
         * @return
         */
        public B setText(int viewId, String text){
            mTextMaps.put(viewId,text);
            return (B) this;
        }


        /**
         * 设置点解事件
         * @param viewId
         * @param clickListener
         * @return
         */
        public  B setOnClickListener(int viewId, View.OnClickListener clickListener){
            mClickListenerMap.put(viewId,clickListener);
            return (B) this;
        }
    }
}
