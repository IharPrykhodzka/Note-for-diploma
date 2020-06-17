package com.example.diplomjava;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "noteDb";
    public static final String TABLE_NOTES = "notes";

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_NOTE = "note";
    public static final String KEY_CHECK_BOX = "checkBoxInInteger";
    public static final String KEY_DATE_AND_TIME = "timeAndDate";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NOTES + "(" + KEY_ID
                + " integer primary key," + KEY_TITLE + " text," + KEY_NOTE + " text,"
                + KEY_CHECK_BOX + " text," + KEY_DATE_AND_TIME + " text" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NOTES);

        onCreate(db);

    }
}