package com.example.lovebaby.Fragment;

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
import com.example.lovebaby.R;

import org.jetbrains.annotations.NotNull;

public class TableFragment extends Fragment {

    //변수 모음
    TextView weightText_1, heightText_1;
    TextView weightText_2, heightText_2;
    TextView weightText_3, heightText_3;
    TextView weightText_4, heightText_4;
    TextView weightText_5, heightText_5;
    TextView weightText_6, heightText_6;
    TextView weightText_7, heightText_7;
    TextView weightText_8, heightText_8;
    TextView weightText_9, heightText_9;
    TextView weightText_10, heightText_10;

    TextView weightText_11, heightText_11;
    TextView weightText_12, heightText_12;
    TextView weightText_13, heightText_13;
    TextView weightText_14, heightText_14;
    TextView weightText_15, heightText_15;
    TextView weightText_16, heightText_16;
    TextView weightText_17, heightText_17;
    TextView weightText_18, heightText_18;
    TextView weightText_19, heightText_19;

    TextView weightText_20, heightText_20;
    TextView weightText_21, heightText_21;
    TextView weightText_22, heightText_22;
    TextView weightText_23, heightText_23;
    TextView weightText_24, heightText_24;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        //테이블 몸무게, 키 TextView 변수 할당
        weightText_1 = view.findViewById(R.id.weightText_1); heightText_1 = view.findViewById(R.id.heightText_1);
        weightText_2 = view.findViewById(R.id.weightText_2); heightText_2 = view.findViewById(R.id.heightText_2);
        weightText_3 = view.findViewById(R.id.weightText_3); heightText_3 = view.findViewById(R.id.heightText_3);
        weightText_4 = view.findViewById(R.id.weightText_4); heightText_4 = view.findViewById(R.id.heightText_4);
        weightText_5 = view.findViewById(R.id.weightText_5); heightText_5 = view.findViewById(R.id.heightText_5);
        weightText_6 = view.findViewById(R.id.weightText_6); heightText_6 = view.findViewById(R.id.heightText_6);
        weightText_7 = view.findViewById(R.id.weightText_7); heightText_7 = view.findViewById(R.id.heightText_7);
        weightText_8 = view.findViewById(R.id.weightText_8); heightText_8 = view.findViewById(R.id.heightText_8);
        weightText_9 = view.findViewById(R.id.weightText_9); heightText_9 = view.findViewById(R.id.heightText_9);
        weightText_10 = view.findViewById(R.id.weightText_10); heightText_10 = view.findViewById(R.id.heightText_10);

        weightText_11 = view.findViewById(R.id.weightText_11); heightText_11 = view.findViewById(R.id.heightText_11);
        weightText_12 = view.findViewById(R.id.weightText_12); heightText_12 = view.findViewById(R.id.heightText_12);
        weightText_13 = view.findViewById(R.id.weightText_13); heightText_13 = view.findViewById(R.id.heightText_13);
        weightText_14 = view.findViewById(R.id.weightText_14); heightText_14 = view.findViewById(R.id.heightText_14);
        weightText_15 = view.findViewById(R.id.weightText_15); heightText_15 = view.findViewById(R.id.heightText_15);
        weightText_16 = view.findViewById(R.id.weightText_16); heightText_16 = view.findViewById(R.id.heightText_16);
        weightText_17 = view.findViewById(R.id.weightText_17); heightText_17 = view.findViewById(R.id.heightText_17);
        weightText_18 = view.findViewById(R.id.weightText_18); heightText_18 = view.findViewById(R.id.heightText_18);
        weightText_19 = view.findViewById(R.id.weightText_19); heightText_19 = view.findViewById(R.id.heightText_19);
        weightText_20 = view.findViewById(R.id.weightText_20); heightText_20 = view.findViewById(R.id.heightText_20);

        weightText_21 = view.findViewById(R.id.weightText_21); heightText_21 = view.findViewById(R.id.heightText_21);
        weightText_22 = view.findViewById(R.id.weightText_22); heightText_22 = view.findViewById(R.id.heightText_22);
        weightText_23 = view.findViewById(R.id.weightText_23); heightText_23 = view.findViewById(R.id.heightText_23);
        weightText_24 = view.findViewById(R.id.weightText_24); heightText_24 = view.findViewById(R.id.heightText_24);

        return view;
    }
}
