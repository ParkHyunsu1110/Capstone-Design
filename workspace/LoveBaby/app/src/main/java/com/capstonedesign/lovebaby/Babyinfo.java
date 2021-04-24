package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class Babyinfo extends Activity {
    //변수 모음
    Button BtnStart , BtnStop;
    ViewFlipper vFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.babyinfo);  // 현재 사용 xml = babyinfo.xml

        Intent intent = getIntent();

        //변수 할당
        BtnStart = findViewById(R.id.BtnStart);
        BtnStop = findViewById(R.id.BtnStop);
        vFlipper = findViewById(R.id.flipper);
        vFlipper.setFlipInterval(1000);

        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.startFlipping();
            }
        });
        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.stopFlipping();
            }
        });
    }
}