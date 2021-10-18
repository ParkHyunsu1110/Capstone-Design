package com.example.lovebaby.Model;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.List;

@Entity
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    public int alarmId;

    @ColumnInfo(name = "uid")
    public String uid;

    @ColumnInfo(name = "alarmYear")
    public int alarmYear;
    @ColumnInfo(name = "alarmMonth")
    public int alarmMonth;
    @ColumnInfo(name = "alarmDate")
    public int alarmDate;
    @ColumnInfo(name = "alarmHour")
    public int alarmHour;
    @ColumnInfo(name = "alarmMinute")
    public int alarmMinute;

    public Alarm(String uid, int alarmYear, int alarmMonth, int alarmDate, int alarmHour, int alarmMinute) {
        this.uid = uid;
        this.alarmYear = alarmYear;
        this.alarmMonth = alarmMonth;
        this.alarmDate = alarmDate;
        this.alarmHour = alarmHour;
        this.alarmMinute = alarmMinute;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getAlarmYear() {
        return alarmYear;
    }

    public void setAlarmYear(int alarmYear) {
        this.alarmYear = alarmYear;
    }

    public int getAlarmMonth() {
        return alarmMonth;
    }

    public void setAlarmMonth(int alarmMonth) {
        this.alarmMonth = alarmMonth;
    }

    public int getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(int alarmDate) {
        this.alarmDate = alarmDate;
    }

    public int getAlarmHour() {
        return alarmHour;
    }

    public void setAlarmHour(int alarmHour) {
        this.alarmHour = alarmHour;
    }

    public int getAlarmMinute() {
        return alarmMinute;
    }

    public void setAlarmMinute(int alarmMinute) {
        this.alarmMinute = alarmMinute;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "alarmId=" + alarmId +
                ", uid='" + uid + '\'' +
                ", alarmYear=" + alarmYear +
                ", alarmMonth=" + alarmMonth +
                ", alarmDate=" + alarmDate +
                ", alarmHour=" + alarmHour +
                ", alarmMinute=" + alarmMinute +
                '}';
    }
}


