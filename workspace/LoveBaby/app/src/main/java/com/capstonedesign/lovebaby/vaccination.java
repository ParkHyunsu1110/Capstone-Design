package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class vaccination extends  Activity {

    ListView listView,listView2;
    TextView next, next2;

    Button vaccinationBtn, btnBack2;

    String toDo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccination);  //현재 사용 xml = vaccination.xml

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "vaccine-db").allowMainThreadQueries().build();

        next = findViewById(R.id.next);
        next2 = findViewById(R.id.next2);

        final EditText vaccineName = findViewById(R.id.vaccineName);
        final EditText vaccineMonth = findViewById(R.id.vaccineMonth);
        final EditText vaccineDay = findViewById(R.id.vaccineDay);

        final ArrayList<String> midList = new ArrayList<String>();
        final ArrayList<String> midList2 = new ArrayList<String>();

        listView = findViewById(R.id.listView);
        listView2 = findViewById(R.id.listView2);


        // 다음 추천 백신을 알려주는 버튼
        findViewById(R.id.vaccinInput).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> arrayList = new ArrayList<>();
                Vaccine vaccine = db.vaccineDAO().getVaccine(vaccineName.getText().toString()).get(0);

                /*
                // 데이터 베이스 백식 정보 입력시 백신 이름만 입력후 버튼 클릭, 아래 코드들은 주석처리
                db.vaccineDAO().insert(new Vaccine(vaccineName.getText().toString()));
                 */
                
                // 추천백신 날짜 지정
                int month = Integer.parseInt(vaccineMonth.getText().toString());
                int day = Integer.parseInt(vaccineDay.getText().toString());
                vaccine.setMonth(month);
                vaccine.setDay(day);

                // 추천결과 저장
                arrayList = Vaccine.setVaccinationDate(db.vaccineDAO().getAll(), vaccine, month, day);

                // 내용 출력
                next.setText(arrayList.get(0));
                next2.setText(arrayList.get(1));
            }
        });

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList);
        listView.setAdapter(adapter);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList2);
        listView2.setAdapter(adapter2);

        Button btnAdd = findViewById(R.id.addbtn);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "신규 내용 저장.", Toast.LENGTH_SHORT).show();
                toDo = vaccineMonth.getText().toString() + "월" + vaccineDay.getText().toString() + "일 백신 명: " + vaccineName.getText().toString();
                midList.add(toDo);  //edtItem.getText().toString()
                adapter.notifyDataSetChanged();
            }
        });
        /*listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "접종 완료.", Toast.LENGTH_SHORT).show();
                toDo = vaccineMonth.getText().toString() + "월" + vaccineDay.getText().toString() + "일 백신 명: " + vaccineName.getText().toString();
                midList2.add(toDo);
                midList.remove(position);
                adapter.notifyDataSetChanged();

                return true;
            }
        });
      /*
        final EditText edtItem2 = (EditText) findViewById(R.id.MTtext2);
        Button btnAdd2 = findViewById(R.id.addbtn2);
        btnAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "메모입력.", Toast.LENGTH_SHORT).show();
                midList2.add(edtItem2.getText().toString());
                adapter2.notifyDataSetChanged();
            }
        });*/
        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "메모삭제.", Toast.LENGTH_SHORT).show();
                midList2.remove(position);
                adapter2.notifyDataSetChanged();

                return true;
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