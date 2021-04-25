package com.capstonedesign.lovebaby;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Calendar extends AppCompatActivity {
    //변수 모음
    public String fname = null, str = null;
    public CalendarView calendarView;
    public Button change_Btn, del_Btn, save_Btn;    //변경, 삭제, 저장
    public TextView diaryTextView, textView2, textView3;
    public EditText contextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);  //현재 사용 xml = calendar.xml

        //변수 할당
        calendarView = findViewById(R.id.calendarView);

        diaryTextView = findViewById(R.id.diaryTextView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        save_Btn = findViewById(R.id.save_Btn);
        del_Btn = findViewById(R.id.del_Btn);
        change_Btn = findViewById(R.id.change_Btn);

        contextEditText = findViewById(R.id.contextEditText);

        //로그인 및 회원가입 엑티비티에서 이름을 받아옴
        /*
        Intent intent=getIntent();
        String name=intent.getStringExtra("userName");
        final String userID=intent.getStringExtra("userID");
         */

        String name = "산모";
        final String userID = "의정";
        textView3.setText(name+"님의 일정");

        //캘린더 날짜 클릭
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //Visible
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);

                //Invisible
                textView2.setVisibility(View.INVISIBLE);
                change_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);

                //출력 형식
                diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));

                //컨에딧 공란 변경
                contextEditText.setText("");

                //checkDay 함수 사용 -> 파일 처리를 통해 년도, 월, 일 유저 아이디 저장.
                checkDay(year, month, dayOfMonth, userID);
            }
        });

        //저장 버튼 클릭 시 수정, 삭제 버튼 나오고 저장 버튼 사라짐
        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);

                //str에 컨에딧 내용 담기도록 -> 컨에딧은 EditText이고, str은 String 형이기 때문에 Casting을 통한 형변환이 필요함.
                str = contextEditText.getText().toString();

                //텍뷰에 str내용 들어감
                textView2.setText(str);

                //Visible
                textView2.setVisibility(View.VISIBLE);
                change_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);

                //Invisible
                contextEditText.setVisibility(View.INVISIBLE);
                save_Btn.setVisibility(View.INVISIBLE);
            }
        });
    }

    //파일처리 부분
    public void  checkDay(int cYear, int cMonth, int cDay, String userID){
        fname = "" + userID + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";    //저장할 파일 이름설정
        FileInputStream fis = null;   //FileStream fis 변수

        try{    //예외처리
            fis = openFileInput(fname);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str = new String(fileData);

            //Visible
            textView2.setVisibility(View.VISIBLE);
            change_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            //텍뷰에 str내용 들어감
            textView2.setText(str);

            //Invisible
            contextEditText.setVisibility(View.INVISIBLE);
            save_Btn.setVisibility(View.INVISIBLE);

            //수정 버튼 클릭시 저장 버튼 나오고, 수정, 삭제 버튼 사라짐
            change_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Visible
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);

                    //컨에딧에 str내용 들어감
                    contextEditText.setText(str);

                    //Invisible
                    textView2.setVisibility(View.INVISIBLE);
                    change_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);

                    //텍뷰2에 컨에딧 내용 들어감
                    textView2.setText(contextEditText.getText());
                }

            });
            //삭제 버튼 클릭 시 저장 버튼 나오고, 수정, 삭제 버튼 사라짐.
            del_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Visible
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);

                    //컨에딧 내용 공란 변경
                    contextEditText.setText("");

                    //InVisible
                    textView2.setVisibility(View.INVISIBLE);
                    change_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);

                    //fname 내용 삭제
                    removeDiary(fname);
                }
            });
            if(textView2.getText() == null){

                //Visible
                contextEditText.setVisibility(View.VISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);

                //Invisible
                textView2.setVisibility(View.INVISIBLE);
                change_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //내용 삭제
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){
        FileOutputStream fos = null;

        try{
            fos = openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content = "";  //내용 공란으로 변경
            fos.write((content).getBytes());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //내용 저장
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){
        FileOutputStream fos = null;

        try{
            fos = openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content = contextEditText.getText().toString();    //내용 컨에딧으로 변경
            fos.write((content).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
