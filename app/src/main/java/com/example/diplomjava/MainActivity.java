package com.example.diplomjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    final static String MY_LOG = "myLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(MY_LOG, "Создание MainActivity");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSettings();

        fAB();

        initList();


    }


    private void fAB() {

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intentNewNote = new Intent(MainActivity.this, NewNoteToBaseData.class);
                startActivityForResult(intentNewNote, 1);


            }
        });
    }

    private void btnSettings() {
        ImageButton btnSettings = findViewById(R.id.mIVSetting);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(MainActivity.this, Settings.class);
                startActivity(intentSettings);
            }
        });
    }


    private void initList() {
         ListView listView = findViewById(R.id.listView);
         final List<DataItems> dataItemsList = App.getNoteRepository().getNotes();


        final DataItemsAdapter dataItemsAdapter = new DataItemsAdapter(dataItemsList, this);
        listView.setAdapter(dataItemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataItems clickCard = dataItemsList.get(position);

                String idCard = clickCard.getId();
                String titleCard = clickCard.getTitle_view();
                String textCard = clickCard.getSubTitle_view();
                String deadLineCard = clickCard.getCheckBoxInInteger();
                String dateAndTimeCard = clickCard.getDateTime_view();

                Intent intentNewNote = new Intent(MainActivity.this, NewNoteToBaseData.class);

                intentNewNote.putExtra("idCard", idCard);
                intentNewNote.putExtra("titleCard", titleCard);
                intentNewNote.putExtra("textCard", textCard);
                intentNewNote.putExtra("deadLineCard", deadLineCard);
                intentNewNote.putExtra("dateAndTimeCard", dateAndTimeCard);
                startActivityForResult(intentNewNote, 1);

                Toast.makeText(MainActivity.this, clickCard.getId(), Toast.LENGTH_SHORT).show();
                dataItemsAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                DataItems clickCard = dataItemsList.get(position);
                String idCard = clickCard.getId();
                Toast.makeText(MainActivity.this, idCard + " " + position, Toast.LENGTH_SHORT).show();
                App.getNoteRepository().deleteDateToSQLite(idCard);

                dataItemsList.remove(position);
                dataItemsAdapter.notifyDataSetChanged();

                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            recreate();
        } else {
            Toast.makeText(this, R.string.toast_error_wrong_result, Toast.LENGTH_SHORT).show();
        }
    }
}


