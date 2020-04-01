package com.haiming.myapplication.dagger;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haiming.myapplication.Letter.LetterSizeBar;
import com.haiming.myapplication.R;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mLetterTv;
    private LetterSizeBar letterSizeBar;

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLetterTv=findViewById(R.id.letter_tv);

        DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

        mainPresenter.showUserName();
    }

    public void showUserName(String name) {
        mLetterTv.setText(name);
    }


}
