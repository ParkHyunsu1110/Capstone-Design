package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class Table extends Activity {

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
        setContentView(R.layout.table);  //현재 사용 xml = table.xml

        // DB 객체 생성
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "vaccine-db").allowMainThreadQueries().build();

        // 키, 몸무게, 개월수 DB list
        List<GraphInfo> graphInfoList = db.graphInfoDAO().getAll();


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

        //테이블에 DB 데이터 입력
        for(int i = 0; i < graphInfoList.size(); i++) {
            GraphInfo graphInfo = graphInfoList.get(i);

            String weightText = Integer.toString(graphInfo.getWeight()) ;
            String heightText = Integer.toString(graphInfo.getHeight());

            switch (graphInfo.getMonth()) {
                case 1:
                    weightText_1.setText(weightText);
                    heightText_1.setText(heightText);
                    break;
                case 2:
                    weightText_2.setText(weightText);
                    heightText_2.setText(heightText);
                    break;
                case 3:
                    weightText_3.setText(weightText);
                    heightText_3.setText(heightText);
                    break;
                case 4:
                    weightText_4.setText(weightText);
                    heightText_4.setText(heightText);
                    break;
                case 5:
                    weightText_5.setText(weightText);
                    heightText_5.setText(heightText);
                    break;
                case 6:
                    weightText_6.setText(weightText);
                    heightText_6.setText(heightText);
                    break;
                case 7:
                    weightText_7.setText(weightText);
                    heightText_7.setText(heightText);
                    break;
                case 8:
                    weightText_8.setText(weightText);
                    heightText_8.setText(heightText);
                    break;
                case 9:
                    weightText_9.setText(weightText);
                    heightText_9.setText(heightText);
                    break;
                case 10:
                    weightText_10.setText(weightText);
                    heightText_10.setText(heightText);
                    break;
                case 11:
                    weightText_11.setText(weightText);
                    heightText_11.setText(heightText);
                    break;
                case 12:
                    weightText_12.setText(weightText);
                    heightText_12.setText(heightText);
                    break;
                case 13:
                    weightText_13.setText(weightText);
                    heightText_13.setText(heightText);
                    break;
                case 14:
                    weightText_14.setText(weightText);
                    heightText_14.setText(heightText);
                    break;
                case 15:
                    weightText_15.setText(weightText);
                    heightText_15.setText(heightText);
                    break;
                case 16:
                    weightText_16.setText(weightText);
                    heightText_16.setText(heightText);
                    break;
                case 17:
                    weightText_17.setText(weightText);
                    heightText_17.setText(heightText);
                    break;
                case 18:
                    weightText_18.setText(weightText);
                    heightText_18.setText(heightText);
                    break;
                case 19:
                    weightText_19.setText(weightText);
                    heightText_19.setText(heightText);
                    break;
                case 20:
                    weightText_20.setText(weightText);
                    heightText_20.setText(heightText);
                    break;
                case 21:
                    weightText_21.setText(weightText);
                    heightText_21.setText(heightText);
                    break;
                case 22:
                    weightText_22.setText(weightText);
                    heightText_22.setText(heightText);
                    break;
                case 23:
                    weightText_23.setText(weightText);
                    heightText_23.setText(heightText);
                    break;
                case 24:
                    weightText_24.setText(weightText);
                    heightText_24.setText(heightText);
                    break;
            }
        }
        //intent로 넘어온 개월수에 맞춰서 값이 입력되도록.




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
