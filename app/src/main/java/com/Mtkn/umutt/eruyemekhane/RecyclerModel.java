package com.Mtkn.umutt.eruyemekhane;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Values")
class RecyclerModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "tarih")
    private final String tarih;
    @ColumnInfo(name = "yemekler")
    private final String yemekler;
    @ColumnInfo(name = "topCal")
    private final int topCal;
    @ColumnInfo(name = "type")
    private final String type;

    RecyclerModel(String tarih, String yemekler, int topCal,String type) {

        this.tarih = tarih;
        this.yemekler = yemekler;
        this.topCal = topCal;
        this.type = type;
    }
    String getTarih() {
        return tarih;
    }

    String getYemekler() {
        return yemekler;
    }

    int getTopCal() {
        return topCal;
    }

    String getType() {
        return type;
    }
}
