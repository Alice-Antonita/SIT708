package com.example.week7sqliteandroom_workshop_starter_code;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LostFoundDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME    = "lostfound.db";
    private static final int    DATABASE_VERSION = 1;

    // table & columns
    public static final String TABLE_NAME      = "items";
    public static final String COL_ID          = "_id";
    public static final String COL_TYPE        = "type";
    public static final String COL_REPORTER    = "reporter";
    public static final String COL_PHONE       = "phone";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_DATE        = "date";
    public static final String COL_LOCATION    = "location";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TYPE        + " TEXT NOT NULL, " +
                    COL_REPORTER    + " TEXT, " +
                    COL_PHONE       + " TEXT, " +
                    COL_DESCRIPTION + " TEXT, " +
                    COL_DATE        + " TEXT, " +
                    COL_LOCATION    + " TEXT" +
                    ");";

    public LostFoundDbHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /** Insert a new item and return its row-ID **/
    public long insertItem(LostFoundItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TYPE,        item.getType());
        cv.put(COL_REPORTER,    item.getReporterName());
        cv.put(COL_PHONE,       item.getPhone());
        cv.put(COL_DESCRIPTION, item.getDescription());
        cv.put(COL_DATE,        item.getDate());
        cv.put(COL_LOCATION,    item.getLocation());
        return db.insert(TABLE_NAME, null, cv);
    }

    /** Fetch all items (newest first) **/
    public List<LostFoundItem> getAllItems() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(
                TABLE_NAME, null, null, null, null, null,
                COL_ID + " DESC"
        );

        List<LostFoundItem> items = new ArrayList<>();
        while (c.moveToNext()) {
            LostFoundItem i = new LostFoundItem();
            i.setId(       c.getLong(   c.getColumnIndexOrThrow(COL_ID)));
            i.setType(     c.getString( c.getColumnIndexOrThrow(COL_TYPE)));
            i.setReporterName(
                    c.getString( c.getColumnIndexOrThrow(COL_REPORTER)));
            i.setPhone(    c.getString( c.getColumnIndexOrThrow(COL_PHONE)));
            i.setDescription(
                    c.getString( c.getColumnIndexOrThrow(COL_DESCRIPTION)));
            i.setDate(     c.getString( c.getColumnIndexOrThrow(COL_DATE)));
            i.setLocation( c.getString( c.getColumnIndexOrThrow(COL_LOCATION)));
            items.add(i);
        }
        c.close();
        return items;
    }

    /** Delete by row-ID **/
    public int deleteItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
    }
}
