package com.example.diplomjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.Calendar;

public class NewNote extends AppCompatActivity {

    Calendar dateAndTime;
    Button btnSetDateAndTime;
    EditText editTitle, editNote, currentDateTime;
    ImageButton btnSave;
    CheckBox checkBoxDeadLine;
    Toolbar toolbar;
    LinearLayout linearLayout;
    int intIsChecked = 0;

    final static String MY_LOG = "myLog";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        toolbar = findViewById(R.id.toolbarNewNote);
        currentDateTime = findViewById(R.id.currentDateTime);
        btnSetDateAndTime = findViewById(R.id.btnDateAndTime);
        editNote = findViewById(R.id.editNote);
        editTitle = findViewById(R.id.editTitle);
        btnSave = findViewById(R.id.mIBSave);
        checkBoxDeadLine = findViewById(R.id.checkBoxDeadLine);
        linearLayout = findViewById(R.id.layoutDeadLine);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.new_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isCheckedDeadLine();
        dateAndTime = Calendar.getInstance();
        setInitialDateTime();
        setDeadLine();
        saveDateToSQLite();

    }

    /**
     * Сохраняем данные в базу данных SQLite
     */

    private void saveDateToSQLite() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String note = editNote.getText().toString().trim();
                String dateTime = currentDateTime.getText().toString().trim();

                Log.d(MY_LOG, " \n"
                        +"Заголовок: \n"
                        + title + "\n"
                        + "Заметка: \n"
                        + note + "\n"
                        + intIsChecked + "\n"
                        + dateTime + "\n");
            }
        });
    }

    /**
     * Устанавливаем наличие DeadLine
     */

    private void isCheckedDeadLine() {

        checkBoxDeadLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!checkBoxDeadLine.isChecked()) {
                    linearLayout.setVisibility(View.GONE);
                    intIsChecked = 0;
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    intIsChecked = 1;
                }
            }
        });
    }


    /**
     * Кнопка на диологовые окна выбора даты и времени
     */

    public void setDeadLine() {
        btnSetDateAndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
                setDate();
            }
        });
    }


    /**
     * отображаем диалоговое окно для выбора даты
     */

    public void setDate() {
        new DatePickerDialog(NewNote.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    /**
     * отображаем диалоговое окно для выбора времени
     */

    public void setTime() {
        new TimePickerDialog(NewNote.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    /**
     * установка начальных даты и времени
     */

    private void setInitialDateTime() {

        currentDateTime.setText(DateFormat.format("dd/MM/yyyy H:m",
                dateAndTime.getTimeInMillis()));
    }

    /**
     * установка обработчика выбора времени
     */

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    /**
     * установка обработчика выбора даты
     */

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    /**
     * установка стрелки назад в ToolBar с значение кнопки назад
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
