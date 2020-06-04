package com.example.diplomjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PinCode extends AppCompatActivity implements KeystoreInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
    }

    @Override
    public boolean hasPin() {
        return false;
    }

    @Override
    public boolean checkPin(String pin) {
        return false;
    }

    @Override
    public void saveNew(String pin) {

    }
}
