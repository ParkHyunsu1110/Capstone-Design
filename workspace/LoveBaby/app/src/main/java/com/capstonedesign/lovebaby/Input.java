package com.capstonedesign.lovebaby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Input extends Activity implements OnClickListener {

    EditText cm,kg,month2;
    Button inputbutton, exitbutton, tableButton;

    private static final int CALL_REQUEST = 1;

    //입력 부분 제작 예정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);  //현재 사용 xml = input.xml
        cm = (EditText)findViewById(R.id.cm);
        kg = (EditText)findViewById(R.id.kg);
        month2 = (EditText)findViewById(R.id.month2);

        inputbutton = (Button)findViewById(R.id.inputbutton);
        exitbutton = (Button)findViewById(R.id.exitbutton);
        tableButton = (Button)findViewById(R.id.tableButton);

        inputbutton.setOnClickListener(this);
        exitbutton.setOnClickListener(this);
        tableButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.inputbutton) {
            Intent intent = new Intent(this, Graph.class);
            // 값을 받을 액티비티로 보낼 값을 지정
            //(key, value 한 쌍으로 이루어진 임의 타입의 정보를 원하는 개수만큼 전달할수있음)
            intent.putExtra("cm", cm.getText().toString());
            intent.putExtra("kg", kg.getText().toString());
            intent.putExtra("month2", month2.getText().toString());
            //리턴값을 돌려주는 액티비티 호출
            startActivityForResult(intent, CALL_REQUEST);
        }
        else if (v.getId() == R.id.tableButton) {
            Intent intent = new Intent(this, Table.class);

            intent.putExtra("cm", cm.getText().toString());
            intent.putExtra("kg", kg.getText().toString());
            intent.putExtra("month2", month2.getText().toString());
            //리턴값을 돌려주는 액티비티 호출
            startActivityForResult(intent, CALL_REQUEST);
        }
        else {
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //호출된 액티비티가 종료시 onActivityResult 메소드 호출
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CALL_REQUEST) {
            if(resultCode==RESULT_OK) {

                cm.setText("");
                kg.setText("");
                month2.setText("");

                String cm = data.getExtras().getString("cm").toString();
                String kg = data.getExtras().getString("kg").toString();
                String month2 = data.getExtras().getString("month2").toString();

                Toast.makeText(this, " 종료되셨습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
