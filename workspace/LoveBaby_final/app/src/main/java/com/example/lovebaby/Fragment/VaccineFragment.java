package com.example.lovebaby.Fragment;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.lovebaby.AlarmBroadcastReceiver;
import com.example.lovebaby.AppDatabase;
import com.example.lovebaby.Model.Alarm;
import com.example.lovebaby.Model.VaccineModel;
import com.example.lovebaby.R;
import com.example.lovebaby.VaccineMapActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;


public class VaccineFragment extends Fragment {
    private String babyName,birthday;
    private Button vaccinemap;
    private String[] vaccinename,vaccine_state;
    private int[] vaccinedate,vaccineterm;
    private RecyclerView recyclerView;
    private TextView textview_name, textview_date;
    private Dialog dialog,datepick_dialog,timepick_dialog;
    private EditText dialog_facname;
    private DatePicker datePicker;
    private TimePicker timePicker;
    int hour,nMinute;
    int nyear,nmonthOfYear, ndayOfMonth;
    private RadioGroup radioGroup;
    private Calendar calendar;
    private List<VaccineModel> vaccineModels = new ArrayList<>();
    private List<String> uidlist = new ArrayList<>();
    private AlarmManager alarmMgr;
    private PendingIntent pendingIntent;
    private AppDatabase db;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccine,container,false);
        recyclerView = view.findViewById(R.id.vaccine_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

        db = Room.databaseBuilder(getActivity(),AppDatabase.class,"alarmdb")
                .allowMainThreadQueries()
                .build();

        vaccinemap = (Button)view.findViewById(R.id.vaccine_map);
        textview_name = (TextView)view.findViewById(R.id.vaccine_babyname);
        textview_date = (TextView)view.findViewById(R.id.vaccine_baby_age);
        dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_vaccine);
        datepick_dialog = new Dialog(view.getContext());
        datepick_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datepick_dialog.setContentView(R.layout.dialog_datepicker);
        timepick_dialog = new Dialog(view.getContext());
        timepick_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        timepick_dialog.setContentView(R.layout.dialog_timepicker);
        dialog_facname = (EditText)dialog.findViewById(R.id.dialog_facility_name);
        datePicker = (DatePicker)datepick_dialog.findViewById(R.id.vDatePicker);
        timePicker = (TimePicker)timepick_dialog.findViewById(R.id.TimePicker);
        radioGroup = (RadioGroup)dialog.findViewById(R.id.radio_vaccine_state);

        Bundle bundle = getArguments();
        if(bundle != null) {
            babyName = bundle.getString("babyName");
            birthday = bundle.getString("birthday");
        }
        textview_name.setText(babyName);

        BabyMenuFragment fragment = new BabyMenuFragment();
        textview_date.setText(fragment.diffMonth(birthday,"month")+"개월");

        vaccinemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VaccineMapActivity.class));
            }
        });

        vaccinename = new String[]{"B형간염 1차", "결핵-BCG(경피용)", "결핵-BCG(피내용)", "B형간염 2차", "DTaP 1차", "Hib 뇌수막염 1차", "폐렴구균 1차"
                , "폴리오 1차", "DTaP 2차", "Hib 뇌수막염 2차", "페렴구균 2차", "폴리오 2차", "B형간염 3차", "DTaP 3차", "Hib 뇌수막염 3차", "폐렴구균 3차", "폴리오 3차"
                , "Hib 뇌수막염 추가 4차", "MMR 1차", "수두 1회", "일본뇌염 사백신 1차","일본뇌염 생백신 1차","페렴구균 추가 4차","일본뇌염 사백신 2차"
                ,"DTaP 추가 4차","일본뇌염 생백신 2차","일본뇌염 사백신 3차","DTaP 추가 5차","MMR 2차","폴리오 추가 4차","일본뇌염 사백신 추가 4차","Td/Tdap 추가 6차"
                ,"일본뇌염 사백신 추가 5차"};
        vaccinedate = new int[]{0,0,0,1,2,2,2,2,4,4,4,6,6,6,6,6,6,12,12,12,12,12,12,13,15,24,25,60,60,60,84,144,156};//일본뇌염 사백신 3차까지
        vaccineterm= new int[]{0,1,1,1,2,2,2,2,2,2,2,6,6,6,6,6,6,4,4,6,1,13,4,13,9,13,12,36,36,36,12,24,24};
        vaccine_state = new String[vaccinename.length];
        for (int i=0;i<vaccine_state.length;i++)vaccine_state[i]="미접종";

        FirebaseDatabase.getInstance().getReference("vaccine").addValueEventListener(new ValueEventListener() {
            //데이터가 바뀔때미디 데이터가 넘어감 (옵저버 패턴)
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                vaccineModels.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    VaccineModel vaccineModel = snapshot.getValue(VaccineModel.class);
                    String uidKey = snapshot.getKey();

                    vaccineModels.add(0,vaccineModel);
                    uidlist.add(uidKey);
                }
                //oldestPostId = uidlist.get(0);
                boardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            String fac_name = data.getStringExtra("facname");
            dialog_facname.setText(fac_name);
        }
    }

    class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vaccine,parent,false);
            return new BoardRecyclerViewAdapter.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            vaccineModels.clear();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String date = getDate(birthday,vaccinedate[position]);
            String date2 = getDate(date,vaccineterm[position]);
            ((CustomViewHolder)holder).vaccinename.setText(vaccinename[position]);
            ((CustomViewHolder)holder).vaccineddate.setText(date+"~"+date2);
            ((CustomViewHolder)holder).vaccinestate.setText(vaccine_state[position]);
            FirebaseDatabase.getInstance().getReference("vaccine").orderByChild("uidandvaccinename").equalTo(uid+","+vaccinename[position]).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                    String[] strary = snapshot.getValue(VaccineModel.class).uidandvaccinename.split(",");
                    String state = snapshot.getValue(VaccineModel.class).reserve_state;
                    if(state.equals("예약중") && vaccinename[position].equals(strary[1])) {
                        vaccine_state[position] = "예약중";
                    }
                    else if(state.equals("접종")) vaccine_state[position] = "접종";
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

        @Override
        public int getItemCount() {
            return vaccinename.length;
        }
        private class CustomViewHolder extends RecyclerView.ViewHolder{
            TextView vaccinename;
            TextView vaccineddate;
            TextView vaccinestate;
            public CustomViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                this.vaccinename = (TextView)itemView.findViewById(R.id.vaccine_name);
                this.vaccineddate = (TextView)itemView.findViewById(R.id.vaccine_date);
                this.vaccinestate = (TextView) itemView.findViewById(R.id.vaccine_state);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(vaccinename.getText().toString());
                    }
                });
            }
        }
    }
    public String getDate(String birthday,int month){
        try {
            Calendar cal = Calendar.getInstance();

            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            Date birth = fm.parse(birthday);
            cal.setTime(birth);
            cal.add(Calendar.MONTH, month);
            return fm.format(cal.getTime());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void showDialog(String vaccine_name){
        EditText edit_date = dialog.findViewById(R.id.edittext_alarm_date);
        EditText edit_time = dialog.findViewById(R.id.edittext_alarm_time);
        EditText edit_fac = dialog.findViewById(R.id.dialog_facility_name);
        edit_date.setText("");
        edit_time.setText("");
        edit_fac.setText("");
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton)dialog.findViewById(id);
        calendar = Calendar.getInstance();
        dialog.show();
        dialog.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //알람/db저장
                int hour, hour_24, minute;
                String am_pm;
                String str = edit_time.getText().toString();
                String[] strary = str.split(":");
                Log.d("시",strary[0]);
                Log.d("분",strary[1]);

                if (Build.VERSION.SDK_INT >= 23){
                    hour_24 = Integer.parseInt(strary[0]);
                    minute = Integer.parseInt(strary[1]);
                }
                else{
                    hour_24 = Integer.parseInt(strary[0]);
                    minute = Integer.parseInt(strary[1]);
                }
                if(hour_24 > 12) {
                    am_pm = "PM";
                    hour = hour_24 - 12;
                }
                else {
                    hour = hour_24;
                    am_pm="AM";
                }

                String[] datearray = edit_date.getText().toString().split("-");
                String description = edit_fac.getText().toString()+"에서 "+vaccine_name+"예약하신 날입니다.";
                //알람
                alarmBroadcastReceiver(Integer.parseInt(datearray[0]), Integer.parseInt(datearray[1]), Integer.parseInt(datearray[2]), hour_24,minute,description);
                //파이어베이스에 state 저장
                createVaccineTable(vaccine_name,edit_date.getText().toString(),edit_time.getText().toString(),edit_fac.getText().toString(),rb.getText().toString());
                //로컬db에 알람저장
                addAlarmToRoom(FirebaseAuth.getInstance().getCurrentUser().getUid(),Integer.parseInt(datearray[0]), Integer.parseInt(datearray[1]), Integer.parseInt(datearray[2]), hour, minute);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.noBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edit_fac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),VaccineMapActivity.class);
                intent.putExtra("getName","y");
                startActivityForResult(intent,0);
            }
        });
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepick_dialog.show();
                datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                        new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        nyear = year;
                        ndayOfMonth = dayOfMonth;
                        nmonthOfYear = monthOfYear;
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,monthOfYear);
                        calendar.set(Calendar.DATE,dayOfMonth);
                    }
                });
                datepick_dialog.findViewById(R.id.datepick_yesBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nmonthOfYear +=1;
                        edit_date.setText(nyear+"-"+nmonthOfYear+"-"+ndayOfMonth);
                        datepick_dialog.dismiss();
                    }
                });
                datepick_dialog.findViewById(R.id.datepick_noBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timepick_dialog.show();
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        hour = hourOfDay;
                        nMinute = minute;
                    }
                });
                timepick_dialog.findViewById(R.id.timepick_yesBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit_time.setText(hour+":"+nMinute);
                        timepick_dialog.dismiss();
                    }
                });
                timepick_dialog.findViewById(R.id.timepick_noBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timepick_dialog.dismiss();
                    }
                });
            }
        });
    }

    public void createVaccineTable(String vaccinename, String date, String time, String facilityname, String reserve_state){
        VaccineModel vaccineModel = new VaccineModel();
        vaccineModel.uidandvaccinename = FirebaseAuth.getInstance().getCurrentUser().getUid()+","+vaccinename;
        vaccineModel.date = date;
        vaccineModel.facilityname = facilityname;
        vaccineModel.time = time;
        vaccineModel.reserve_state = reserve_state;
        FirebaseDatabase.getInstance().getReference("vaccine").push().setValue(vaccineModel);
    }

    public void alarmBroadcastReceiver(int year, int month, int date, int hour, int minute, String description) {
        Intent alarmBroadcastReceiverintent = new Intent(getActivity(), AlarmBroadcastReceiver.class);
        alarmBroadcastReceiverintent.putExtra("title","LoveBaby 백신알람");
        alarmBroadcastReceiverintent.putExtra("descript",description);

        //alarmid 를 함수 파라미터로 받음
        //db를 만들어 count를 alarmid로 씀
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmBroadcastReceiverintent, FLAG_UPDATE_CURRENT);

        alarmMgr = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        // Set the alarm to start at a particular time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DATE,date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND,0);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void addAlarmToRoom(String uid, int year, int month, int date, int hour, int minute){
        db.alarmDAO().insertAll(new Alarm(uid, year, month, date, hour, minute));
    }
}