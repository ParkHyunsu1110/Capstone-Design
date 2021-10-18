package com.example.lovebaby.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lovebaby.MainActivity;
import com.example.lovebaby.Model.BabyInfoModel;
import com.example.lovebaby.MyMarkerView;
import com.example.lovebaby.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GraphFragment extends Fragment {
    //변수 모음

    Button inputWeightButton;
    Button inputHeightButton;
    Button btnBack;

    private TextView textView_babyname, textView_birth;
    private String babyName;
    private String birthday;

    private LineChart lineChart, lineChart3;
    private LineChart lineChart2, lineChart4;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph,container,false);

        textView_babyname = (TextView)view.findViewById(R.id.babymenu_babyname);
        textView_birth = (TextView)view.findViewById(R.id.babymenu_birth);

        btnBack = (Button) view.findViewById(R.id.backBtnGraph);

        Bundle bundle = getArguments();
        if (bundle != null) {
            babyName = bundle.getString("babyname");
            birthday = bundle.getString("birthday");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle(2);
                bundle.putString("babyName",babyName);
                bundle.putString("birthday",birthday);

                Fragment hwFragment = new HWFragment();
                hwFragment.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(hwFragment);
            }
        });

        FirebaseDatabase.getInstance().getReference("babyinfo").orderByChild("name").equalTo(babyName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                birthday = snapshot.getValue(BabyInfoModel.class).birthday;
                textView_birth.setText(diffMonth(birthday,"month")+"개월,  "+diffMonth(birthday,"")+"일");
                textView_babyname.setText(babyName);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //체중
        inputWeightButton = view.findViewById(R.id.inputWeightButton);
        lineChart = (LineChart) view.findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, (float) 4.5));
        entries.add(new Entry(2, (float) 5.6));
        entries.add(new Entry(3, (float) 6.4));
        entries.add(new Entry(4, (float) 7.0));
        entries.add(new Entry(5, (float) 7.5));
        entries.add(new Entry(6, (float) 7.9));
        entries.add(new Entry(7, (float) 8.3));
        entries.add(new Entry(8, (float) 8.6));
        entries.add(new Entry(9, (float) 8.9));
        entries.add(new Entry(10, (float) 9.2));
        entries.add(new Entry(11, (float) 9.4));
        entries.add(new Entry(12, (float) 9.6));
        entries.add(new Entry(13, (float) 9.9));
        entries.add(new Entry(14, (float) 10.1));
        entries.add(new Entry(15, (float) 10.3));
        entries.add(new Entry(16, (float) 10.5));
        entries.add(new Entry(17, (float) 10.7));
        entries.add(new Entry(18, (float) 10.9));
        entries.add(new Entry(19, (float) 11.1));
        entries.add(new Entry(20, (float) 11.3));
        entries.add(new Entry(21, (float) 11.5));
        entries.add(new Entry(22, (float) 11.8));
        entries.add(new Entry(23, (float) 12.0));
        entries.add(new Entry(24, (float) 12.2));

        LineDataSet lineDataSet = new LineDataSet(entries, "저장 몸무게");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(2);
        lineDataSet.setCircleColor(Color.parseColor("#0000FF"));
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#0000FF"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();

        MyMarkerView marker = new MyMarkerView(view.getContext(),R.layout.markerview);
        marker.setChartView(lineChart);
        lineChart.setMarker(marker);

        lineChart3 = (LineChart) view.findViewById(R.id.chart);

        inputWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Entry> entries3 = new ArrayList<>();
                entries3.add(new Entry(1, (float) 5.5));
                entries3.add(new Entry(2, (float) 6.6));
                entries3.add(new Entry(3, (float) 7.4));
                entries3.add(new Entry(4, (float) 8.0));
                entries3.add(new Entry(5, (float) 8.5));

                LineData chartData = new LineData();
                LineDataSet set1 = new LineDataSet(entries,"저장 몸무게");
                set1.setLineWidth(2);
                set1.setCircleRadius(2);
                set1.setDrawCircleHole(true);
                set1.setDrawCircles(true);
                set1.setCircleColor(Color.parseColor("#0000FF"));
                set1.setCircleColorHole(Color.BLUE);
                set1.setColor(Color.parseColor("#0000FF"));
                set1.setDrawHorizontalHighlightIndicator(false);
                set1.setDrawHighlightIndicators(false);
                set1.setDrawValues(false);
                chartData.addDataSet(set1);

                LineDataSet set2 = new LineDataSet(entries3,"입력 몸무게");
                set2.setLineWidth(2);
                set2.setCircleRadius(2);
                set2.setCircleColor(Color.parseColor("#FF0000"));
                set2.setCircleColorHole(Color.RED);
                set2.setColor(Color.parseColor("#FF0000"));
                set2.setDrawCircleHole(true);
                set2.setDrawCircles(true);
                set2.setDrawHorizontalHighlightIndicator(false);
                set2.setDrawHighlightIndicators(false);
                set2.setDrawValues(false);
                chartData.addDataSet(set2);
                lineChart.setData(chartData);

                lineChart.invalidate();

                XAxis xAxis3 = lineChart3.getXAxis();
                xAxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis3.setTextColor(Color.BLACK);
                xAxis3.enableGridDashedLine(8, 24, 0);

                YAxis yLAxis3 = lineChart3.getAxisLeft();
                yLAxis3.setTextColor(Color.BLACK);

                YAxis yRAxis3 = lineChart.getAxisRight();
                yRAxis3.setDrawAxisLine(false);
                yRAxis3.setDrawLabels(false);
                yRAxis3.setDrawGridLines(false);
                Description description3 = new Description();
                description3.setText("");

                lineChart3.setDrawGridBackground(false);
                lineChart3.setDoubleTapToZoomEnabled(false);
                lineChart3.setDescription(description);
                lineChart3.animateY(2000, Easing.EasingOption.EaseInCubic);
                lineChart3.invalidate();
            }
        });

        MyMarkerView marker3 = new MyMarkerView(view.getContext(),R.layout.markerview);
        marker3.setChartView(lineChart3);
        lineChart3.setMarker(marker);

        //신장
        inputHeightButton = view.findViewById(R.id.inputHeightButton);
        lineChart2 = (LineChart) view.findViewById(R.id.chart2);

        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(1, (float) 54.7));
        entries2.add(new Entry(2, (float) 58.4));
        entries2.add(new Entry(3, (float) 61.4));
        entries2.add(new Entry(4, (float) 63.9));
        entries2.add(new Entry(5, (float) 65.9));
        entries2.add(new Entry(6, (float) 67.6));
        entries2.add(new Entry(7, (float) 69.2));
        entries2.add(new Entry(8, (float) 70.6));
        entries2.add(new Entry(9, (float) 72.0));
        entries2.add(new Entry(10, (float) 73.3));
        entries2.add(new Entry(11, (float) 74.5));
        entries2.add(new Entry(12, (float) 75.7));
        entries2.add(new Entry(13, (float) 76.9));
        entries2.add(new Entry(14, (float) 78.0));
        entries2.add(new Entry(15, (float) 79.1));
        entries2.add(new Entry(16, (float) 80.2));
        entries2.add(new Entry(17, (float) 81.2));
        entries2.add(new Entry(18, (float) 82.3));
        entries2.add(new Entry(19, (float) 83.2));
        entries2.add(new Entry(20, (float) 84.2));
        entries2.add(new Entry(21, (float) 85.1));
        entries2.add(new Entry(22, (float) 86.0));
        entries2.add(new Entry(23, (float) 86.9));
        entries2.add(new Entry(24, (float) 87.1));

        LineDataSet lineDataSet2 = new LineDataSet(entries2, "저장 키");
        lineDataSet2.setLineWidth(2);
        lineDataSet2.setCircleRadius(2);
        lineDataSet2.setCircleColor(Color.parseColor("#0000FF"));
        lineDataSet2.setCircleColorHole(Color.BLUE);
        lineDataSet2.setColor(Color.parseColor("#0000FF"));
        lineDataSet2.setDrawCircleHole(true);
        lineDataSet2.setDrawCircles(true);
        lineDataSet2.setDrawHorizontalHighlightIndicator(false);
        lineDataSet2.setDrawHighlightIndicators(false);
        lineDataSet2.setDrawValues(false);

        LineData lineData2 = new LineData(lineDataSet2);
        lineChart2.setData(lineData2);

        XAxis xAxis2 = lineChart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setTextColor(Color.BLACK);
        xAxis2.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis2 = lineChart2.getAxisLeft();
        yLAxis2.setTextColor(Color.BLACK);

        YAxis yRAxis2 = lineChart2.getAxisRight();
        yRAxis2.setDrawLabels(false);
        yRAxis2.setDrawAxisLine(false);
        yRAxis2.setDrawGridLines(false);

        Description description2 = new Description();
        description2.setText("");

        lineChart2.setDoubleTapToZoomEnabled(false);
        lineChart2.setDrawGridBackground(false);
        lineChart2.setDescription(description2);
        lineChart2.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart2.invalidate();

        MyMarkerView marker2 = new MyMarkerView(view.getContext(),R.layout.markerview);
        marker2.setChartView(lineChart2);
        lineChart2.setMarker(marker2);

        lineChart4 = (LineChart) view.findViewById(R.id.chart2);

        inputHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Entry> entries4 = new ArrayList<>();
                entries4.add(new Entry(1, (float) 55.7));
                entries4.add(new Entry(2, (float) 59.4));
                entries4.add(new Entry(3, (float) 62.4));
                entries4.add(new Entry(4, (float) 64.9));
                entries4.add(new Entry(5, (float) 66.9));

                LineData chartData2 = new LineData();
                LineDataSet set3 = new LineDataSet(entries2, "저장 키");
                set3.setLineWidth(2);
                set3.setCircleRadius(2);
                set3.setDrawCircleHole(true);
                set3.setDrawCircles(true);
                set3.setCircleColor(Color.parseColor("#0000FF"));
                set3.setCircleColorHole(Color.BLUE);
                set3.setColor(Color.parseColor("#0000FF"));
                set3.setDrawHorizontalHighlightIndicator(false);
                set3.setDrawHighlightIndicators(false);
                set3.setDrawValues(false);
                chartData2.addDataSet(set3);

                LineDataSet set4 = new LineDataSet(entries4, "입력 키");
                set4.setLineWidth(2);
                set4.setCircleRadius(2);
                set4.setCircleColor(Color.parseColor("#FF0000"));
                set4.setCircleColorHole(Color.RED);
                set4.setColor(Color.parseColor("#FF0000"));
                set4.setDrawCircleHole(true);
                set4.setDrawCircles(true);
                set4.setDrawHorizontalHighlightIndicator(false);
                set4.setDrawHighlightIndicators(false);
                set4.setDrawValues(false);
                chartData2.addDataSet(set4);
                lineChart4.setData(chartData2);

                lineChart4.invalidate();

                XAxis xAxis3 = lineChart4.getXAxis();
                xAxis3.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis3.setTextColor(Color.BLACK);
                xAxis3.enableGridDashedLine(8, 24, 0);

                YAxis yLAxis3 = lineChart4.getAxisLeft();
                yLAxis3.setTextColor(Color.BLACK);

                YAxis yRAxis3 = lineChart4.getAxisRight();
                yRAxis3.setDrawLabels(false);
                yRAxis3.setDrawAxisLine(false);
                yRAxis3.setDrawGridLines(false);

                Description description3 = new Description();
                description3.setText("");

                lineChart4.setDoubleTapToZoomEnabled(false);
                lineChart4.setDrawGridBackground(false);
                lineChart4.setDescription(description);
                lineChart4.animateY(2000, Easing.EasingOption.EaseInCubic);
                lineChart4.invalidate();
            }
        });

        MyMarkerView marker4 = new MyMarkerView(view.getContext(),R.layout.markerview);
        marker4.setChartView(lineChart4);
        lineChart4.setMarker(marker);

        /*
        //이전 액티비티에서 전달한 값 불러오기
        Intent intent = getIntent();

        cm = intent.getExtras().getString("cm").toString();
        kg = intent.getExtras().getString("kg").toString();
        month2 = intent.getExtras().getString("month2").toString();

        txtResult = view.findViewById(R.id.txtResult);
        txtResult.setText("키 :  " + cm + ",  몸무게 : " + kg + ",  개월수 : " + month2);
        */

        return view;
    }

    public String diffMonth(String startDate,String type){
        long allMonth = 0;
        long diffDays = 0;
        try {
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            String endDate = fm.format(System.currentTimeMillis());
            Date sDate = fm.parse(startDate);
            Date eDate = fm.parse(endDate);
            int sd2 = sDate.getMonth();

            long diff = eDate.getTime() - sDate.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000) ;

            long difMonth = (diffDays+1)/30;
            long chkNum = 0;
            int j=0;

            for(int i=sd2; j<difMonth; i++) {
                if(i==1 || i==3 || i==5 || i==7 || i==8 || i==10 || i==12) {
                    chkNum += 31;
                }else if(i==4 || i==6 || i==9 || i==11) {
                    chkNum += 30;
                }
                if(i==2) {
                    //윤달체크
                    if((sd2%400) == 0) {
                        chkNum+=29;
                    } else {
                        chkNum+=28;
                    }
                }
                j++;
                if(i>12) { i=1; j=j-1;}
            }
            allMonth = (chkNum+1)/30;

            if(diffDays < chkNum) {
                allMonth = allMonth-1;
            }

            System.out.println("날짜차이 =" + diffDays);
            System.out.println("총 차이  =" + chkNum);
            System.out.println("개월수 =" + difMonth);
            System.out.println("진짜개월수 =" + allMonth);

        } catch (Exception e) {

        }
        if(type.equals("month")) return Long.toString(allMonth);
        else return Long.toString(diffDays);
    }
}
