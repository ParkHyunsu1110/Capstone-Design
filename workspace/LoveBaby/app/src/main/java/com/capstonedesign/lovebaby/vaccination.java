package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class vaccination extends  Activity {

    ListView listView,listView2;
    Button vaccinationBtn, btnBack2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccination);  //현재 사용 xml = vaccination.xml
        final ArrayList<String> midList = new ArrayList<String>();
        final ArrayList<String> midList2 = new ArrayList<String>();
        listView = findViewById(R.id.listView);
        listView2 = findViewById(R.id.listView2);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList);
        listView.setAdapter(adapter);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList2);
        listView2.setAdapter(adapter2);

        final EditText edtItem = (EditText) findViewById(R.id.MTtext);
        Button btnAdd = findViewById(R.id.addbtn);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "메모입력.", Toast.LENGTH_SHORT).show();
                midList.add(edtItem.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "메모삭제.", Toast.LENGTH_SHORT).show();
                midList.remove(position);
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        final EditText edtItem2 = (EditText) findViewById(R.id.MTtext2);
        Button btnAdd2 = findViewById(R.id.addbtn2);
        btnAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "메모입력.", Toast.LENGTH_SHORT).show();
                midList2.add(edtItem2.getText().toString());
                adapter2.notifyDataSetChanged();
            }
        });
        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "메모삭제.", Toast.LENGTH_SHORT).show();
                midList2.remove(position);
                adapter2.notifyDataSetChanged();

                return false;
            }
        });

        vaccinationBtn = findViewById(R.id.vaccinationBtn);

        vaccinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://nip.kdca.go.kr/irgd/index.html"));
                startActivity(intent);
            }
        });

        btnBack2 = findViewById(R.id.btnBack2);

        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}