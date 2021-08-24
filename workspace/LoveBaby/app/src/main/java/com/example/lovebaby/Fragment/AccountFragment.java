package com.example.lovebaby.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lovebaby.LoginActivity;
import com.example.lovebaby.Model.NotificationModel;
import com.example.lovebaby.Model.UserModel;
import com.example.lovebaby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountFragment extends Fragment {
    private FirebaseAuth auth;
    private Button btn_logout,btn_sharingmsg;
    private EditText editText;
    private UserModel destinationuserModel;
    private TextView textView_uid;
    private Context context;
    private String token;
    private String myuid;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        auth = FirebaseAuth.getInstance();
        context = container.getContext();
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        btn_logout = (Button)view.findViewById(R.id.account_btn_logout);
        btn_sharingmsg = (Button)view.findViewById(R.id.account_sharing_message);
        textView_uid = (TextView)view.findViewById(R.id.account_myuid);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(v.getContext(),LoginActivity.class));
                Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
            }
        });
        textView_uid.setText(auth.getCurrentUser().getUid());

        btn_sharingmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = new EditText(getActivity().getApplicationContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("함께 할 id를 입력하세요.").setView(editText).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("users").orderByChild("nickname").equalTo(editText.getText().toString()).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                                System.out.println("되나");
                                token = snapshot.getValue(UserModel.class).pushToken;
                                sendGcm(token, myuid);
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

                    }
                }).setNegativeButton("취소",null);
                builder.show();
            }
        });

        return view;
    }

    void sendGcm(String token, String sourceuid){
        Gson gson = new Gson();
        System.out.println("토큰 "+token);

        String userNickname = auth.getCurrentUser().getDisplayName();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = token;

        notificationModel.notification.title = userNickname+"의 아이정보 공유신청입니다.";
        notificationModel.notification.body = "ㅎㅎ";
        notificationModel.notification.click_action = "ForShareActivity";
        notificationModel.notification.sourceuid = sourceuid;
        notificationModel.data .title = userNickname+"의 아이정보 공유신청입니다.";
        notificationModel.data.body = "ㅎㅎ";
        notificationModel.data.click_action = "ForShareActivity";
        notificationModel.data.sourceuid = sourceuid;

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"),gson.toJson(notificationModel));

        Request request = new Request.Builder().header("Content-Type","application/json")
                .addHeader("Authorization","key=AAAAfVHXODI:APA91bEA4wJSY35TwloVOUkOotLCvSqWwj2sL4xukzV3iHVPf3VAueAkypHu8knwfpcdZYsVmtnZC5FNh4A7dhv8aixeNeiM5jH5atpKxTsOnlQRQnIXDu0Vnl4AibGkRxYIlHIu3Rxx")
                .url("https://fcm.googleapis.com/fcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });
    }
}
