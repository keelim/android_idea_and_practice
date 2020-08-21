package com.keelim.practice8.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;


public class InquryDBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DBFILE_CONTACT = "inqury_list.db";

    public InquryDBHelper(Context context) {
        super(context, DBFILE_CONTACT, null, DB_VERSION);
    }

    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(InquryDBCtrct.SQL_CREATE_TBL);
    }

    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL(InquryDBCtrct.SQL_DROP_TBL) ;
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // onUpgrade(db, oldVersion, newVersion); }
    }


}