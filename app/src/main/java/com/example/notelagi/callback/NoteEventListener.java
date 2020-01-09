package com.example.notelagi.callback;

import com.example.notelagi.Model.Note;

public interface NoteEventListener {

    void onNoteClick(Note note);
    void onNoteLongClick(Note note);

}
