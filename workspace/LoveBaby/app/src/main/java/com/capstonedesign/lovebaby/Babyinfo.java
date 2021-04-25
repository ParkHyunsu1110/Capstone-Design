package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class Babyinfo extends Activity {
    //변수 모음
    Button BtnStart, BtnStop,Doublecheck;
    ViewFlipper vFlipper;
    View diglogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.babyinfo);  // 현재 사용 xml = babyinfo.xml

        Intent intent = getIntent();

        //변수 할당
        BtnStart = findViewById(R.id.BtnStart);
        BtnStop = findViewById(R.id.BtnStop);
        vFlipper = findViewById(R.id.flipper);

        //뷰 플리퍼 1초마다 넘어가도록 1초  = 1000
        vFlipper.setFlipInterval(1000);

        //뷰 플리퍼 시작 버튼
        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.startFlipping();
            }
        });
        //뷰 플리퍼 정지 버튼
        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.stopFlipping();
            }
        });

        Doublecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diglogView = (View) View.inflate(Babyinfo.this, R.layout.dialog1, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(Babyinfo.this);
                dlg.setTitle("암호 입력");
                dlg.setView(diglogView);
                dlg.setPositiveButton("확인",null);
                dlg.setNegativeButton("취소",null);
                dlg.show();
            }
        });
    }
}