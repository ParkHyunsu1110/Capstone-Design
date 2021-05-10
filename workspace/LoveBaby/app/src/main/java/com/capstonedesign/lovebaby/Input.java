package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintSet;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
<<<<<<< Updated upstream
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Input extends Activity implements OnClickListener {

    EditText cm,kg,month2;
    Button inputbutton, exitbutton;

    private static final int CALL_REQUEST = 1;

=======
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.time.temporal.ValueRange;

public class Input extends Activity  {

    FrameLayout container;

    //변수 모음
    TextView weightText_1, heightText_1;
    TextView weightText_2, heightText_2;
    TextView weightText_3, heightText_3;
    TextView weightText_4, heightText_4;
    TextView weightText_5, heightText_5;
    EditText cm, kg, month;

    Button inputButton;

>>>>>>> Stashed changes
    //입력 부분 제작 예정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);  //현재 사용 xml = input.xml
        cm = (EditText)findViewById(R.id.cm);
        kg = (EditText)findViewById(R.id.kg);
        month2 = (EditText)findViewById(R.id.month2);

        Button inputbutton = (Button)findViewById(R.id.inputbutton);
        Button exitbutton = (Button)findViewById(R.id.exitbutton);

        inputbutton.setOnClickListener(this);
        exitbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.inputbutton) {
            Intent intent = new Intent(this, Graph.class);
            // 값을 받을 액티비티로 보낼 값을 지정
            //(key, value 한 쌍으로 이루어진 임의 타입의 정보를 원하는 개수만큼 전달할수있음)
            intent.putExtra("cm", cm.getText().toString());
            intent.putExtra("kg", kg.getText().toString());
            intent.putExtra("month2", month2.getText().toString());
            //리턴값을 돌려주는 액티비티 호출
            startActivityForResult(intent, CALL_REQUEST);
        }
        else {
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //호출된 액티비티가 종료시 onActivityResult 메소드 호출
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CALL_REQUEST) {
            if(resultCode==RESULT_OK) {

                cm.setText("");
                kg.setText("");
                month2.setText("");

<<<<<<< Updated upstream
                String cm = data.getExtras().getString("cm").toString();
                String kg = data.getExtras().getString("kg").toString();
                String month2 = data.getExtras().getString("month2").toString();
=======
        //Input 몸무게, 키 editText 변수 할당
        cm = findViewById(R.id.cm); kg = findViewById(R.id.kg); month = findViewById(R.id.month);

        View inflate = getLayoutInflater().inflate(R.layout.table,  null,false);

        //테이블 몸무게, 키 TextView 변수 할당
        weightText_1 = inflate.findViewById(R.id.weightText_1); heightText_1 = inflate.findViewById(R.id.heightText_1);
        weightText_2 = inflate.findViewById(R.id.weightText_2); heightText_2 = inflate.findViewById(R.id.heightText_2);
        weightText_3 = inflate.findViewById(R.id.weightText_3); heightText_3 = inflate.findViewById(R.id.heightText_3);
        weightText_4 = inflate.findViewById(R.id.weightText_4); heightText_4 = inflate.findViewById(R.id.heightText_4);
        weightText_5 = inflate.findViewById(R.id.weightText_5); heightText_5 = inflate.findViewById(R.id.heightText_5);

        //실행 버튼 할당
        inputButton = findViewById(R.id.inputbutton);

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Input.this, Table.class);
/*
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View convertView = layoutInflater.inflate(R.layout.table, null, false);

*/
                //문자열 변수 생성
                String weightInput, heightInput, monthInput;

                //입력된 숫자 문자열로 변경
                weightInput = cm.getText().toString();
                heightInput = kg.getText().toString();
                monthInput = month.getText().toString();

                intent.putExtra("cm", weightInput);
                intent.putExtra("kg", heightInput);
                intent.putExtra("month", monthInput);
                startActivity(intent);
                finish();

            }
        });
>>>>>>> Stashed changes

                Toast.makeText(this, " 종료되셨습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
