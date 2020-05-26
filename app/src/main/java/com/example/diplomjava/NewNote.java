package com.example.diplomjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class NewNote extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Toolbar toolbar = findViewById(R.id.toolbarNewNote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.new_note);

    }
}
