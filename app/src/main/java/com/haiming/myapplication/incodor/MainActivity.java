package com.haiming.myapplication.incodor;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haiming.myapplication.Letter.LetterSizeBar;
import com.haiming.myapplication.R;
import com.haiming.myapplication.dagger.DaggerMainComponent;
import com.haiming.myapplication.dagger.MainModule;
import com.haiming.myapplication.dagger.MainPresenter;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {


    private ColoeTrackTextView mColoeTrackTextView;
    private Button left;
    private Button right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incodoer_layout);
        mColoeTrackTextView = findViewById(R.id.ColoeTrackTextView);
        left =findViewById(R.id.left);
        right =findViewById(R.id.right);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftToRight();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightToLeft();
            }
        });


    }

    public void leftToRight(){
        mColoeTrackTextView.setDirection(ColoeTrackTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentPregress = (float) animation.getAnimatedValue();
                mColoeTrackTextView.setCurrentProgress(currentPregress);
            }
        });
        valueAnimator.start();
    }


    public void rightToLeft(){
        mColoeTrackTextView.setDirection(ColoeTrackTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentPregress = (float) animation.getAnimatedValue();
                mColoeTrackTextView.setCurrentProgress(currentPregress);
            }
        });
        valueAnimator.start();
    }

}
