package com.capstonedesign.lovebaby;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class GraphInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int month;
    private int weight;
    private int height;

    public GraphInfo(int month, int weight, int height) {
        this.month = month;
        this.weight = weight;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setMonth(int month) { this.month = month; }

    public void setWeight(int weight) { this.weight = weight; }

    public void setHeight(int height) { this.height = height; }
}
