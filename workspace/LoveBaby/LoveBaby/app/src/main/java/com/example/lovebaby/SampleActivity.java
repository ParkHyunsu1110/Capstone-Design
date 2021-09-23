package com.example.lovebaby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        //HttpConnector thread = new HttpConnector(s);
        //thread.start();
    }
}