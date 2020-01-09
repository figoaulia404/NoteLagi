package com.example.notelagi.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notelagi.Model.Note;

@Database(entities = Note.class, version = 1)
public abstract class NotesDB extends RoomDatabase {
    public abstract  NotesDAO notesDAO();

    public static final String DATABASE_NAME = "notesDB";
    private static NotesDB instance;

    public static NotesDB getInstance(Context context){
        if (instance == null)
            instance = Room.databaseBuilder(context,NotesDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
