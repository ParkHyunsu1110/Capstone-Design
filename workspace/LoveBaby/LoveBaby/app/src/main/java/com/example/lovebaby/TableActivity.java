package com.example.lovebaby;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class TableActivity extends Activity {

    //변수 모음
    TextView weightText_1, heightText_1;
    TextView weightText_2, heightText_2;
    TextView weightText_3, heightText_3;
    TextView weightText_4, heightText_4;
    TextView weightText_5, heightText_5;
    TextView weightText_6, heightText_6;
    TextView weightText_7, heightText_7;
    TextView weightText_8, heightText_8;
    TextView weightText_9, heightText_9;
    TextView weightText_10, heightText_10;

    TextView weightText_11, heightText_11;
    TextView weightText_12, heightText_12;
    TextView weightText_13, heightText_13;
    TextView weightText_14, heightText_14;
    TextView weightText_15, heightText_15;
    TextView weightText_16, heightText_16;
    TextView weightText_17, heightText_17;
    TextView weightText_18, heightText_18;
    TextView weightText_19, heightText_19;

    TextView weightText_20, heightText_20;
    TextView weightText_21, heightText_21;
    TextView weightText_22, heightText_22;
    TextView weightText_23, heightText_23;
    TextView weightText_24, heightText_24;


    Button btnBack;
    //표 제작
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);  //현재 사용 xml = table.xml

        //테이블 몸무게, 키 TextView 변수 할당
        weightText_1 = findViewById(R.id.weightText_1); heightText_1 = findViewById(R.id.heightText_1);
        weightText_2 = findViewById(R.id.weightText_2); heightText_2 = findViewById(R.id.heightText_2);
        weightText_3 = findViewById(R.id.weightText_3); heightText_3 = findViewById(R.id.heightText_3);
        weightText_4 = findViewById(R.id.weightText_4); heightText_4 = findViewById(R.id.heightText_4);
        weightText_5 = findViewById(R.id.weightText_5); heightText_5 = findViewById(R.id.heightText_5);
        weightText_6 = findViewById(R.id.weightText_6); heightText_6 = findViewById(R.id.heightText_6);
        weightText_7 = findViewById(R.id.weightText_7); heightText_7 = findViewById(R.id.heightText_7);
        weightText_8 = findViewById(R.id.weightText_8); heightText_8 = findViewById(R.id.heightText_8);
        weightText_9 = findViewById(R.id.weightText_9); heightText_9 = findViewById(R.id.heightText_9);
        weightText_10 = findViewById(R.id.weightText_10); heightText_10 = findViewById(R.id.heightText_10);

        weightText_11 = findViewById(R.id.weightText_11); heightText_11 = findViewById(R.id.heightText_11);
        weightText_12 = findViewById(R.id.weightText_12); heightText_12 = findViewById(R.id.heightText_12);
        weightText_13 = findViewById(R.id.weightText_13); heightText_13 = findViewById(R.id.heightText_13);
        weightText_14 = findViewById(R.id.weightText_14); heightText_14 = findViewById(R.id.heightText_14);
        weightText_15 = findViewById(R.id.weightText_15); heightText_15 = findViewById(R.id.heightText_15);
        weightText_16 = findViewById(R.id.weightText_16); heightText_16 = findViewById(R.id.heightText_16);
        weightText_17 = findViewById(R.id.weightText_17); heightText_17 = findViewById(R.id.heightText_17);
        weightText_18 = findViewById(R.id.weightText_18); heightText_18 = findViewById(R.id.heightText_18);
        weightText_19 = findViewById(R.id.weightText_19); heightText_19 = findViewById(R.id.heightText_19);
        weightText_20 = findViewById(R.id.weightText_20); heightText_20 = findViewById(R.id.heightText_20);

        weightText_21 = findViewById(R.id.weightText_21); heightText_21 = findViewById(R.id.heightText_21);
        weightText_22 = findViewById(R.id.weightText_22); heightText_22 = findViewById(R.id.heightText_22);
        weightText_23 = findViewById(R.id.weightText_23); heightText_23 = findViewById(R.id.heightText_23);
        weightText_24 = findViewById(R.id.weightText_24); heightText_24 = findViewById(R.id.heightText_24);

        //버튼 할당
        btnBack = findViewById(R.id.btnBack);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
