package com.example.diplomjava;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class NotesRepository extends AppCompatActivity {

    DBHelper dbHelper = null;
    final static String MY_LOG = "myLog";


    public void readInDateBase() {

        Log.d(MY_LOG, "Вызов readSQLiteBase");

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int noteIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE);
            int checkIndex = cursor.getColumnIndex(DBHelper.KEY_CHECK_BOX);
            int dateAndTimeIndex = cursor.getColumnIndex(DBHelper.KEY_DATE_AND_TIME);
            do {
                Log.d(MY_LOG, "ID = " + cursor.getInt(idIndex) +
                        ", title = " + cursor.getString(titleIndex) +
                        ", note = " + cursor.getString(noteIndex) +
                        ", checkDeadLine = " + cursor.getString(checkIndex) +
                        ", dateAndTime = " + cursor.getString(dateAndTimeIndex));
            } while (cursor.moveToNext());
        } else
            Log.d(MY_LOG, "0 рядов");

        cursor.close();
        dbHelper.close();
    }


    public void saveDateBase(NewNote newNote) {

        Log.d(MY_LOG, "Вызов NotesRepositoryInterface saveDateToSQLite");

        Log.d(MY_LOG, newNote.toString());

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, newNote.getTitleNote());
        contentValues.put(DBHelper.KEY_NOTE, newNote.getTextNote());
        contentValues.put(DBHelper.KEY_CHECK_BOX, newNote.getCheckDeadLine());
        contentValues.put(DBHelper.KEY_DATE_AND_TIME, newNote.getDateAndTime());


        database.insert(DBHelper.TABLE_NOTES, null, contentValues);

        dbHelper.close();
    }



}
