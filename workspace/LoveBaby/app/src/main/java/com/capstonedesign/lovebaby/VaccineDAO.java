package com.capstonedesign.lovebaby;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface VaccineDAO {

    @Query("SELECT * FROM Vaccine")
    List<Vaccine> getAll();

    @Insert
    void insert(Vaccine vaccine);

    @Update
    void update(Vaccine vaccine);

    @Delete
    void delete(Vaccine vaccine);

    @Query("SELECT * FROM Vaccine WHERE name > :sname ")
    List<Vaccine> getVaccine(String sname);
}
