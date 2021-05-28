package com.capstonedesign.lovebaby;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;



@Database(entities = {GraphInfo.class, Vaccine.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract VaccineDAO vaccineDAO();
    public abstract GraphInfoDAO graphInfoDAO();
}
