package com.haiming.myapplication.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.haiming.myapplication.Letter.LetterSizeBar;
import com.haiming.myapplication.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private MeFragment mMeFragment;

    private FrameLayout frameLayout;

    private RadioButton radioButton1,radioButton2,radioButton3;

    private FragmentManagerHelper fragmentManagerHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ui);


        frameLayout = findViewById(R.id.fl_fragment);

        radioButton1=findViewById(R.id.home_rb);
        radioButton2=findViewById(R.id.find_rb);
        radioButton3=findViewById(R.id.me_rb);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeFragment == null){
                    mHomeFragment =new HomeFragment();
                }
                if(fragmentManagerHelper != null){
                    fragmentManagerHelper.switchFragment(mHomeFragment);
                }
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFindFragment == null){
                    mFindFragment=new FindFragment();
                }
                if(fragmentManagerHelper != null){
                    fragmentManagerHelper.switchFragment(mFindFragment);
                }
            }
        });


        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMeFragment == null){
                    mMeFragment=new MeFragment();
                }
               if(fragmentManagerHelper != null){
                   fragmentManagerHelper.switchFragment(mMeFragment);
               }
            }
        });

        initData();

    }

    private void initData() {
        mHomeFragment = new HomeFragment();
       fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(),R.id.fl_fragment);
       fragmentManagerHelper.add(mHomeFragment);
    }


    @Override
    public void onClick(View v) {

    }
}
