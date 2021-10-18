package com.example.lovebaby.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lovebaby.Model.Alarm;

import java.util.List;

@Dao
public interface AlarmDAO {
    @Query("SELECT * FROM Alarm")
    List<Alarm> getAll();

    @Query("SELECT * FROM Alarm WHERE uid IN (:userIds)")
    List<Alarm> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(Alarm alarm);

    @Delete
    void delete(Alarm alarm);

    @Update
    void update(Alarm alarm);
}
