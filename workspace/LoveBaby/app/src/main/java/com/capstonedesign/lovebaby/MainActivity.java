package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    //변수 모음
    Button entranceBtn, infoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //현재 사용 xml = activity_main.xml

        //입장, 정보 버튼 할당
        entranceBtn = findViewById(R.id.entranceBtn);
        infoBtn = findViewById(R.id.infoBtn);

        //로그인 이후 입장 버튼
        entranceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent 사용, home.xml로 넘어가도록
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        //제작자 정보
        //클릭시 정보가 담긴 사진이 나오도록 함.
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.info);
                dialog.setTitle("Programmers Info");

                Button okBtn = dialog.findViewById(R.id.okBtn);

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
}