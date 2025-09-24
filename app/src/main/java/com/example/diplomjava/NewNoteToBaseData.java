package com.example.diplomjava;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class NewNoteToBaseData extends AppCompatActivity {

    Calendar dateAndTime;
    Button btnSetDateAndTime;
    EditText editTitle, editNote;
    TextView currentDateTime;
    ImageButton btnSave;
    CheckBox checkBoxDeadLine;
    Toolbar toolbar;
    LinearLayout linearLayout;
    Integer intIsChecked = 0;

    final static String MY_LOG = "myLog";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Log.d(MY_LOG, "Создание NoteSave");

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
        Intent intent = getIntent();
        openCardFromSQL(intent);
        setBtnSave(intent);

    }


    /**
     * Кнопка сохранения в SQL
     */

    private void setBtnSave(final Intent intent) {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String note = editNote.getText().toString().trim();
                String dateTime = null;
                Calendar calendar = Calendar.getInstance();


                if (title.length() != 0 && note.length() != 0) {

                    if (checkBoxDeadLine.isChecked()) {

                        Log.d(MY_LOG, "1Текст для массива" + currentDateTime.toString());

                        String text = currentDateTime.toString();

                        Log.d(MY_LOG, "2Текст для массива" + text);

                        long millis = dateAndTime.getTimeInMillis();
                        dateTime = String.valueOf(millis);

                        NewNote newNote = new NewNote(title, note, intIsChecked, dateTime);
                        Log.d(MY_LOG, newNote.toString());

                        if (intent.getStringExtra("idCard") == null) {
                            App.getNoteRepository().saveDateToSQLite(newNote);

                        } else {
                            App.getNoteRepository().updateDateToSQLite(intent.getStringExtra("idCard"), newNote);
                        }

                        saveOrNot();
                        finish();

                    } else {
                        dateTime = null;

                        NewNote newNote = new NewNote(title, note, intIsChecked, dateTime);
                        Log.d(MY_LOG, newNote.toString());

                        if (intent.getStringExtra("idCard") == null) {
                            App.getNoteRepository().saveDateToSQLite(newNote);
                            Log.d(MY_LOG, "сохронена новая запись");
                        } else {
                            App.getNoteRepository().updateDateToSQLite(intent.getStringExtra("idCard"), newNote);
                            Log.d(MY_LOG, "отредактированно");
                        }

                        saveOrNot();
                        finish();
                        recreate();
                    }
                }else {
                    Toast.makeText(NewNoteToBaseData.this, R.string.toast_error_need_text, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Передал в MainActivity сигнал о рестарте активити
     */

    private void saveOrNot() {
        Intent intent = new Intent();
        intent.putExtra("name", true);
        setResult(RESULT_OK, intent);
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
        new DatePickerDialog(NewNoteToBaseData.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    /**
     * отображаем диалоговое окно для выбора времени
     */

    public void setTime() {
        new TimePickerDialog(NewNoteToBaseData.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    /**
     * установка начальных даты и времени
     */

    private void setInitialDateTime() {

        currentDateTime.setText(DateFormat.format("dd/MM/yy HH:mm",
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


    /**
     * открытие карточки для редактирования
     */

    public void openCardFromSQL(Intent intent) {

        String idCard = intent.getStringExtra("idCard");
        String titleCard = intent.getStringExtra("titleCard");
        String textCard = intent.getStringExtra("textCard");
        String deadLineCard = intent.getStringExtra("deadLineCard");
        String dateAndTimeCard = intent.getStringExtra("dateAndTimeCard");

        if (idCard != null) {
            editNote.setText(textCard);
            editTitle.setText(titleCard);
            assert deadLineCard != null;
            if (deadLineCard.equals("1")) {
                checkBoxDeadLine.setChecked(true);
                if (dateAndTimeCard != null) {
                    Log.d(MY_LOG, dateAndTimeCard);
                    currentDateTime.setText(DateFormat.format("dd/MM/yy HH:mm",
                            Long.parseLong(dateAndTimeCard)));
                }
            } else {
                checkBoxDeadLine.setChecked(false);
            }
        }
    }
}
