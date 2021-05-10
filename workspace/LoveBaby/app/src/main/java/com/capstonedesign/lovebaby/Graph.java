package com.capstonedesign.lovebaby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Graph extends AppCompatActivity {

    //변수 모음
    TextView txtResult;

    Button inputWeightButton, outputWeightButton;
    Button inputHeightButton, outputHeightButton;
    Button btnBack;

    String cm,kg,month2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphshow);     //현재 사용 xml = graphshow.xml

        //그래프 생성 및 할당
        final GraphView graphWeight = (GraphView) findViewById(R.id.graphWeight);
        graphWeight.setVisibility(View.VISIBLE);  //그래프 보이도록 설정

        //그래프 생성 및 할당
        final GraphView graphHeight = (GraphView) findViewById(R.id.graphHeight);
        graphHeight.setVisibility(View.VISIBLE);  //그래프 보이도록 설정

        //실행 버튼 할당
        inputHeightButton = findViewById(R.id.inputHeightButton);
        outputHeightButton = findViewById(R.id.outputHeightButton);

        inputWeightButton = findViewById(R.id.inputWeightButton);
        outputWeightButton = findViewById(R.id.outputWeightButton);

        btnBack = findViewById(R.id.btnBack);

        //텍스트뷰 할당
        txtResult = findViewById(R.id.txtResult);

        //신장 입력값 출력 버튼
        inputHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //그래프 그리기
                //문자열로 변경 된 입력 값을 valueOf를 사용하여 문자열에 대한 원시데이터형을 리턴하도록함.
                //valueOf의 경우 자바스크립트 내부적으로 사용되고, 작성할 때 코드상으로는 사용되지 않는다.
                try {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries < > (new DataPoint[] {
                            //그래프 값 입력 시 오름차순 입력할 것, 그렇지 않을 경우 예외처리에 걸려서 오류 메시지 토스트 형식으로 출력됨
                            new DataPoint(0, 55.4),
                            new DataPoint(Double.valueOf("1"), Double.valueOf("56.8")),
                            new DataPoint(Double.valueOf("2"), Double.valueOf("58.7")),
                            new DataPoint(Double.valueOf("3"), Double.valueOf("62.8")),
                            new DataPoint(Double.valueOf("4"), Double.valueOf("65.7")),
                            new DataPoint(Double.valueOf(month2), Double.valueOf(cm))
                    });
                    graphHeight.addSeries(series);
                } catch (IllegalArgumentException e) {
                    //예외처리 하여 메시지 출력하도록.
                    Toast.makeText(Graph.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //신장 저장값 출력 버튼
        outputHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineGraphSeries<DataPoint> series = new LineGraphSeries < > (new DataPoint[] {
                        //그래프 값 입력 시 오름차순 입력할 것, 그렇지 않을 경우 예외처리에 걸려서 오류 메시지 토스트 형식으로 출력됨
                        new DataPoint(0, 55.4),
                        new DataPoint(Double.valueOf("1"), Double.valueOf("56.8")),
                        new DataPoint(Double.valueOf("2"), Double.valueOf("58.7")),
                        new DataPoint(Double.valueOf("3"), Double.valueOf("62.8")),
                        new DataPoint(Double.valueOf("4"), Double.valueOf("65.7"))
                });
                graphHeight.addSeries(series);
            }
        });

        //체중 입력값 출력 버튼
        inputWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries < > (new DataPoint[] {
                            //그래프 값 입력 시 오름차순 입력할 것, 그렇지 않을 경우 예외처리에 걸려서 오류 메시지 토스트 형식으로 출력됨
                            new DataPoint(0, 4.4),
                            new DataPoint(Double.valueOf("1"), Double.valueOf("6.5")),
                            new DataPoint(Double.valueOf("2"), Double.valueOf("7.0")),
                            new DataPoint(Double.valueOf("3"), Double.valueOf("7.4")),
                            new DataPoint(Double.valueOf("4"), Double.valueOf("7.8")),
                            new DataPoint(Double.valueOf(month2), Double.valueOf(kg))
                    });
                    graphWeight.addSeries(series);
                } catch (IllegalArgumentException e) {
                    //예외처리 하여 메시지 출력하도록.
                    Toast.makeText(Graph.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //체중 저장값 출력 버튼
        outputWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LineGraphSeries<DataPoint> series = new LineGraphSeries < > (new DataPoint[] {
                        //그래프 값 입력 시 오름차순 입력할 것, 그렇지 않을 경우 예외처리에 걸려서 오류 메시지 토스트 형식으로 출력됨
                        new DataPoint(0, 4.4 ),
                        new DataPoint(Double.valueOf("1"), Double.valueOf("6.5")),
                        new DataPoint(Double.valueOf("2"), Double.valueOf("7.0")),
                        new DataPoint(Double.valueOf("3"), Double.valueOf("7.4")),
                        new DataPoint(Double.valueOf("4"), Double.valueOf("7.8"))
                });
                graphWeight.addSeries(series);
            }
        });

        //이전 액티비티에서 전달한 값 불러오기
        Intent intent = getIntent();

        cm = intent.getExtras().getString("cm").toString();
        kg = intent.getExtras().getString("kg").toString();
        month2 = intent.getExtras().getString("month2").toString();

        txtResult.setText("키 :  " + cm + "\n몸무게 : " + kg + "\n개월수 : " + month2);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}