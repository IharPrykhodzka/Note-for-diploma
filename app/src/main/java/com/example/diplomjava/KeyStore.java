package com.example.diplomjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.diplomjava.Interfaces.KeyStoreInterface;

public class KeyStore extends AppCompatActivity implements KeyStoreInterface {

    final static String MY_KEY = "MyPinCod";
    SharedPreferences sharedPref;
    final static String MY_LOG = "myLog";

    public KeyStore(App app) {
        sharedPref = ((Context) app).getSharedPreferences(MY_KEY, Context.MODE_PRIVATE);
    }

    @Override
    public boolean hasPin() {
        return sharedPref.contains(MY_KEY);
    }

    @Override
    public boolean checkPin(String pin) {
        return pin.equals(sharedPref.getString(MY_KEY, ""));
    }

    @Override
    public void saveNew(String pin) {
        SharedPreferences.Editor myEditor = sharedPref.edit();
        myEditor.putString(MY_KEY, pin);
        myEditor.apply();
        Log.d(MY_LOG, sharedPref.getString(MY_KEY, ""));
    }
}
