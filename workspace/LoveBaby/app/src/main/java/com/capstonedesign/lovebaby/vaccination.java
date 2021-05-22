package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
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

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccination);  //현재 사용 xml = vaccination.xml
        final ArrayList<String> midList = new ArrayList<String>();
        listView = findViewById(R.id.listView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList);
        listView.setAdapter(adapter);

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
    }
}