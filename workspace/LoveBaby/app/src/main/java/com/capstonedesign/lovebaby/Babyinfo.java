package com.capstonedesign.lovebaby;

import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewFlipper;
import android.support.v7.app.AppCompatActivity;

public class Babyinfo extends AppCompatActivity {
    //변수 모음
    Button btnStart, btnStop, infoChange, infoDelete;
    ImageButton doubleCheck;
    CheckBox checkBox;

    SharedPreferences appData;
    public EditText babyName, rrNumber1, rrNumber2, year, month, day, birthTime, bloodType;

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

        checkBox = findViewById(R.id.checkBox);
        doubleCheck = findViewById(R.id.doubleCheck);

        babyName = findViewById(R.id.babyName);
        rrNumber1 = findViewById(R.id.rrNumber1);
        rrNumber2 = findViewById(R.id.rrNumber2);
        year = findViewById(R.id.year);
        month = findViewById(R.id.month);
        day = findViewById(R.id.day);
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
                diglogView = (View) View.inflate(Babyinfo.this, R.layout.graphmain, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder(Babyinfo.this);

                dlg.setTitle("암호 입력");

                dlg.setView(diglogView);

                dlg.setPositiveButton("확인",null);
                dlg.setNegativeButton("취소",null);

               dlg.show();
            }
        });

        // 설정값 불러오기
        appData = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean isChecked = appData.getBoolean("save", false);
        checkBox.setChecked(isChecked);

        //CheckBox에 체크 되어있으면 isChecked가 True 아니면 false
        if(isChecked) {
            String BabyName = appData.getString("BabyName", "");
            babyName.setText(BabyName);
            String RRNumber1 = appData.getString("RRNumber1", "");
            rrNumber1.setText(RRNumber1);
            String RRNumber2 = appData.getString("RRNumber2", "");
            rrNumber2.setText(RRNumber2);
            String Year = appData.getString("Year", "");
            year.setText(Year);
            String Month = appData.getString("Month", "");
            month.setText(Month);
            String Day = appData.getString("Day", "");
            day.setText(Day);
            String BirthTime = appData.getString("BirthTime", "");
            birthTime.setText(BirthTime);
            String BloodType = appData.getString("BloodType", "");
            bloodType.setText(BloodType);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // SharedPreferences의 수정 가능한 객체 얻기
        SharedPreferences.Editor editor = appData.edit();
        // 저장할 데이터
        editor.putBoolean("save", checkBox.isChecked());
        editor.putString("BabyName", babyName.getText().toString());
        editor.putString("RRNumber1", rrNumber1.getText().toString());
        editor.putString("RRNumber2", rrNumber2.getText().toString());
        editor.putString("Year", year.getText().toString());
        editor.putString("Month", month.getText().toString());
        editor.putString("Day", day.getText().toString());
        editor.putString("BirthTime", birthTime.getText().toString());
        editor.putString("BloodType", bloodType.getText().toString());

        //저장
        editor.apply();
    }
}