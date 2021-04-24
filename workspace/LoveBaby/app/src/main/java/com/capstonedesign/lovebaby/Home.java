package com.capstonedesign.lovebaby;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class Home extends Activity {
    //변수 모음
    ImageButton graphBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);  //현재 사용 xml = home.xml

        //그래프 이동 버튼 할당
        graphBtn = findViewById(R.id.graphBtn);

        //버튼 클릭 시 그래프 부분으로 이동
        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Graph.class);
                startActivity(intent);
            }
        });

    }
}