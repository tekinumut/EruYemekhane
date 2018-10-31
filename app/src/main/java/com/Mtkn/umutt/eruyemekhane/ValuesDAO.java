package com.Mtkn.umutt.eruyemekhane;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
