package com.example.diplomjava;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    final static String MY_LOG = "myLog";
    private final static int REQUEST_CODE_NEW_NOTE_TOBD = 1;
    private final static int REQUEST_CODE_PIN_CODE = 2;
    boolean pinIsTrue = false;
    boolean pinIsTrue2 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            checkFirstStart();
        }

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
                startActivityForResult(intentNewNote, REQUEST_CODE_NEW_NOTE_TOBD);


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
                startActivityForResult(intentNewNote, REQUEST_CODE_NEW_NOTE_TOBD);
                
                dataItemsAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case Dialog.BUTTON_POSITIVE:
                                DataItems clickCard = dataItemsList.get(position);
                                String idCard = clickCard.getId();
                                Toast.makeText(MainActivity.this, idCard + " " + position, Toast.LENGTH_SHORT).show();
                                App.getNoteRepository().deleteDateToSQLite(idCard);

                                dataItemsList.remove(position);
                                dataItemsAdapter.notifyDataSetChanged();

                                Toast.makeText(MainActivity.this, R.string.deleted, Toast.LENGTH_SHORT).show();
                                break;
                            case Dialog.BUTTON_NEGATIVE:
                                Toast.makeText(MainActivity.this, R.string.negative, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder adb = new AlertDialog.Builder(view.getContext());
                adb.setTitle(R.string.delete);
                adb.setMessage(R.string.delete_data);
                adb.setIcon(android.R.drawable.ic_dialog_info);
                adb.setPositiveButton(R.string.yes, dialogClickListener);
                adb.setNegativeButton(R.string.no, dialogClickListener);
                adb.show();

                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NEW_NOTE_TOBD:
                    ListView listView = findViewById(R.id.listView);
                    List<DataItems> dataItemsList = App.getNoteRepository().getNotes();
                    DataItemsAdapter dataItemsAdapter = new DataItemsAdapter(dataItemsList, this);
                    listView.setAdapter(dataItemsAdapter);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isPin", true);
                    Intent in = new Intent(MainActivity.this, MainActivity.class);
                    in.putExtras(bundle);
                    recreate();
                    break;
                case REQUEST_CODE_PIN_CODE:
                    assert data != null;
                    pinIsTrue = data.getBooleanExtra("PIN", false);
                    Log.d(MY_LOG, "pinIsTrue = " + String.valueOf(pinIsTrue));
                    break;
            }
        } else {
            switch (requestCode) {
                case REQUEST_CODE_NEW_NOTE_TOBD:
                    Toast.makeText(this, R.string.toast_error_wrong_result, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_CODE_PIN_CODE:
                    Toast.makeText(this, R.string.toast_error_wrong_result, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    }


    private void checkFirstStart() {

        if (!pinIsTrue2) {

            SharedPreferences sp = getSharedPreferences("hasVisited",
                    Context.MODE_PRIVATE);
            boolean hasVisited = sp.getBoolean("hasVisited", false);

            if (!hasVisited) {
                SharedPreferences.Editor e = sp.edit();
                e.putBoolean("hasVisited", true);
                e.apply();

                Intent intentSettings = new Intent(MainActivity.this, Settings.class);
                startActivity(intentSettings);

            } else if (App.getKeystore().hasPin()) {
                Intent intentPinCode = new Intent(MainActivity.this, PinCode.class);
                startActivityForResult(intentPinCode, REQUEST_CODE_PIN_CODE);
                pinIsTrue2 = true;

                if (pinIsTrue) {
                    Log.d(MY_LOG, "pinIsTrue = " + String.valueOf(pinIsTrue));
                    Toast.makeText(MainActivity.this, R.string.txt_true_pin, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
