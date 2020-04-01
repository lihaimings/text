package com.haiming.myapplication.flowlayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.haiming.myapplication.Letter.LetterSizeBar;
import com.haiming.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  {


    private Button btn1,btn2;
    private TagFlowLayout flowLayout;
    private  FlowAdapter flowAdapter;
    private String[] names = {"1","2dadf","3rg","4dsafs","5afsd","6df","7df","8sf"};
    private List<Integer> deletes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flowlayout);
        btn1 = findViewById(R.id.insert);
        btn2 = findViewById(R.id.delete);
        flowLayout = findViewById(R.id.flow_layout);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flowAdapter == null){
                    flowAdapter = new FlowAdapter(MainActivity.this,Arrays.asList(names));
                    flowLayout.setAdapter(flowAdapter);
                }

                flowLayout.delete(deletes);


                flowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        for(Integer integer:selectPosSet){
                            Log.d("数据",""+integer);
                        }
                    }
                });

                flowLayout.setMax(new TagFlowLayout.max() {
                    @Override
                    public void kk() {
                        Toast.makeText(MainActivity.this,"三个了",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                for (Integer integer:deletes){
                    deletes.remove(i++);
                }
                deletes.add(2);
            }
        });
    }


}
