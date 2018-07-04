package com.example.administrator.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DbAdapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_LIST = "list";
    public static final String KEY_COLOR= "color";
    public static final String KEY_REMIND= "remind";
    public static final String TABLE_NAME = "tdo";
    private Dbhelper mDbHelper;
    private SQLiteDatabase mdb;
    private final Context mCtx;
    private ContentValues values;

    public DbAdapter(Context mCtx) {
        this.mCtx = mCtx;
        open();
    }

    public void open(){
        mDbHelper = new Dbhelper(mCtx);
        mdb = mDbHelper.getWritableDatabase();
        Log.i("DB=",mdb.toString());
    }

    public long createContact(String time, String list, String color, String remind){
        try{
            values = new ContentValues();
            values.put(KEY_TIME, time);
            values.put(KEY_LIST, list);
            values.put(KEY_COLOR, color);
            values.put(KEY_REMIND, remind);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(mCtx,"新增成功!", Toast.LENGTH_SHORT).show();

        }
        return mdb.insert(TABLE_NAME,null,values);
    }

    public Cursor listContacts(){
        Cursor mCursor = mdb.query(TABLE_NAME, new String[]{KEY_ID, KEY_TIME,KEY_LIST, KEY_COLOR, KEY_REMIND},
                null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor queryById(int item_id){
        Cursor  mCursor = mdb.query(TABLE_NAME, new String[] {KEY_ID, KEY_TIME, KEY_LIST, KEY_COLOR, KEY_REMIND},
                KEY_ID + "=" + item_id, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public long updateContacts(int id, String time, String list, String color, String remind){
        long update = 0;
        try{
            //將資料丟到contentValues
            ContentValues values = new ContentValues();
            values.put(KEY_TIME, time);
            values.put(KEY_LIST, list);
            values.put(KEY_COLOR, color);
            values.put(KEY_REMIND, remind);
            update = mdb.update(TABLE_NAME, values, "_id=" + id,null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Toast.makeText(mCtx, "成功更新一筆資料!",Toast.LENGTH_LONG).show();
        }
        return update;
    }
    public boolean deleteContacts(int id){
        String[] args = {Integer.toString(id)};
        mdb.delete(TABLE_NAME, "_id= ?",args);
        return true;
    }
}