package com.example.diplomjava;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.example.diplomjava.Interfaces.NotesRepositoryInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotesRepository extends AppCompatActivity implements NotesRepositoryInterface {

    DBHelper dbHelper;

    public NotesRepository(App app) {
        dbHelper = new DBHelper(app);
    }


    final static String MY_LOG = "myLog";

    @Override
    public List<DataItems> getNotes() {

        Log.d(MY_LOG, "Вызов List<DataItems> getNotes");

        List<DataItems> list = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Calendar dateAndTime = Calendar.getInstance();

        String sDateAndTime = String.valueOf(DateFormat.format("dd/MM/yyyy HH:mm",
                dateAndTime.getTimeInMillis()));

        String orderBy = "checkBoxInInteger DESC, timeAndDate ASC";

        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null,
                null, null, null, orderBy);

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
    public void updateDateToSQLite(String id, NewNote newNote) {

        if (id.equalsIgnoreCase("")){
            Toast.makeText(this, R.string.toast_error_save, Toast.LENGTH_LONG).show();
        }

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_TITLE, newNote.getTitleNote());
        contentValues.put(DBHelper.KEY_NOTE, newNote.getTextNote());
        contentValues.put(DBHelper.KEY_CHECK_BOX, newNote.getCheckDeadLine());
        contentValues.put(DBHelper.KEY_DATE_AND_TIME, newNote.getDateAndTime());

        int updCount = database.update(DBHelper.TABLE_NOTES, contentValues, DBHelper.KEY_ID + "= ?", new String[] {id});

        Log.d(MY_LOG, "updates сохранения = " + updCount);
        dbHelper.close();
    }

    @Override
    public void deleteDateToSQLite(String id) {


        if (id.equalsIgnoreCase("")){
            Toast.makeText(this, R.string.toast_error_delete, Toast.LENGTH_LONG).show();
        }else {

            SQLiteDatabase database = dbHelper.getReadableDatabase();

            int delCount = database.delete(DBHelper.TABLE_NOTES, DBHelper.KEY_ID + "=" + id, null);

            Log.d(MY_LOG, "deleted = " + delCount);

            dbHelper.close();
        }
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

            Log.d(MY_LOG, "DataItems point " + dataItems.toString());


        } else
            Log.d(MY_LOG, " не найден");

        cursor.close();
        dbHelper.close();

        return dataItems;
    }
}
