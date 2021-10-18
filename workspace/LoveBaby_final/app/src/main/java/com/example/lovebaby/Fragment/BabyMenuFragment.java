package com.example.lovebaby.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.lovebaby.MainActivity;
import com.example.lovebaby.Model.BabyInfoModel;
import com.example.lovebaby.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BabyMenuFragment extends Fragment {
    private TextView textView_babyname, textView_birth;
    private ImageView imageView, imageView_vaccine,imageView_diary,imageView_hw,imageView_sleep_timer;
    private String babyName;
    private Button vaccine, mydiary, sleepTimer;
    private String birthday;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_babymenu,container,false);
        textView_babyname = (TextView)view.findViewById(R.id.babymenu_babyname);
        textView_birth = (TextView)view.findViewById(R.id.babymenu_birth);
        imageView = (ImageView)view.findViewById(R.id.babymenu_img);
        imageView_diary = (ImageView)view.findViewById(R.id.babymenu_diary);
        imageView_hw = (ImageView) view.findViewById(R.id.babymenu_hw);
        imageView_sleep_timer = (ImageView) view.findViewById(R.id.babymenu_sleep_timer);
        imageView_vaccine = (ImageView) view.findViewById(R.id.babymenu_vaccine);

        imageView_vaccine.setOnClickListener(onClickListener);
        imageView_hw.setOnClickListener(onClickListener);
        imageView_sleep_timer.setOnClickListener(onClickListener);
        imageView_diary.setOnClickListener(onClickListener);

        Bundle bundle = getArguments();
        if(bundle != null) babyName = bundle.getString("babyname");

        FirebaseDatabase.getInstance().getReference("babyinfo").orderByChild("name").equalTo(babyName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                birthday = snapshot.getValue(BabyInfoModel.class).birthday;
                textView_birth.setText(diffMonth(birthday,"month")+"개월,  "+diffMonth(birthday,"")+"일");
                textView_babyname.setText(babyName);
                Glide.with(getContext()).load(snapshot.getValue(BabyInfoModel.class).imageUrl).into(imageView);
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

        return view;
    }

    public String diffMonth(String startDate,String type){
        long allMonth = 0;
        long diffDays = 0;
        try {
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            String endDate = fm.format(System.currentTimeMillis());
            Date sDate = fm.parse(startDate);
            Date eDate = fm.parse(endDate);
            int sd2 = sDate.getMonth();

            long diff = eDate.getTime() - sDate.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000) ;

            long difMonth = (diffDays+1)/30;
            long chkNum = 0;
            int j=0;

            for(int i=sd2; j<difMonth; i++) {
                if(i==1 || i==3 || i==5 || i==7 || i==8 || i==10 || i==12) {
                    chkNum += 31;
                }else if(i==4 || i==6 || i==9 || i==11) {
                    chkNum += 30;
                }
                if(i==2) {
                    //윤달체크
                    if((sd2%400) == 0) {
                        chkNum+=29;
                    } else {
                        chkNum+=28;
                    }
                }
                j++;
                if(i>12) { i=1; j=j-1;}
            }
            allMonth = (chkNum+1)/30;

            if(diffDays < chkNum) {
                allMonth = allMonth-1;
            }

            System.out.println("날짜차이 =" + diffDays);
            System.out.println("총 차이  =" + chkNum);
            System.out.println("개월수 =" + difMonth);
            System.out.println("진짜개월수 =" + allMonth);

        } catch (Exception e) {

        }
        if(type.equals("month")) return Long.toString(allMonth);
        else return Long.toString(diffDays);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle(2);
            bundle.putString("babyName",babyName);
            bundle.putString("birthday",birthday);
            bundle.putString("startTime","");
            switch (v.getId()){
                case R.id.babymenu_hw:
                    Fragment HWFragment = new HWFragment();
                    HWFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(HWFragment);
                    break;
                case R.id.babymenu_vaccine:
                    Fragment VaccineFragment = new VaccineFragment();
                    VaccineFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(VaccineFragment);
                    break;
                case R.id.babymenu_diary:
                    Fragment MyDiaryFragment = new MyDiaryFragment();
                    MyDiaryFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(MyDiaryFragment);
                    break;
                case R.id.babymenu_sleep_timer:
                    Fragment SleepTimerFragment = new SleepTimerFragment();
                    SleepTimerFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(SleepTimerFragment);
                    break;
            }
        }
    };
}
