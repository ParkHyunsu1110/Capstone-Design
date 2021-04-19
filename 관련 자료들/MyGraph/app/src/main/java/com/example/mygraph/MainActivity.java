package com.example.mygraph;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {
    EditText firstNum_1, secondNum_1;
    EditText firstNum_2, secondNum_2;
    EditText firstNum_3, secondNum_3;
    EditText firstNum_4, secondNum_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstNum_1 = findViewById(R.id.firstNum_1);
        secondNum_1 = findViewById(R.id.secondNum_1);
        firstNum_2 = findViewById(R.id.firstNum_2);
        secondNum_2 = findViewById(R.id.secondNum_2);
        firstNum_3 = findViewById(R.id.firstNum_3);
        secondNum_3 = findViewById(R.id.secondNum_3);
        firstNum_4 = findViewById(R.id.firstNum_4);
        secondNum_4 = findViewById(R.id.secondNum_4);
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        Button button = findViewById(R.id.addButton);
        graph.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstInput_1, secondInput_1;
                String firstInput_2, secondInput_2;
                String firstInput_3, secondInput_3;
                String firstInput_4, secondInput_4;
                //  1 and 5
                firstInput_1 = firstNum_1.getText().toString();
                secondInput_1 = secondNum_1.getText().toString();
                firstInput_2 = firstNum_2.getText().toString();
                secondInput_2 = secondNum_2.getText().toString();
                firstInput_3 = firstNum_3.getText().toString();
                secondInput_3 = secondNum_3.getText().toString();
                firstInput_4 = firstNum_4.getText().toString();
                secondInput_4 = secondNum_4.getText().toString();
                try {
                    LineGraphSeries<DataPoint> series = new LineGraphSeries < > (new DataPoint[] {
                            new DataPoint(0, 1),
                            new DataPoint(Integer.valueOf(firstInput_1), Integer.valueOf(secondInput_1)),
                            new DataPoint(Integer.valueOf(firstInput_2), Integer.valueOf(secondInput_2)),
                            new DataPoint(Integer.valueOf(firstInput_3), Integer.valueOf(secondInput_3)),
                            new DataPoint(Integer.valueOf(firstInput_4), Integer.valueOf(secondInput_4))
                    });
                    graph.addSeries(series);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


