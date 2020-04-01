package com.haiming.myapplication.banner;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haiming.myapplication.R;
import com.haiming.myapplication.incodor.Activitys;
import com.haiming.myapplication.incodor.ColoeTrackTextView;

import java.sql.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private BannerView mBannerView;

    private String[] url = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583608357897&di=87c5801dc8f155c54b137a2288585943&imgtype=0&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fd009b3de9c82d1587e249850820a19d8bd3e42a9.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583608357896&di=3e48f71c2d4b942e7a292e9ee228ba68&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fd62a6059252dd42a1c362a29033b5bb5c9eab870.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583608357895&di=42e050010f27198d7a831370a854ff4c&imgtype=0&src=http%3A%2F%2Fbig5.wallcoo.com%2Fanimal%2Ffly_and_freedom%2Fimages%2F0Vol_096_DY164.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583608357895&di=64a4126a298344bdd2d1ae9e6f469632&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Frushidao%2Fpics%2Fhv1%2F20%2F108%2F1744%2F113431160.jpg"
    };

    private String[] desc = {
            "游戏","狮子","鸟","美景"
    };

    public BannerAdapter<String> mBannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_layout);

        mBannerView= findViewById(R.id.banner_view);

        initBanner();
    }

    private void initBanner() {

        if(mBannerAdapter == null){
            mBannerAdapter = new BannerAdapter<String>(Arrays.asList(url)) {
                @Override
                public View getView(int position, View convertView, List<String> dataList) {
                    ImageView bannerIv = null;
                    if(convertView == null){
                        bannerIv = new ImageView(MainActivity.this);
                    }else {
                        bannerIv= (ImageView) convertView;
                    }
                    int curPosition = position%getCount();
                   //利用第三方的工具加载图片 glide
                    String  path = dataList.get(curPosition);
                    Glide.with(MainActivity.this)
                            .load(path)
                            .placeholder(R.mipmap.ic_launcher)
                            .into(bannerIv);
                    return bannerIv;
                }

                @Override
                public int getCount() {
                    return url.length;
                }
            };
        }
        mBannerAdapter.setDescription(Arrays.asList(desc));
        mBannerView.setAdapter(mBannerAdapter);
    }

}
