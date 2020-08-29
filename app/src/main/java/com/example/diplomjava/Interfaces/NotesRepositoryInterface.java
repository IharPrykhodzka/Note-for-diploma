package com.example.diplomjava.Interfaces;

import com.example.diplomjava.DataItems;
import com.example.diplomjava.NewNote;
import com.example.diplomjava.NewNoteToBaseData;

import java.util.List;

public interface NotesRepositoryInterface {

    List<DataItems> getNotes();

    void saveDateToSQLite(NewNote newNote);

    void updateDateToSQLite(String id, NewNote newNote);

    void deleteDateToSQLite(String id);

}
