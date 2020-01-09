package com.example.notelagi.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notelagi.Model.Note;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDAO {
//    @Insert(onConflict = OnConflictStrategy.)
    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void UpdateNote(Note note);

    @Query("SELECT * FROM notes")
    List<Note> getNotes();

    @Query("SELECT * FROM notes WHERE id = :noteID")
    Note getNoteById(int noteID);

    @Query("DELETE FROM notes WHERE id = :noteId")
    void deleteNoteById(int noteId);
}
