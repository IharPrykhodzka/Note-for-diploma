package com.example.diplomjava;

import java.util.List;

public interface NotesRepositoryInterface {

    NewNote getNoteById(String id);

    List<NewNote> getNotes();

    void saveDateToSQLite(NewNote newNote);

    void deleteDateToSQLite(String id);

}
