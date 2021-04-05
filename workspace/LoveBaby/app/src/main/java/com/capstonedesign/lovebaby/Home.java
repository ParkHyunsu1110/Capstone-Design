package com.capstonedesign.lovebaby;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD


public class Home extends Activity {
    Button graphBtn;
=======
import android.widget.ImageButton;


public class Home extends Activity {
    ImageButton graphBtn;
>>>>>>> f4f4da15c2fa14d743588600bb58ebcaa3af3825
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        graphBtn = findViewById(R.id.graphBtn);

        graphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Graph.class);
                startActivity(intent);
            }
        });

    }
}