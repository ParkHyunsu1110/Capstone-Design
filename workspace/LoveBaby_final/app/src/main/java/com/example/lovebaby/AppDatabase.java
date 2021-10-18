package com.example.lovebaby;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.lovebaby.Model.Alarm;
import com.example.lovebaby.Model.AlarmDAO;

@Database(entities = {Alarm.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlarmDAO alarmDAO();

}
