package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

public class Babyinfo extends Activity {
    //변수 모음
    Button btnStart, btnStop, infoChange, infoDelete;
    ImageButton doubleCheck;

    EditText babyName, rrNumber1, rrNumber2, birthDate, birthTime, bloodType;

    ViewFlipper vFlipper;
    View diglogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.babyinfo);  // 현재 사용 xml = babyinfo.xml

        //변수 할당
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        infoChange = findViewById(R.id.infoChange);
        infoDelete = findViewById(R.id.infoDelete);

        doubleCheck = findViewById(R.id.doubleCheck);

        babyName = findViewById(R.id.babyName);
        rrNumber1 = findViewById(R.id.rrNumber1);
        rrNumber2 = findViewById(R.id.rrNumber2);
        birthDate = findViewById(R.id.birthDate);
        birthTime = findViewById(R.id.birthTime);
        bloodType = findViewById(R.id.bloodType);

        vFlipper = findViewById(R.id.flipper);


        //뷰 플리퍼 1초마다 넘어가도록 1초  = 1000
        vFlipper.setFlipInterval(1000);

        //뷰 플리퍼 시작 버튼
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.startFlipping();
            }
        });

        //뷰 플리퍼 정지 버튼
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.stopFlipping();
            }
        });

        //주민번호 등록 시 이중 확인 버튼
        doubleCheck.setOnClickListener(new View.OnClickListener() {
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