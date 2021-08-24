package com.example.lovebaby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.lovebaby.Fragment.AccountFragment;
import com.example.lovebaby.Fragment.ChatFragment;
import com.example.lovebaby.Fragment.DiaryFragment;
import com.example.lovebaby.Fragment.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.mainactivity_bottonnavigationview);
        getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_framelayout, new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new HomeFragment()).commit(); break;
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new DiaryFragment()).commit(); break;
                    case R.id.navigation_chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new ChatFragment()).commit(); break;
                    case R.id.navigation_account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, new AccountFragment()).commit(); break;
                }
                return true;
            }
        });
        passPushTokenToServer();
    }

    public void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout, fragment).commit();
    }
    void passPushTokenToServer(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<String> task) {
                if (!task.isSuccessful()) return;
                String token = task.getResult();
                Map<String,Object> map = new HashMap<>();
                map.put("pushToken",token);
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(map);
            }
        });

    }
}