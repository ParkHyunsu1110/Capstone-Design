package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Table extends Activity {

    //변수 모음
    TextView weightText_1, heightText_1;
    TextView weightText_2, heightText_2;
    TextView weightText_3, heightText_3;
    TextView weightText_4, heightText_4;
    TextView weightText_5, heightText_5;

    Button btnBack;
    //표 제작
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);  //현재 사용 xml = table.xml

        //테이블 몸무게, 키 TextView 변수 할당
        weightText_1 = findViewById(R.id.weightText_1); heightText_1 = findViewById(R.id.heightText_1);
        weightText_2 = findViewById(R.id.weightText_2); heightText_2 = findViewById(R.id.heightText_2);
        weightText_3 = findViewById(R.id.weightText_3); heightText_3 = findViewById(R.id.heightText_3);
        weightText_4 = findViewById(R.id.weightText_4); heightText_4 = findViewById(R.id.heightText_4);
        weightText_5 = findViewById(R.id.weightText_5); heightText_5 = findViewById(R.id.heightText_5);

        //버튼 할당
        btnBack = findViewById(R.id.btnBack);

        //인텐트로 값 할당
        Intent getIntent = getIntent();
        String heightText = getIntent.getExtras().getString("cm");
        String weightText = getIntent.getExtras().getString("kg");
        String monthInput = getIntent.getExtras().getString("month2");

        //intent로 넘어온 개월수에 맞춰서 값이 입력되도록.
        switch (Integer.parseInt(monthInput)) {
            case 1:
                weightText_1.setText(weightText);
                heightText_1.setText(heightText);
                break;
            case 2: case 3:
                weightText_2.setText(weightText);
                heightText_2.setText(heightText);
                break;
            case 4: case 5:
                weightText_3.setText(weightText);
                heightText_3.setText(heightText);
                break;
            case 6: case 7: case 8:
                weightText_4.setText(weightText);
                heightText_4.setText(heightText);
                break;
            case 9: case 10: case 11:
                weightText_5.setText(weightText);
                heightText_5.setText(heightText);
                break;
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
