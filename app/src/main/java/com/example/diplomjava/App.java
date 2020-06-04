package com.example.diplomjava;

import android.app.Application;
import android.util.Log;

import com.example.diplomjava.Interfaces.KeystoreInterface;
import com.example.diplomjava.Interfaces.NotesRepositoryInterface;

public class App extends Application {

    final static String MY_LOG = "myLog";
    private static NotesRepositoryInterface noteRepository;
    private static KeystoreInterface keystore;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MY_LOG, "Создание App");

        noteRepository = new NoteFromBaseData();
        keystore = new PinCode();
    }



    public static NotesRepositoryInterface getNoteRepository() {
        Log.d(MY_LOG, "Возрат в App NotesRepositoryInterface");
        return noteRepository;
    }


    public static KeystoreInterface getKeystore() {
        Log.d(MY_LOG, "Возрат в App KeystoreInterface");
        return keystore;
    }
}
