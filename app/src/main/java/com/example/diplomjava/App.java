package com.example.diplomjava;

import android.app.Application;
import android.util.Log;

import com.example.diplomjava.Interfaces.KeyStoreInterface;
import com.example.diplomjava.Interfaces.NotesRepositoryInterface;

public class App extends Application {

    final static String MY_LOG = "myLog";
    private static NotesRepository noteRepository;
    private static KeyStore keyStore;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(MY_LOG, "Создание App");

        noteRepository = new NotesRepository(this);
        keyStore = new KeyStore(this);
    }



    public static NotesRepositoryInterface getNoteRepository() {
        Log.d(MY_LOG, "Возрат в App NotesRepositoryInterface");
        return noteRepository;
    }


    public static KeyStoreInterface getKeystore() {
        Log.d(MY_LOG, "Возрат в App KeystoreInterface");
        return keyStore;
    }
}
