package com.example.lovebaby;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lovebaby.Model.BabyInfoModel;
import com.example.lovebaby.Model.DayAnalysisModel;
import com.example.lovebaby.Model.SleepAnalysisModel;
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

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateBabyInfo extends AppCompatActivity {

    private EditText name, birthtime;
    private RadioGroup sex,bloodtype;
    private Button savebtn, birthday;
    private Spinner spinner_blood;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private ImageView babyImg;
    private RadioButton rb_blood, rb_sex;
    private static final int GALLARY_CODE = 10;
    private String imagePath;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_info);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        name = (EditText)findViewById(R.id.babyName);
        sex = (RadioGroup) findViewById(R.id.sex);
        birthday = (Button) findViewById(R.id.birthDay);
        birthtime = (EditText)findViewById(R.id.birthTime);
        bloodtype = (RadioGroup)findViewById(R.id.bloodType);
        savebtn = (Button)findViewById(R.id.infoSave);
        babyImg = (ImageView)findViewById(R.id.baby1);

        arrayList = new ArrayList<>(); arrayList.add("RH+"); arrayList.add("RH-");
        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner_blood = (Spinner)findViewById(R.id.spinner_blood);
        spinner_blood.setAdapter(arrayAdapter);

        //gallery 권한 주기
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);

        //이미지뷰 클릭시 이미지 업로드
        babyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLARY_CODE);
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_blood = (RadioButton)findViewById(bloodtype.getCheckedRadioButtonId());
                rb_sex = (RadioButton)findViewById(sex.getCheckedRadioButtonId());

                if(alertBlank(name.getText().toString(),birthday.getText().toString(),birthtime.getText().toString()).equals("")){
                    upload(imagePath);
                }
                else {
                    String alert = alertBlank(name.getText().toString(),birthday.getText().toString(),birthtime.getText().toString());
                    Toast.makeText(CreateBabyInfo.this,alert+"을 입력하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(year,month,dayOfMonth);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                birthday.setText(dateFormat.format(c.getTime()));
            }
        }, mYear, mMonth, mDay);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (birthday.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });


    }

    private String alertBlank(String name, String birthday, String birthtime){
        String alert="";
        //focusing 할것인가??
        if(name.equals("")) alert += "이름";
        else if(birthday.equals("")) alert += "태어난 날";
        else if(birthtime.equals("")) alert += "태어난 시간";

        return alert;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if(requestCode == GALLARY_CODE){
            imagePath = getPath(data.getData());
            File f = new File(imagePath);
            babyImg.setImageURI(Uri.fromFile(f));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getPath(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cusorloader = new CursorLoader(this, uri,proj, null,null,null);
        Cursor cursor = cusorloader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }

    private void upload(String uri) {
        StorageReference storageRef = storage.getReferenceFromUrl("gs://lovebaby-2aec2.appspot.com/");

        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("babyinfo/"+file.getLastPathSegment());
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
                            Toast.makeText(CreateBabyInfo.this, "정보가 저장되었습나다.", Toast.LENGTH_SHORT).show();

                            //파이어베이스에 데이터베이스 업로드
                            @SuppressWarnings("VisibleForTests")
                            Uri downloadUrl = task.getResult();

                            BabyInfoModel babyInfoModel = new BabyInfoModel();
                            babyInfoModel.imageUrl = downloadUrl.toString();
                            babyInfoModel.name = name.getText().toString();
                            babyInfoModel.sex = rb_sex.getText().toString();
                            babyInfoModel.birthday = birthday.getText().toString();
                            babyInfoModel.birthtime = birthtime.getText().toString();
                            babyInfoModel.bloodtype = spinner_blood.getSelectedItem().toString()+" "+rb_blood.getText().toString();
                            babyInfoModel.uid = auth.getCurrentUser().getUid();
                            babyInfoModel.userId = auth.getCurrentUser().getEmail();
                            babyInfoModel.imageName = file.getLastPathSegment();

                            database.getReference().child("babyinfo").push().setValue(babyInfoModel);
                            createAnalysisDatabase(name.getText().toString());

                            startActivity(new Intent(CreateBabyInfo.this,MainActivity.class));

                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
            }
        });
    }
    private void createAnalysisDatabase(String babyname){

        DayAnalysisModel dayAnalysisModel = new DayAnalysisModel();
        dayAnalysisModel.babyName = babyname;
        dayAnalysisModel.gaewol = 0;
        dayAnalysisModel.numNap = 0;
        dayAnalysisModel.timeNap = null;
        dayAnalysisModel.timeSleep = null;
        dayAnalysisModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("DayAnalysis").push().setValue(dayAnalysisModel);

        SleepAnalysisModel sleepAnalysisModel = new SleepAnalysisModel();
        sleepAnalysisModel.timeNap = "00:00:00";
        sleepAnalysisModel.numNap = 0;
        sleepAnalysisModel.daycnt = 0;
        sleepAnalysisModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid()+","+babyname;
        sleepAnalysisModel.gaewol = 0;
        sleepAnalysisModel.timeSleep = "00:00:00";
        FirebaseDatabase.getInstance().getReference("AnalysisSleep").push().setValue(sleepAnalysisModel);
    }
}