package com.keelim.temp1.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String dbName = "WordDB";
    private static int dbVersion = 1;
    private static String wordTable = "word";
    private static String idColumn = "id";
    private static String wordColumn = "word";
    private static String meanColumn = "mean";


    public DatabaseHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + wordTable + "(" +
                idColumn + " integer primary key autoincrement, " +
                wordColumn + " tex, " +
                meanColumn + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + wordTable);
        onCreate(sqLiteDatabase);
    }

    public List<WordMean> findAll() {
        List<WordMean> wordMeans = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + wordTable, null);
            if (cursor.moveToFirst()) {
                wordMeans = new ArrayList<>();
                do {
                    WordMean wordMean = new WordMean();
                    wordMean.setId(cursor.getInt(0));
                    wordMean.setWord(cursor.getString(1));
                    wordMean.setWord(cursor.getString(2));
                    wordMeans.add(wordMean);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            wordMeans = null;
        }
        return wordMeans;
    }

    public List<WordMean> search(String keyword) {
        List<WordMean> wordMeans = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + wordTable + " where " + wordColumn + " like ?", new String[]{"%" + keyword + "%"});
            if (cursor.moveToFirst()) {
                wordMeans = new ArrayList<>();
                do {
                    WordMean wordMean = new WordMean();
                    wordMean.setId(cursor.getInt(0));
                    wordMean.setWord(cursor.getString(1));
                    wordMean.setWord(cursor.getString(2));
                    wordMeans.add(wordMean);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            wordMeans = null;
        }
        return wordMeans;
    }

}