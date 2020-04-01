package com.haiming.myapplication.recycleView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haiming.myapplication.Letter.LetterSizeBar;
import com.haiming.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        mTextView = findViewById(R.id.txt);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BaseUserActivity.class);
                startActivity(intent);
            }
        });
    }


    public void baseUserClick(View view){

    }

}
