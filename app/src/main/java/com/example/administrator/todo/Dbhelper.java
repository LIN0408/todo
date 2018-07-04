package com.example.administrator.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {

    public static final String KEY_ID = "_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_LIST = "list";
    public static final String KEY_COLOR= "color";
    public static final String KEY_REMIND= "remind";
    public static final String DATABASE_NAME = "todolist";
    public static final String TABLE_NAME = "tdo";
    public static final int DB_VERSION = 1;

    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                KEY_ID + " integer PRIMARY KEY autoincrement," +
                KEY_TIME + "," +
                KEY_LIST + "," +
                KEY_COLOR + "," +
                KEY_REMIND + ");";
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
