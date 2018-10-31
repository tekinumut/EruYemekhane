package com.Mtkn.umutt.eruyemekhane;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {RecyclerModel.class},version = 1,exportSchema = false)
public abstract class ValuesDatabase extends RoomDatabase {

    public abstract ValuesDAO valuesDAO();
}
