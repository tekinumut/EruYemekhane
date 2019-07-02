package com.Mtkn.umutt.eruyemekhane.RoomFiles;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.Mtkn.umutt.eruyemekhane.RecyclerModel;
import com.Mtkn.umutt.eruyemekhane.RoomFiles.ValuesDAO;

@Database(entities = {RecyclerModel.class},version = 2,exportSchema = false)
public abstract class ValuesDatabase extends RoomDatabase {

    public abstract ValuesDAO valuesDAO();
}
