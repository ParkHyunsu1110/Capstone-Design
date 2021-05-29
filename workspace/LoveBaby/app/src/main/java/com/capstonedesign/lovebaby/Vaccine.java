package com.capstonedesign.lovebaby;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Vaccine {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int month;
    private int day;
    
    public Vaccine(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private static void setVaccineDate(Vaccine vaccine, Vaccine nextVaccine) {
        if (vaccine.getDay() + 14 > 30) {
            nextVaccine.setMonth(vaccine.getMonth() + 1);
            nextVaccine.setDay((vaccine.getDay() + 14) - 30);
        }
        else {
            nextVaccine.setMonth(vaccine.getMonth());
            nextVaccine.setDay(vaccine.getDay() + 14);
        }
    }

    public static ArrayList<String> setVaccinationDate(List<Vaccine> vaccineList, Vaccine vaccine, int month, int day){
        ArrayList<String> result = new ArrayList<>();
        vaccine.setMonth(month);
        vaccine.setDay(day);

        for (int i = vaccine.getId() - 2; i < vaccineList.size(); i++) {
            setVaccineDate(vaccine, vaccineList.get(vaccine.getId() - 1));
        }
        setVaccineDate(vaccineList.get(vaccine.getId() - 1), vaccineList.get(vaccine.getId()));

        Vaccine nextVaccine = vaccineList.get(vaccine.getId() - 1);
        Vaccine nextVaccine2 = vaccineList.get(vaccine.getId());

        String r1 = String.format("다음 추천 예방접종은 %s, 추천 접종일은 %d월 %d일 입니다.",nextVaccine.getName(), nextVaccine.getMonth(), nextVaccine.getDay());
        String r2 = String.format("그 다음 추천 예방접종은 %s, 추천 접종일은 %d월 %d일 입니다.",nextVaccine2.getName(), nextVaccine2.getMonth(), nextVaccine2.getDay());

        result.add(r1);
        result.add(r2);

        return result;
    }

}
