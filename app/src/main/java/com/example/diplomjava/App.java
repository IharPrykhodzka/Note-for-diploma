package com.example.diplomjava;

import android.app.Application;

public class App extends Application {

    private static NotesRepositoryInterface noteRepository;
    private static KeystoreInterface keystore;

    @Override
    public void onCreate() {
        super.onCreate();

        noteRepository = new NewNote();
        keystore = new PinCode();
    }



    public static NotesRepositoryInterface getNoteRepository() {
        return noteRepository;
    }


    public static KeystoreInterface getKeystore() {
        return keystore;
    }
}
