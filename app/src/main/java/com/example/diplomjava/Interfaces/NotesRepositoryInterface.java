package com.example.diplomjava.Interfaces;

import com.example.diplomjava.NewNote;
import com.example.diplomjava.NoteFromBaseData;

import java.util.List;

public interface NotesRepositoryInterface {

    NoteFromBaseData getNoteById(String id);

    List<NoteFromBaseData> getNotes();

    void saveDateToSQLite(NewNote newNote);

    void deleteDateToSQLite(String id);

}
