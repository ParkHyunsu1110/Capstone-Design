package com.capstonedesign.lovebaby;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class Home extends Activity {
    //변수 모음
    ImageButton graphBtn, babyinfoBtn, babyCalendalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);  //현재 사용 xml = home.xml

        //그래프 이동 버튼 할당
        graphBtn = findViewById(R.id.graphBtn);
        babyinfoBtn = findViewById(R.id.babyinfoBtn);
        babyCalendalBtn = findViewById(R.id.babyCalendalBtn);

        //버튼 클릭 시 그래프 부분으로 이동
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphTab.class);
                startActivity(intent);
            }
        });
        //버튼 클릭 시 아이 정보 부분으로 이동
        babyinfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), Babyinfo.class);
                startActivity(intent2);
            }
        });
        //버튼 클릭 시 캘린더 부분으로 이동
        babyCalendalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), Calendar.class);
                startActivity(intent3);
            }
        });
    }
}