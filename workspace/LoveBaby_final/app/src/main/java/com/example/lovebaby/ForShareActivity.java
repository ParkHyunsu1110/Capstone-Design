package com.example.lovebaby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ForShareActivity extends AppCompatActivity {
    private String partneruid;
    private TextView textView;
    private Button btn_accept, btn_deny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_share);

        textView = (TextView)findViewById(R.id.ex_textview);
        partneruid = getIntent().getStringExtra("sourceuid");
        //textView.setText(word);
        btn_accept = (Button)findViewById(R.id.accept_share);
        btn_deny = (Button)findViewById(R.id.deny_share);

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Map<String,Object> map = new HashMap<>();
                map.put("partneruid",partneruid);
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Map<String,Object> map2 = new HashMap<>();
                        map2.put("partneruid",uid);
                        FirebaseDatabase.getInstance().getReference().child("users").child(partneruid).updateChildren(map2).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(),"sharing success",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                        });
                    }
                });
        btn_deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"deny sharing",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
                //String uid1 = word;
                //String uid2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //GroupModel groupModel = new GroupModel();
                //groupModel.member = uid1;
                //groupModel.partner_member = uid2;
                //FirebaseDatabase.getInstance().getReference().child("group").push().setValue(groupModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                //    @Override
                //    public void onSuccess(Void unused) {
                //    Toast.makeText(getApplicationContext(),"sharing success",Toast.LENGTH_SHORT).show();
                //        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                //    }
                //});
            }
        });
    }
}