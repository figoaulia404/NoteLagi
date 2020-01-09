package com.example.notelagi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notelagi.Model.Note;
import com.example.notelagi.R;
import com.example.notelagi.Utils.NoteUtils;
import com.example.notelagi.callback.NoteEventListener;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private Context context;
    private ArrayList<Note> notes;
    private NoteEventListener listener;
    private boolean multiCheckedMode = false;
    private boolean multiCheckMode;

    public NotesAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_layout,parent,false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, final int position) {
        final Note note = getNote(position);
        if (note !=null){
            holder.noteText.setText(note.getNoteText());
            holder.noteDate.setText(NoteUtils.dateFromLong(note.getNoteDate()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onNoteClick(note);
                    Toast.makeText(context, position+"",Toast.LENGTH_SHORT).show();
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onNoteLongClick(note);
                    return false;
                }
            });

            if (multiCheckedMode){
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.checkBox.setChecked(note.isChecked());
            }else holder.checkBox.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private Note getNote(int position){
        return notes.get(position);
    }

    public void setMultiCheckMode(boolean multiCheckMode){
        this.multiCheckMode = multiCheckMode;
        if (!multiCheckMode)
            for (Note note : notes) {
                note.setChecked(false);
            }

        notifyDataSetChanged();
    }

    public List<Note> getCheckedNotes() {
        List<Note> checkedNotes = new ArrayList<>();
        for (Note n : this.notes) {
            if (n.isChecked())
                checkedNotes.add(n);
        }

        return checkedNotes;
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        TextView noteText, noteDate;
        CheckBox checkBox;

        public NoteHolder(View itemView) {
            super(itemView);
            noteDate = itemView.findViewById(R.id.note_date);
            noteText = itemView.findViewById(R.id.note_text);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    public void setListener(NoteEventListener listener) {
        this.listener = listener;
    }

    public void setMultiCheckedMode(boolean multiCheckedMode) {
        this.multiCheckedMode =     multiCheckedMode;
        if (!multiCheckedMode)
            for (Note note : this.notes) {
                note.setChecked(false);
            }
        notifyDataSetChanged();
    }
}
