package com.example.lovebaby.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class HWFragment extends Fragment {

    private TextView textView_babyname, textView_birth;
    private String babyName;
    private String birthday;

    EditText cm,kg,month2;
    Button graphbutton, exitbutton, tableButton;

    private static final int CALL_REQUEST = 1;

    //입력 부분 제작 예정
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hw,container,false);

        textView_babyname = (TextView)view.findViewById(R.id.babymenu_babyname);
        textView_birth = (TextView)view.findViewById(R.id.babymenu_birth);

        cm = (EditText) view.findViewById(R.id.cm);
        kg = (EditText)view.findViewById(R.id.kg);
        month2 = (EditText)view.findViewById(R.id.month2);

        graphbutton = (Button) view.findViewById(R.id.graphbutton);
        tableButton = (Button) view.findViewById(R.id.tableButton);
        exitbutton = (Button) view.findViewById(R.id.exitbutton);

        graphbutton.setOnClickListener(onClickListener);
        tableButton.setOnClickListener(onClickListener);
        exitbutton.setOnClickListener(onClickListener);

        Bundle bundle = getArguments();
        if(bundle != null){
            babyName = bundle.getString("babyname");
            birthday = bundle.getString("birthday");
        }

        FirebaseDatabase.getInstance().getReference("babyinfo").orderByChild("name").equalTo(babyName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                birthday = snapshot.getValue(BabyInfoModel.class).birthday;
                textView_birth.setText(diffMonth(birthday,"month")+"개월,  "+diffMonth(birthday,"")+"일");
                textView_babyname.setText(babyName);
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle(2);
            bundle.putString("babyName",babyName);
            bundle.putString("birthday",birthday);
            switch (v.getId()){
                case R.id.graphbutton:
                    Fragment GraphFragment = new GraphFragment();
                    GraphFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(GraphFragment);
                    break;
                case R.id.tableButton:
                    Fragment TableFragment = new TableFragment();
                    TableFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(TableFragment);
                    break;
                case R.id.exitbutton:
                    Fragment exitFragment = new BabyMenuFragment();
                    exitFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment(exitFragment);
                    break;
            }
        }
    };

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                Toast.makeText(getView().getContext(), " 종료되셨습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
