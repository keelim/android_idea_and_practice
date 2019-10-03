package com.keelim.temp1.room;

import androidx.room.RoomDatabase;

public abstract class WordMeanDatabase extends RoomDatabase {
    abstract UserDao getUserDao();

}
