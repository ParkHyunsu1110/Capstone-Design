package com.example.lovebaby.Fragment;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.example.lovebaby.Model.ImageModel;
import com.example.lovebaby.Model.MyBoardModel;
import com.example.lovebaby.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class WriteDiaryFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private static final int GALLARY_CODE = 10;
    private ImageView imageView;
    private EditText title, descript;
    private Button upload_btn;
    private String imagePath;
    private CheckBox checkBox;
    private int ischecked;
    private TextView birth;
    private String birthday, babyName;
    private String gaewol1;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_write_diary,container,false);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        imageView = view.findViewById(R.id.imageView);
        title = view.findViewById(R.id.upload_pic_title);
        descript = view.findViewById(R.id.upload_pic_descript);
        upload_btn = view.findViewById(R.id.upload_pic_btn);
        checkBox = view.findViewById(R.id.private_checkBox);
        birth = view.findViewById(R.id.write_diary_birth);

        //권한 주기
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

                startActivityForResult(intent, GALLARY_CODE);
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload(imagePath);
            }
        });

        Bundle bundle = getArguments();
        if(bundle != null) {
            babyName = bundle.getString("babyName");
            birthday = bundle.getString("birthday");
        }
        BabyMenuFragment fragment = new BabyMenuFragment();
        gaewol1 = fragment.diffMonth(birthday,"month");
        birth.setText(gaewol1+"개월");


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if(requestCode == GALLARY_CODE){
            imagePath = getPath(data.getData());
            File f = new File(imagePath);
            imageView.setImageURI(Uri.fromFile(f));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cusorloader = new CursorLoader(getActivity(), uri,proj, null,null,null);
        Cursor cursor = cusorloader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }

    private void upload(String uri) {
        StorageReference storageRef = storage.getReferenceFromUrl("gs://lovebaby-2aec2.appspot.com/");

        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.

                //image url 가져오기
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return riversRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "업로드 성공", Toast.LENGTH_SHORT).show();

                            //파이어베이스에 데이터베이스 업로드
                            @SuppressWarnings("VisibleForTests")
                            Uri downloadUrl = task.getResult();
                            if(!checkBox.isChecked()) {
                                ImageModel imageModel = new ImageModel();
                                imageModel.imageUri = downloadUrl.toString();
                                imageModel.title = title.getText().toString();
                                imageModel.description = descript.getText().toString();
                                imageModel.uid = auth.getCurrentUser().getUid();
                                imageModel.userId = auth.getCurrentUser().getEmail();
                                imageModel.imageName = file.getLastPathSegment();
                                imageModel.gaewol = gaewol1;

                                //image 라는 테이블에 json 형태로 담긴다.
                                database.getReference().child("images").push().setValue(imageModel);
                            }
                            else {
                                MyBoardModel myBoardModel = new MyBoardModel();
                                myBoardModel.imageUri = downloadUrl.toString();
                                myBoardModel.title = title.getText().toString();
                                myBoardModel.description = descript.getText().toString();
                                myBoardModel.uid = auth.getCurrentUser().getUid();
                                myBoardModel.userId = auth.getCurrentUser().getEmail();
                                myBoardModel.imageName = file.getLastPathSegment();
                                myBoardModel.babyname = babyName;

                                database.getReference().child("myboard").push().setValue(myBoardModel);
                            }

                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
            }
        });
    }
}
