package com.Mtkn.umutt.eruyemekhane;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Values")
public class RecyclerModel {
  
  @PrimaryKey(autoGenerate = true)
  public int id;
  @ColumnInfo(name = "tarih")
  private final String tarih;
  @ColumnInfo(name = "yemekler")
  private final String yemekler;
  @ColumnInfo(name = "topCal")
  private final String topCal;
  @ColumnInfo(name = "type")
  private final String type;
  
  public RecyclerModel(String tarih, String yemekler, String topCal, String type) {
	 
	 this.tarih = tarih;
	 this.yemekler = yemekler;
	 this.topCal = topCal;
	 this.type = type;
  }
  
  public int getId() {
	 return id;
  }
  
  public String getTarih() {
	 return tarih;
  }
  
  public String getYemekler() {
	 return yemekler;
  }
  
  public String getTopCal() {
	 return topCal;
  }
  
  public String getType() {
	 return type;
  }
}
