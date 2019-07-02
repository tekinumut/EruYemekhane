package com.Mtkn.umutt.eruyemekhane.RoomFiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.Mtkn.umutt.eruyemekhane.RecyclerModel;

import java.util.List;

@Dao
public interface ValuesDAO {

    @Insert
    void addValues(List<RecyclerModel> recyclerModels);

    @Query("Select *from `Values` where type=:type")//Öğrenci mi yoksa Personel verisi mi çekilecek ?
    List<RecyclerModel> getValues(String type);

    @Query("Delete from `Values` where type=:type") //Tabloda ki seçilen grubun tüm verilerini sil
    void deleteTable(String type);
}
