package com.example.week7sqliteandroom_workshop_starter_code;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import java.util.*;

public class LostFoundDbHelper extends SQLiteOpenHelper {
    private static final String NAME = "lostfound.db";
    private static final int    VER  = 1;

    public static final String TBL = "items",
            C_ID="_id", C_TYPE="type", C_NAME="reporter",
            C_PHONE="phone", C_DESC="description",
            C_DATE="date", C_LOC="location",
            C_LAT="latitude", C_LNG="longitude";

    private static final String SQL_CREATE =
            "CREATE TABLE "+TBL+" ("+
                    C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    C_TYPE+" TEXT NOT NULL, "+
                    C_NAME+" TEXT, "+
                    C_PHONE+" TEXT, "+
                    C_DESC+" TEXT, "+
                    C_DATE+" TEXT, "+
                    C_LOC+" TEXT, "+
                    C_LAT+" REAL, "+
                    C_LNG+" REAL"+
                    ");";

    public LostFoundDbHelper(Context c){
        super(c, NAME, null, VER);
    }
    @Override public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE);
    }

    @Override public void onUpgrade(SQLiteDatabase db,int o,int n){
        db.execSQL("DROP TABLE IF EXISTS "+TBL);
        onCreate(db);
    }

    /** Insert and return row-ID **/
    public long insertItem(LostFoundItem it){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_TYPE, it.getType());
        cv.put(C_NAME, it.getReporterName());
        cv.put(C_PHONE, it.getPhone());
        cv.put(C_DESC, it.getDescription());
        cv.put(C_DATE, it.getDate());
        cv.put(C_LOC, it.getLocation());
        cv.put(C_LAT, it.getLatitude());
        cv.put(C_LNG, it.getLongitude());
        return db.insert(TBL, null, cv);
    }

    /** Fetch all, newest first **/
    public List<LostFoundItem> getAllItems(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TBL, null, null, null, null, null, C_ID+" DESC");
        List<LostFoundItem> out = new ArrayList<>();
        while(c.moveToNext()){
            LostFoundItem i = new LostFoundItem();
            i.setId(   c.getLong(c.getColumnIndexOrThrow(C_ID)));
            i.setType( c.getString(c.getColumnIndexOrThrow(C_TYPE)));
            i.setReporterName(
                    c.getString(c.getColumnIndexOrThrow(C_NAME)));
            i.setPhone(
                    c.getString(c.getColumnIndexOrThrow(C_PHONE)));
            i.setDescription(
                    c.getString(c.getColumnIndexOrThrow(C_DESC)));
            i.setDate(
                    c.getString(c.getColumnIndexOrThrow(C_DATE)));
            i.setLocation(
                    c.getString(c.getColumnIndexOrThrow(C_LOC)));
            i.setLatitude(
                    c.getDouble(c.getColumnIndexOrThrow(C_LAT)));
            i.setLongitude(
                    c.getDouble(c.getColumnIndexOrThrow(C_LNG)));
            out.add(i);
        }
        c.close();
        return out;
    }
}
