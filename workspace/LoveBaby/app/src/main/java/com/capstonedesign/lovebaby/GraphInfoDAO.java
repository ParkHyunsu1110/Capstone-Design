package com.capstonedesign.lovebaby;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface GraphInfoDAO {
    @Query("SELECT * FROM GraphInfo")
    List<GraphInfo> getAll();

    @Insert
    void insert(GraphInfo graphInfo);

    @Update
    void update(GraphInfo graphInfo);

    @Delete
    void delete(GraphInfo graphInfo);

}
