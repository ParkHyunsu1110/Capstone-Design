package com.capstonedesign.lovebaby;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graph extends AppCompatActivity {
    /**
     * weight_1,2,3
     * height_1,2,3
     *  위 6가지는 우선 제외하고 진행하도록 할게요.
     * 교수님은 우선적으로 실행을 보시는것 같으니 임의값 집어 넣고 실행하도록 하겠습니다.
     * */

    //변수 모음
    /*
    EditText weight_1, height_1;
    EditText weight_2, height_2;
    EditText weight_3, height_3;*/
    EditText weight_4, height_4;

    Button inputButton, outputButton;

    RadioGroup radioGroup;
    RadioButton radioBoyBtn, radioGirlBtn;

    ImageView image;

    String cm,kg,month2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphshow);     //현재 사용 xml = graphshow.xml

        //그래프 생성 및 할당
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);  //그래프 보이도록 설정

        //몸무게, 키 editText 변수 할당
       /* weight_1 = findViewById(R.id.weight_1); height_1 = findViewById(R.id.height_1);
        weight_2 = findViewById(R.id.weight_2); height_2 = findViewById(R.id.height_2);
        weight_3 = findViewById(R.id.weight_3); height_3 = findViewById(R.id.height_3);*/
        weight_4 = findViewById(R.id.weight_4); height_4 = findViewById(R.id.height_4);

        //실행 버튼 할당
        inputButton = findViewById(R.id.inputButton);
        outputButton = findViewById(R.id.outputButton);

        //라디오 변수 할당
        radioGroup = findViewById(R.id.radioGroup);
        radioBoyBtn = findViewById(R.id.radioBoyBtn);
        radioGirlBtn = findViewById(R.id.radioGirlBtn);

        //이미지 할당 -> 추후 표로 변경 후 제거 예정
        image = findViewById(R.id.image);

        //아이들 표 나오도록, 선택 -> 사진이 잘 안보인다는 교수님 의견 반영하여 표로 제작할 예정임.
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int rb = radioGroup.getCheckedRadioButtonId();

                //라디오 버튼 클릭시 이미지가 보이도록하며, 라디오 그룹에 있는 버튼 중 하나만 선택 가능한 성질을 이용, 남아 선택 시 남아만, 여아 선택 시 여아만
                switch(rb){
                    case R.id.radioBoyBtn:
                        image.setImageResource(R.drawable.malehwtable);
                        image.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioGirlBtn:
                        image.setImageResource(R.drawable.femalehwtable);
                        image.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //입력값 출력 버튼
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //문자열 변수 생성
                /*String weightInput_1, heightInput_1;
                String weightInput_2, heightInput_2;
                String weightInput_3, heightInput_3;*/
                String weightInput_4, heightInput_4;

                //입력된 숫자 문자열로 변경
                /*weightInput_1 = weight_1.getText().toString(); heightInput_1 = height_1.getText().toString();
                weightInput_2 = weight_2.getText().toString(); heightInput_2 = height_2.getText().toString();
                weightInput_3 = weight_3.getText().toString(); heightInput_3 = height_3.getText().toString();*/
                weightInput_4 = weight_4.getText().toString(); heightInput_4 = height_4.getText().toString();

                //그래프 그리기
                //문자열로 변경 된 입력 값을 valueOf를 사용하여 문자열에 대한 원시데이터형을 리턴하도록함.
                //valueOf의 경우 자바스크립트 내부적으로 사용되고, 작성할 때 코드상으로는 사용되지 않는다.
                try {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries < > (new DataPoint[] {
                            //그래프 값 입력 시 오름차순 입력할 것, 그렇지 않을 경우 예외처리에 걸려서 오류 메시지 토스트 형식으로 출력됨
                            new DataPoint(4.4, 55.0),
                            new DataPoint(Double.valueOf("6.5"), Double.valueOf("62")),
                            new DataPoint(Double.valueOf("7.7"), Double.valueOf("66.3")),
                            new DataPoint(Double.valueOf("8.7"), Double.valueOf("71.5")),
                            new DataPoint(Double.valueOf("9.6"), Double.valueOf("76.3")),
                            new DataPoint(Double.valueOf(kg), Double.valueOf(cm))
                    });
                    graph.addSeries(series);
                } catch (IllegalArgumentException e) {
                    //예외처리 하여 메시지 출력하도록.
                    Toast.makeText(Graph.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

        });

        //저장값 출력 버튼
        outputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineGraphSeries<DataPoint> series = new LineGraphSeries < > (new DataPoint[] {

                        /*new DataPoint(Double.valueOf(weightInput_1), Double.valueOf(heightInput_1)),
                        new DataPoint(Double.valueOf(weightInput_2), Double.valueOf(heightInput_2)),
                        new DataPoint(Double.valueOf(weightInput_3), Double.valueOf(heightInput_3)),*/

                        //그래프 값 입력 시 오름차순 입력할 것, 그렇지 않을 경우 예외처리에 걸려서 오류 메시지 토스트 형식으로 출력됨
                        new DataPoint(4.4, 55.0),
                        new DataPoint(Double.valueOf("6.5"), Double.valueOf("62")),
                        new DataPoint(Double.valueOf("7.7"), Double.valueOf("66.3")),
                        new DataPoint(Double.valueOf("8.7"), Double.valueOf("71.5")),
                        new DataPoint(Double.valueOf("9.6"), Double.valueOf("76.3"))
                });
                graph.addSeries(series);
            }
        });

        //이전 액티비티에서 전달한 값 불러오기
        Intent intent = getIntent();
        cm = intent.getExtras().getString("cm").toString();
        kg = intent.getExtras().getString("kg").toString();
        month2 = intent.getExtras().getString("month2").toString();

        TextView txtResult = (TextView) findViewById(R.id.txtResult);
        txtResult.setText("키 :  " + cm + "\n 몸무게 : " + kg + "\n 개월수 : " + month2);

        Button btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                process();
                finish();
            }
        });
    }

    private void process() {
        //호출한 액티비티에게 결과값 전달
        Intent intent = new Intent(this, Input.class);
        intent.putExtra("cm", cm);
        intent.putExtra("kg", kg);
        intent.putExtra("month2", month2);
        setResult(Activity.RESULT_OK, intent);
    }

}