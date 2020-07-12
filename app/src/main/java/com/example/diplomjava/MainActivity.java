package com.example.diplomjava;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.diplomjava.App.getNoteRepository;

public class MainActivity extends AppCompatActivity {

    final static String MY_LOG = "myLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(MY_LOG, "Создание MainActivity");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intentNewNote = new Intent(MainActivity.this, NewNoteToBaseData.class);
                startActivity(intentNewNote);
            }
        });

        ImageButton btnSettings = findViewById(R.id.mIVSetting);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSettings = new Intent(MainActivity.this, Settings.class);
                startActivity(intentSettings);
            }
        });

        initList();
    }

    private void initList() {
        final ListView listView = findViewById(R.id.listView);
        List<DataItems> dataItemsList = App.getNoteRepository().getNotes();


        final DataItemsAdapter dataItemsAdapter = new DataItemsAdapter(dataItemsList, this);
        listView.setAdapter(dataItemsAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Object object = dataItemsList.get(position);
//                String massage = object.toString();
//
//
//                switch (massage) {
//                    case "Записная книжка":
//                        Intent intentNotes = new Intent(MainActivity.this, NotesActivity.class);
//                        startActivity(intentNotes);
//                        break;
//                    case "Календарь":
//                        Intent intentCalendar = new Intent(MainActivity.this, CalendarActivity.class);
//                        startActivity(intentCalendar);
//                        break;
//                    case "Адресс":
//                        Intent intentAddress = new Intent(MainActivity.this, AddressActivity.class);
//                        startActivity(intentAddress);
//                        break;
//                    case "Настройки":
//                        Toast.makeText(MainActivity.this, R.string.txtOpenSettings, Toast.LENGTH_LONG).show();
//                        break;
//                }
//            }
//        });

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                Object object = dataItemsList.get(position);
//                String massage = object.toString();
//
//                Toast.makeText(MainActivity.this, massage, Toast.LENGTH_LONG).show();
//
//                return false;
//            }
//        });


    }




}
