package com.example.diplomjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    EditText editPin;
    Button btnSavePin, btnVisiblePin;
    final static String MY_LOG = "myLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_settings);


        editPin = findViewById(R.id.editPinCod);
        btnSavePin = findViewById(R.id.btnSavePinCode);
        btnVisiblePin = findViewById(R.id.btnVisiblePinCod);

        btnSavePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int len = editPin.getText().toString().trim().length();

                if (len != 4) {

                    Toast.makeText(Settings.this, R.string.toast_error_wrong_save_pin, Toast.LENGTH_LONG).show();

                } else {
                    App.getKeystore().saveNew(editPin.getText().toString());
                    Log.d(MY_LOG, editPin.getText().toString());
                    Toast.makeText(Settings.this, R.string.toast_save_pin, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        btnVisiblePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editPin.getTransformationMethod() == null) {
                    editPin.setTransformationMethod(new PasswordTransformationMethod());
                    btnVisiblePin.setBackgroundResource(R.drawable.ic_visibility_off_black_24dp);
                }else {
                    editPin.setTransformationMethod(null);
                    btnVisiblePin.setBackgroundResource(R.drawable.ic_visibility_black_24dp);
                }
            }
        });

    }

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
