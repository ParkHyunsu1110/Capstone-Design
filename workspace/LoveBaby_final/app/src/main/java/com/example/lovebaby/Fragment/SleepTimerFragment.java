package com.example.lovebaby.Fragment;

import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lovebaby.Model.BabyInfoModel;
import com.example.lovebaby.Model.SleepAnalysisModel;
import com.example.lovebaby.Model.SleepRecordModel;
import com.example.lovebaby.TimerService;
import com.example.lovebaby.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.internal.Slashes;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class SleepTimerFragment extends Fragment {
    public static final String CHANNEL_ID = "exampleServiceChannel";
    private TextView timer,hiddentext,textView_gaewol,textView_totaltime,textView_nighttime,textView_naptime,textView_napcnt;
    private TextView textView_totaltime2,textView_nighttime2,textView_naptime2,textView_napcnt2,textView_daycnt;
    private ScrollView scrollView;
    private Button bt_sta, bt_rec, btn_selectday;
    private Boolean isRunning = true;
    private Thread thread = null;
    private RecyclerView recyclerView;
    private String babyName,birthday,gaewol;
    private Long nowDate;
    private List<SleepRecordModel> sleepRecordModels = new ArrayList<>();
    private List<SleepAnalysisModel> sleepAnalysisModels = new ArrayList<>();
    private List<String> uidlist = new ArrayList<>();
    private CheckBox checkBox;
    private int numNap;
    private String timeNap, allTime;
    private String timeSleep = "00:00:00";
    private String napTime = "00:00:00";
    private String sTime = null;
    private String myUid;
    private SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
    private int napcnt = 0;
    private String timenap, timesleep = "";
    private ImageView questionbtn;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sleep_timer,container,false);

        timer = (TextView)view.findViewById(R.id.timer);
        hiddentext = (TextView)view.findViewById(R.id.hiddentext);
        scrollView = (ScrollView)view.findViewById(R.id.scrollView);
        bt_sta = (Button)view.findViewById(R.id.bt_sta);
        bt_rec = (Button)view.findViewById(R.id.bt_rec);
        btn_selectday = (Button) view.findViewById(R.id.selectDay);
        questionbtn = (ImageView)view.findViewById(R.id.questionbtn);
        textView_gaewol = (TextView)view.findViewById(R.id.timer_gaewol);
        textView_totaltime = (TextView)view.findViewById(R.id.timer_total_time);
        textView_nighttime = (TextView)view.findViewById(R.id.timer_night_time);
        textView_naptime = (TextView)view.findViewById(R.id.timer_nap_time);
        textView_napcnt = (TextView)view.findViewById(R.id.timer_nap_cnt);
        textView_totaltime2 = (TextView)view.findViewById(R.id.timer_total_time2);
        textView_nighttime2 = (TextView)view.findViewById(R.id.timer_night_time2);
        textView_naptime2 = (TextView)view.findViewById(R.id.timer_nap_time2);
        textView_napcnt2 = (TextView)view.findViewById(R.id.timer_nap_cnt2);
        textView_daycnt = (TextView)view.findViewById(R.id.timer_daycnt);
        checkBox = (CheckBox)view.findViewById(R.id.timer_checkBox);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_record);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);
        Bundle bundle = getArguments();
        if(bundle != null) {
            birthday = bundle.getString("birthday");
            babyName = bundle.getString("babyName");
            sTime = bundle.getString("startTime");

        }
        if(!sTime.equals("")) timer.setText(sTime);
        BabyMenuFragment babyMenuFragment = new BabyMenuFragment();
        gaewol = babyMenuFragment.diffMonth(birthday,"month");
        textView_gaewol.setText(gaewol+"개월");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        bt_sta.setOnClickListener(onClickListener);
        bt_rec.setOnClickListener(onClickListener);

        createNotificationChannel();

        myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        viewRecordList(boardRecyclerViewAdapter);
        viewAnalysisSleep2();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        btn_selectday.setText(dateFormat.format(c.getTime()));

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(year,month,dayOfMonth);

                btn_selectday.setText(dateFormat.format(c.getTime()));
                textView_naptime.setText("");
                textView_nighttime.setText("");
                textView_napcnt.setText("");
                textView_totaltime.setText("");
                viewRecordList(boardRecyclerViewAdapter);
            }
        }, mYear, mMonth, mDay);



        btn_selectday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_selectday.isClickable()) {
                    datePickerDialog.show();
                }
            }
        });
        questionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQ();
            }
        });

        return view;
    }
    private void viewRecordList(BoardRecyclerViewAdapter boardRecyclerViewAdapter){
        FirebaseDatabase.getInstance().getReference("SleepRecord").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                sleepRecordModels.clear();
                uidlist.clear();
                numNap = 0;
                napTime = "00:00:00";
                timeSleep = "00:00:00";
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SleepRecordModel sleepRecordModel = snapshot.getValue(SleepRecordModel.class);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    if(sleepRecordModel.uid.equals(myUid) && sleepRecordModel.babyName.equals(babyName)
                            && dateFormat.format(sleepRecordModel.date.getTime()).equals(btn_selectday.getText().toString())){
                        String uidKey = snapshot.getKey();
                        sleepRecordModels.add(sleepRecordModel);
                        uidlist.add(uidKey);
                        SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
                        if(sleepRecordModel.isNap == true){
                            numNap++;
                            try {
                                napTime = addTime(sleepRecordModel.runningTime,napTime);
                                textView_naptime.setText(napTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            try {
                                timeSleep = addTime(sleepRecordModel.runningTime,timeSleep);
                                textView_nighttime.setText(timeSleep);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                textView_napcnt.setText(String.valueOf(numNap));
                try {
                    textView_totaltime.setText(addTime(napTime,timeSleep));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                boardRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_sta:
                    nowDate = System.currentTimeMillis();
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                    startService(babyName,sdf2.format(nowDate));
                    timer.setText(sdf2.format(nowDate));
                    //int cnt = Integer.parseInt(textView_daycnt.getText().toString());

                    break;
                case R.id.bt_rec:
                    if(sleepRecordModels.size() == 0) {
                        //cnt++;
                        updateDayCount(babyName);
                    }
                    stopService();
                    nowDate = System.currentTimeMillis();
                    nowDate = System.currentTimeMillis();

                    Boolean ischecked = false;
                    if(checkBox.isChecked()) ischecked = true;
                    updateAnalysisSleep(timer.getText().toString(),ischecked,babyName);
                    try {
                        createDatabase(babyName, new Date(nowDate), timer.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    timer.setText("시작 시간");

                    break;
            }
        }
    };

    public String timerFormat(int time){
        int sec = time % 60;
        int min = (time / 60) % 60;
        int hour = (time / 3600) % 24;
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }

    public void startService(String babyname, String starttime) {
        String time = timer.getText().toString();
        Intent serviceIntent = new Intent(getActivity(), TimerService.class);
        serviceIntent.putExtra("babyName", babyname);
        serviceIntent.putExtra("time",time);
        serviceIntent.putExtra("startTime",starttime);

        ContextCompat.startForegroundService(getActivity(), serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(getActivity(), TimerService.class);
        getActivity().stopService(serviceIntent);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private class BoardRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        @NonNull
        @NotNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record,parent,false);
            return new BoardRecyclerViewAdapter.CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
            CustomViewHolder customViewHolder = ((CustomViewHolder)holder);
            customViewHolder.textView1.setText(sleepRecordModels.get(position).startTime);
            customViewHolder.textView2.setText(sleepRecordModels.get(position).runningTime);
            if(sleepRecordModels.get(position).isNap == true) {
                customViewHolder.imageView.setImageResource(R.drawable.sun);
            }
            else customViewHolder.imageView.setImageResource(R.drawable.moon);
        }

        @Override
        public int getItemCount() {
            return sleepRecordModels.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView textView1, textView2;
            ImageView imageView;
            public CustomViewHolder(View view) {
                super(view);
                textView1 = (TextView)view.findViewById(R.id.text_duringSleep);
                textView2 = (TextView)view.findViewById(R.id.text_sleepTime);
                imageView = (ImageView)view.findViewById(R.id.image_sleep);
            }
        }
    }
    private void createDatabase(String babyname, Date date, String starttime) throws ParseException {
        SleepRecordModel sleepRecordModel = new SleepRecordModel();
        sleepRecordModel.date = date;
        sleepRecordModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        sleepRecordModel.startTime = starttime;
        sleepRecordModel.babyName = babyname;
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        sleepRecordModel.runningTime = diffTime(sdf2.format(new Date(System.currentTimeMillis())),starttime);

        if(checkBox.isChecked()) sleepRecordModel.isNap = true;
        else sleepRecordModel.isNap = false;
        FirebaseDatabase.getInstance().getReference("SleepRecord").push().setValue(sleepRecordModel);
    }

    private String addTime(String d1, String d2) throws ParseException {
        int hour = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat f2 = new SimpleDateFormat("mm:ss");
        Date date = simpleDateFormat.parse(d1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String[] strArr = d2.split(":");
        String[] strArr2 = d1.split(":");
        if(Integer.parseInt(strArr[1])+Integer.parseInt(strArr2[1]) >= 60){
            hour++;
        }
        cal.add(Calendar.SECOND,Integer.parseInt(strArr[2]));
        cal.add(Calendar.MINUTE,Integer.parseInt(strArr[1]));
        hour += Integer.parseInt(strArr[0])+Integer.parseInt(strArr2[0]);
        return  hour+":"+f2.format(cal.getTime());
    }

    private void viewAnalysisSleep2() {
        FirebaseDatabase.getInstance().getReference("AnalysisSleep").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SleepAnalysisModel sleepAnalysisModel = dataSnapshot.getValue(SleepAnalysisModel.class);
                    if(sleepAnalysisModel.uid.equals(myUid+","+babyName)){
                        int daycnt = sleepAnalysisModel.daycnt;
                        napcnt = sleepAnalysisModel.numNap;
                        timenap = sleepAnalysisModel.timeNap;
                        timesleep = sleepAnalysisModel.timeSleep;

                        if(daycnt == 0){
                            textView_naptime2.setText(timenap);
                            textView_nighttime2.setText(timesleep);
                        } else {
                            textView_naptime2.setText(devideTime(timenap,daycnt));
                            textView_nighttime2.setText(devideTime(timesleep,daycnt));
                        }
                        if (daycnt == 0) textView_napcnt2.setText(String.valueOf(napcnt));
                        else textView_napcnt2.setText(String.valueOf(napcnt/daycnt));
                        textView_daycnt.setText(String.valueOf(daycnt));
                        try {
                            textView_totaltime2.setText(addTime(textView_naptime2.getText().toString(),textView_nighttime2.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void updateAnalysisSleep(String starttime, boolean isNap, String babyname){
        Map<String, Object> updateTime = new HashMap<>();
        FirebaseDatabase.getInstance().getReference("AnalysisSleep").orderByChild("uid").equalTo(myUid+","+babyname).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                String key = snapshot.getKey();
                SleepAnalysisModel sleepAnalysisModel = snapshot.getValue(SleepAnalysisModel.class);
                String runtime = null;
                try {
                    runtime = diffTime(sdf2.format(new Date(System.currentTimeMillis())),starttime);
                    System.out.println("runtime: "+runtime);
                    if(isNap) {
                          updateTime.put(key+"/numNap",sleepAnalysisModel.numNap+1);
                          updateTime.put(key+"/timeNap",addTime(runtime, sleepAnalysisModel.timeNap));
                        System.out.println("낮잠: "+sleepAnalysisModel.numNap+1+" "+addTime(runtime, sleepAnalysisModel.timeNap));
                    }
                    else {
                        updateTime.put(key+"/timeSleep",addTime(runtime,sleepAnalysisModel.timeSleep));
                        System.out.println("밤잠: "+addTime(runtime,sleepAnalysisModel.timeSleep));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                updateTime.put(key+"/gaewol",Integer.parseInt(gaewol));
                FirebaseDatabase.getInstance().getReference("AnalysisSleep").updateChildren(updateTime);
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
    private void updateDayCount(String babyname){
        FirebaseDatabase.getInstance().getReference("AnalysisSleep").orderByChild("uid").equalTo(myUid+","+babyname).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                int c = snapshot.getValue(SleepAnalysisModel.class).daycnt;
                String key = snapshot.getKey();
                Map<String, Object> updateTime = new HashMap<>();
                updateTime.put(key+"/daycnt",c+1);
                FirebaseDatabase.getInstance().getReference("AnalysisSleep").updateChildren(updateTime);
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

    private String diffTime(String d1, String d2) throws ParseException {
        d2 = "2021/11/11 "+d2;
        d1 = "2021/11/11 "+d1;
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //String d = sdf2.format(d2);
        Date d = sdf2.parse(d2);
        Date dc = sdf2.parse(d1);
        Log.i(TAG, "diffTime: 현재 "+d1);
        Log.i(TAG, "diffTime: "+d);
        dc.setHours(dc.getHours()-9);
        long diff = dc.getTime() - d.getTime();

        return sdf.format(diff);
    }
    private String devideTime(String time, int cnt){
        if(time.equals("00:00:00")) return "00:00:00";
        String[] timearr = time.split(":");
        int totalsec = Integer.parseInt(timearr[2]) + Integer.parseInt(timearr[1]) * 60 + Integer.parseInt(timearr[0]) * 3600;
        int devided = totalsec/cnt;
        int hour = devided/3600;
        devided = devided%3600;
        int min = devided/60;
        devided = devided%60;
        int sec = devided;

        String result = String.valueOf(hour)+":"+String.valueOf(min)+":"+String.valueOf(sec);
        return result;
    }

    private void openQ(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("월령별 수면 패턴").setView(R.layout.dialog_analysis);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
            }
        });
        
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
