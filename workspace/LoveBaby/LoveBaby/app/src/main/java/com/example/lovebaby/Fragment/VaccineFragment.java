package com.example.lovebaby.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lovebaby.R;
import com.example.lovebaby.VaccineMapActivity;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class VaccineFragment extends Fragment {
    private String babyName,birthday;
    private Button vaccinemap;
    private String[] vaccinename;
    private int[] vaccinedate,vaccineterm;
    private RecyclerView recyclerView;
    private TextView textview_name, textview_date;
    private Dialog dialog,datepick_dialog,timepick_dialog;
    private EditText dialog_facname;
    private DatePicker datePicker;
    private TimePicker timePicker;
    int hour,nMinute;
    int nyear,nmonthOfYear, ndayOfMonth;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccine,container,false);
        recyclerView = view.findViewById(R.id.vaccine_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        BoardRecyclerViewAdapter boardRecyclerViewAdapter = new BoardRecyclerViewAdapter();
        recyclerView.setAdapter(boardRecyclerViewAdapter);

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

        Bundle bundle = getArguments();
        if(bundle != null) {
            babyName = bundle.getString("babyName");
            birthday = bundle.getString("birthday");
        }
        textview_name.setText(babyName);

        BabyMenuFragment fragment = new BabyMenuFragment();
        textview_date.setText(fragment.diffMonth(birthday)+"개월");

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
            String date = getDate(birthday,vaccinedate[position]);
            String date2 = getDate(date,vaccineterm[position]);
            ((CustomViewHolder)holder).vaccinename.setText(vaccinename[position]);
            ((CustomViewHolder)holder).vaccineddate.setText(date+"~"+date2);
        }

        @Override
        public int getItemCount() {
            return vaccinename.length;
        }
        private class CustomViewHolder extends RecyclerView.ViewHolder{
            TextView vaccinename;
            TextView vaccineddate;
            ImageView vaccinestate;
            public CustomViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                this.vaccinename = (TextView)itemView.findViewById(R.id.vaccine_name);
                this.vaccineddate = (TextView)itemView.findViewById(R.id.vaccine_date);
                this.vaccinestate = (ImageView)itemView.findViewById(R.id.vaccine_state_imageview);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog();
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

    public void showDialog(){
        EditText edit_date = dialog.findViewById(R.id.edittext_alarm_date);
        EditText edit_time = dialog.findViewById(R.id.edittext_alarm_time);

        dialog.show();
        dialog.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.noBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.dialog_facility_name).setOnClickListener(new View.OnClickListener() {
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
                    }
                });
                datepick_dialog.findViewById(R.id.datepick_yesBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit_date.setText(nyear+"-"+nmonthOfYear+"-"+ndayOfMonth);
                        dialog.dismiss();
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
                        edit_time.setText(hour+"시 "+nMinute+"분");
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
}
