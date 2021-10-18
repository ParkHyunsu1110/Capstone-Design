package com.example.lovebaby;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lovebaby.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

public class SignupActivity extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 10;
    private EditText name, useremail, pw, checkpw, nickname;
    private Button btnSignup;
    private ImageView profileimg;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        profileimg = (ImageView)findViewById(R.id.userimage);
        name = (EditText)findViewById(R.id.username);
        useremail = (EditText)findViewById(R.id.userid);
        pw = (EditText)findViewById(R.id.userpw);
        checkpw = (EditText)findViewById(R.id.checkpw);
        nickname = (EditText)findViewById(R.id.usernickname);
        btnSignup = (Button)findViewById(R.id.btnsignup);

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri==null||name.getText().toString()==null||useremail.getText().toString()==null||pw.getText().toString()==null||
                        checkpw.getText().toString()==null||nickname.getText().toString()==null) return;

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(useremail.getText().toString(), pw.getText().toString())
                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            String uid = task.getResult().getUser().getUid();
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(nickname.getText().toString()).build();
                            task.getResult().getUser().updateProfile(userProfileChangeRequest);
                            FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    FirebaseStorage.getInstance().getReference().child("userImages").child(uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imguri = uri.toString();
                                            UserModel userModel = new UserModel();
                                            userModel.username = name.getText().toString();
                                            userModel.nickname = nickname.getText().toString();
                                            userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            userModel.profileImageUrl = imguri;
                                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(SignupActivity.this,"회원가입성공", Toast.LENGTH_SHORT).show();
                                                    SignupActivity.this.finish();
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if(requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK){
            profileimg.setImageURI(data.getData());
            imageUri = data.getData();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}