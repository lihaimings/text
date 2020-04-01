package com.haiming.myapplication.build;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.haiming.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup parent = findViewById(R.id.view_root);

//        NavigationBar navigationBar = (NavigationBar) new NavigationBar.Builder(this,R.layout.header,parent)
//                .setText(R.id.back_tv,"改变")
//                .setOnClickListener(R.id.back_tv, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        finish();
//                    }
//                })
//                .create();
//
//        //设置其他属性
//        TextView textView = navigationBar.findViewById(R.id.back_tv);
//        textView.setVisibility(View.VISIBLE);

        //提供一个默认的

        DefaultNavigationBar defaultNavigationBar = (DefaultNavigationBar)
                new DefaultNavigationBar.Builder(this,parent)
                .setLeftText("默认")
                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setHideLeftText()
                .create();

    }


}
