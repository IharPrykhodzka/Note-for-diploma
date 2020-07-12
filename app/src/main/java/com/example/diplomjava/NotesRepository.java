package com.example.diplomjava;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.diplomjava.Interfaces.NotesRepositoryInterface;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository extends AppCompatActivity implements NotesRepositoryInterface {

    DBHelper dbHelper;

    public NotesRepository(App app) {
        dbHelper = new DBHelper(app);
    }


    final static String MY_LOG = "myLog";

    @Override
    public DataItems getNoteById(String id) {

        Log.d(MY_LOG, "Вызов DataItems getNoteById");

        return point(id);
    }

    @Override
    public List<DataItems> getNotes() {

        Log.d(MY_LOG, "Вызов List<DataItems> getNotes");

        List<DataItems> list = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);

            do {
                Log.d(MY_LOG, "ID = " + cursor.getInt(idIndex));

                list.add(point(String.valueOf(cursor.getInt(idIndex))));

            } while (cursor.moveToNext());
        } else
            Log.d(MY_LOG, "0 рядов");

        Log.d(MY_LOG, list.toString());

        cursor.close();
        dbHelper.close();

        return list;
    }



    @Override
    public void saveDateToSQLite(NewNote newNote) {


        Log.d(MY_LOG, "Вызов saveDateToSQLite");

        Log.d(MY_LOG, newNote.toString() + "\n" + dbHelper.toString());

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, newNote.getTitleNote());
        contentValues.put(DBHelper.KEY_NOTE, newNote.getTextNote());
        contentValues.put(DBHelper.KEY_CHECK_BOX, newNote.getCheckDeadLine());
        contentValues.put(DBHelper.KEY_DATE_AND_TIME, newNote.getDateAndTime());


        database.insert(DBHelper.TABLE_NOTES, null, contentValues);

        dbHelper.close();
        Log.d(MY_LOG, "Точка" + App.getNoteRepository().getNotes().toString());
        finish();
    }

    @Override
    public void deleteDateToSQLite(String id) {


        Log.d(MY_LOG, "Вызов deleteDateToSQLite");

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null,
                null, null, null, null);

        if (cursor.move(Integer.parseInt(id))) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            database.delete(DBHelper.TABLE_NOTES, String.valueOf(idIndex), null);
        } else
            Log.d(MY_LOG, "нечего удалать");

        cursor.close();
        dbHelper.close();
    }



    private DataItems point(String id) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null,
                null, null, null, null);

        DataItems dataItems = null;

        if (cursor.move(Integer.parseInt(id))) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int titleIndex = cursor.getColumnIndex(DBHelper.KEY_TITLE);
            int noteIndex = cursor.getColumnIndex(DBHelper.KEY_NOTE);
            int checkIndex = cursor.getColumnIndex(DBHelper.KEY_CHECK_BOX);
            int dateAndTimeIndex = cursor.getColumnIndex(DBHelper.KEY_DATE_AND_TIME);

            Log.d(MY_LOG, "ID = " + cursor.getInt(idIndex) +
                    ", title = " + cursor.getString(titleIndex) +
                    ", note = " + cursor.getString(noteIndex) +
                    ", checkDeadLine = " + cursor.getString(checkIndex) +
                    ", dateAndTime = " + cursor.getString(dateAndTimeIndex));


            dataItems = new DataItems(cursor.getString(titleIndex), cursor.getString(noteIndex),
                    cursor.getString(dateAndTimeIndex), cursor.getString(checkIndex), String.valueOf(cursor.getInt(idIndex)));

            Log.d(MY_LOG,  "DataItems point " + dataItems.toString());


        } else
            Log.d(MY_LOG, " не найден");

        cursor.close();
        dbHelper.close();

        return dataItems;
    }
}
