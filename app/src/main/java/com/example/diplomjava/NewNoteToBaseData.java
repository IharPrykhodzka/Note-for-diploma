package com.example.diplomjava;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

import java.util.Arrays;
import java.util.Calendar;

public class NewNoteToBaseData extends AppCompatActivity {

    Calendar dateAndTime;
    Button btnSetDateAndTime;
    EditText editTitle, editNote;
    MaskEditText currentDateTime;
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


                String[] inPairs = new String[5];

                if (checkBoxDeadLine.isChecked()) {

                    Log.d(MY_LOG, "1Текст для массива" + currentDateTime.getRawText());

                    String text = currentDateTime.getRawText();

                    Log.d(MY_LOG, "2Текст для массива" + text);

                    int longText = text.length();
                    int check = 0;

                    try {
                        for (int i = 0; i < longText; i += 2) {

                            char ch = text.charAt(i);
                            char ch2 = text.charAt(i + 1);
                            String letter = Character.toString(ch);
                            String letter2 = Character.toString(ch2);

                            Log.d(MY_LOG, "3Текст для массива" + letter);

                            inPairs[check] = letter + letter2;
                            check += 1;

                            Log.d(MY_LOG, "5Массив значений" + Arrays.toString(inPairs));
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        Toast.makeText(NewNoteToBaseData.this, "Заполните строку",
                                Toast.LENGTH_LONG).show();
                    }

                    int year = Integer.parseInt("20" + inPairs[2]);
                    int month = Integer.parseInt(inPairs[1]);
                    int day = Integer.parseInt(inPairs[0]);
                    int hour = Integer.parseInt(inPairs[3]);
                    int minutes = Integer.parseInt(inPairs[4]);

                    if (forCalendar(month, year, day, hour, minutes)) {
                        calendar.set(year, month - 1, day, hour, minutes);
                        Log.d(MY_LOG, "Календарь " + DateFormat.format("dd/MM/yy HH:mm",
                                calendar.getTimeInMillis()));
                        long millis = calendar.getTimeInMillis();
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
                        Toast.makeText(NewNoteToBaseData.this, R.string.toast_error_date,
                                Toast.LENGTH_LONG).show();
                        return;
                    }

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
     * проверка високостного года
     */

    public static int leapYear(int month, int year) {
        int daysInMonth;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else if (month == 2) {
            daysInMonth = (year % 4 == 0) ? 29 : 28;
        } else {
            daysInMonth = 31;
        }
        return daysInMonth;
    }

    /**
     * проверка правельности ввода даты и времени
     */

    public static boolean forCalendar(int month, int year, int days, int hour, int minutes) {
        int daysInMonth = leapYear(month, year);
        if (2020 >= year & year <= 2200) {
            Log.d(MY_LOG, "year - true");
            if (0 >= month & month <= 11) {
                Log.d(MY_LOG, "month - true");
                if (0 >= days & days <= daysInMonth) {
                    Log.d(MY_LOG, "days - true");
                    if (0 >= hour & hour <= 23) {
                        Log.d(MY_LOG, "hour - true");
                        if (0 >= minutes & minutes <= 59) {
                            Log.d(MY_LOG, "minutes - true");
                            return true;

                        }
                    }
                }
            }
        } else {
            Log.d(MY_LOG, "forCalendar - false" + "\n" + daysInMonth + "\n" + month + "\n" + year + "\n" + days + "\n" + hour + "\n" + minutes);
            return false;
        }

        return true;
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
