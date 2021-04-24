package com.capstonedesign.lovebaby;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graph extends AppCompatActivity {
    //변수 모음
    EditText weight_1, height_1;
    EditText weight_2, height_2;
    EditText weight_3, height_3;
    EditText weight_4, height_4;

    Button button;

    RadioGroup radioGroup;
    RadioButton radioBoyBtn, radioGirlBtn;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphshow);     //현재 사용 xml = graphshow.xml

        //그래프 생성 및 할당
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setVisibility(View.VISIBLE);  //그래프 보이도록 설정

        //몸무게, 키 editText 변수 할당
        weight_1 = findViewById(R.id.weight_1); height_1 = findViewById(R.id.height_1);
        weight_2 = findViewById(R.id.weight_2); height_2 = findViewById(R.id.height_2);
        weight_3 = findViewById(R.id.weight_3); height_3 = findViewById(R.id.height_3);
        weight_4 = findViewById(R.id.weight_4); height_4 = findViewById(R.id.height_4);

        //실행 버튼 할당
        button = findViewById(R.id.addButton);

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
                        image.setImageResource(R.drawable.boy1);
                        image.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioGirlBtn:
                        image.setImageResource(R.drawable.girl1);
                        image.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //그래프 입력 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //문자열 변수 생성
                String weightInput_1, heightInput_1;
                String weightInput_2, heightInput_2;
                String weightInput_3, heightInput_3;
                String weightInput_4, heightInput_4;

                //입력된 숫자 문자열로 변경
                weightInput_1 = weight_1.getText().toString(); heightInput_1 = height_1.getText().toString();
                weightInput_2 = weight_2.getText().toString(); heightInput_2 = height_2.getText().toString();
                weightInput_3 = weight_3.getText().toString(); heightInput_3 = height_3.getText().toString();
                weightInput_4 = weight_4.getText().toString(); heightInput_4 = height_4.getText().toString();

                //그래프 그리기
                //문자열로 변경 된 입력 값을 valueOf를 사용하여 문자열에 대한 원시데이터형을 리턴하도록함.
                //valueOf의 경우 자바스크립트 내부적으로 사용되고, 작성할 때 코드상으로는 사용되지 않는다.
                try {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries < > (new DataPoint[] {
                            new DataPoint(0, 10),
                            new DataPoint(Double.valueOf(weightInput_1), Double.valueOf(heightInput_1)),
                            new DataPoint(Double.valueOf(weightInput_2), Double.valueOf(heightInput_2)),
                            new DataPoint(Double.valueOf(weightInput_3), Double.valueOf(heightInput_3)),
                            new DataPoint(Double.valueOf(weightInput_4), Double.valueOf(heightInput_4))
                    });
                    graph.addSeries(series);
                } catch (IllegalArgumentException e) {
                    //예외처리 하여 메시지 출력하도록.
                    Toast.makeText(Graph.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}