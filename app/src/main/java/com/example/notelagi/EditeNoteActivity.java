package com.example.notelagi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.notelagi.DataBase.NotesDAO;
import com.example.notelagi.DataBase.NotesDB;
import com.example.notelagi.Model.Note;

import java.util.Date;


public class EditeNoteActivity extends AppCompatActivity {
    private EditText inputNote;
    private NotesDAO dao;
    private Note temp;
    public static final String NOTE_EXTRA_Key = "note_id";
//    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.APP_PREFERENCE, Context.MODE_PRIVATE);
        int theme = sharedPreferences.getInt(MainActivity.THEME_key, R.style.AppTheme);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_note);

        temp = null;
        Toolbar toolbar = findViewById(R.id.toolbaredit);
        setSupportActionBar(toolbar);
        inputNote = findViewById(R.id.input_note);
        dao = NotesDB.getInstance (this).notesDAO ();
        if (getIntent ().getExtras () != null) {
            int id = getIntent ().getExtras ().getInt (NOTE_EXTRA_Key, 0);
            if (id!=0){
                temp = dao.getNoteById (id);
                inputNote.setText (temp.getNoteText ());
            }
        }else inputNote.setFocusable (true);
        }/*else inputNote.setFocusable(true);*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edite_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note)
            onSaveNote();
        return super.onOptionsItemSelected(item);
    }


    private void onSaveNote() {
        String text = inputNote.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime();

            if (temp == null) {
                temp = new Note(text, date);
                dao.insertNote(temp);

            } else {
                temp.setNoteText(text);
                temp.setNoteDate(date);
                dao.UpdateNote(temp);
           }

                finish();
            }
        }
    }

