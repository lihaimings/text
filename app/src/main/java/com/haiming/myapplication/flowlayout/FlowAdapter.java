package com.haiming.myapplication.flowlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.haiming.myapplication.R;

import java.util.List;

public class FlowAdapter extends TagAdapter<String> {

    private LayoutInflater layoutInflater;

    public FlowAdapter(Context context,List<String> datas) {
        super(datas);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView textView = (TextView) layoutInflater.inflate(R.layout.item,parent,false);
        textView.setText(s);
        return textView;
    }
}
