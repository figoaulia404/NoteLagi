package com.example.notelagi;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;

import com.example.notelagi.Adapter.NotesAdapter;
import com.example.notelagi.DataBase.NotesDAO;
import com.example.notelagi.DataBase.NotesDB;
import com.example.notelagi.Login.LoginActivity;
import com.example.notelagi.Login.SharedPrefrenceManager;
import com.example.notelagi.Model.Note;
import com.example.notelagi.Utils.NoteUtils;
import com.example.notelagi.callback.MainActionCallback;
import com.example.notelagi.callback.NoteEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.SyncStateContract;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.notelagi.EditeNoteActivity.NOTE_EXTRA_Key;
import static com.example.notelagi.Login.SharedPrefrenceManager.sp_sudahlog;

public class MainActivity extends AppCompatActivity implements NoteEventListener, Drawer.OnDrawerItemClickListener {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private NotesDAO dao;
    private MainActionCallback actionCallback;
    private int checkedCount = 0;
    private FloatingActionButton fab;
    Window window;
    ActionBar actionBar;
    Toolbar toolbar;
    private DrawerLayout dl;
    ActionBarDrawerToggle t;
    NavigationView nv;
    private SharedPreferences settingz;
    public static String THEME_key = "app_theme";
    public static final String APP_PREFERENCE = "notepad_settings";
    TextView tvresultnik;
    String resultnik;
    SharedPrefrenceManager sharedPrefrenceManager;

    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settingz = getSharedPreferences(APP_PREFERENCE,Context.MODE_PRIVATE);
        theme = settingz.getInt(THEME_key, R.style.AppTheme);
        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupNavigation(savedInstanceState, toolbar);
        recyclerView = findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sharedPrefrenceManager = new SharedPrefrenceManager(this);

        initComponen();
//        Bundle extras = getIntent().getExtras();
//        if (extras != null)
//            resultnik = extras.getString("result_nik");
//            tvresultnik.setText(resultnik);

        dl = (DrawerLayout) findViewById(R.id.dwLyt);
        t = new ActionBarDrawerToggle(this, dl, 0, 0);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        nv = (NavigationView)findViewById(R.id.nv);
//        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                switch(id)
//                {
//                    case R.id.item1:
//                        Toast.makeText(MainActivity.this, "item1",Toast.LENGTH_SHORT).show();break;
//                    case R.id.item2:
//                        Toast.makeText(MainActivity.this, "item2",Toast.LENGTH_SHORT).show();break;
//                    case R.id.item3:
//                        Toast.makeText(MainActivity.this, "item3",Toast.LENGTH_SHORT).show();break;
//                    default:
//                        return true;
//                }
//
//
//                return true;
//
//            }
//        });

        //dao = NotesDB.getInstance(this).notesDAO();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddNewNote();
            }
        });

        dao = NotesDB.getInstance(this).notesDAO();
    }

    private void initComponen() {

    }

    private void setupNavigation(Bundle savedInstanceState, Toolbar toolbar) {

        //Navigation menu item
        List<IDrawerItem> iDrawerItems = new ArrayList<>();
        iDrawerItems.add(new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.ic_home_black_24dp));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Notes").withIcon(R.drawable.ic_note_black_24dp));
        iDrawerItems.add(new PrimaryDrawerItem().withName("Log out"));

        //Sticky draweritems : footer menu item
        List<IDrawerItem> stickyItems = new ArrayList<> ();
        SwitchDrawerItem switchDrawerItem = new SwitchDrawerItem()
                .withName("Dark Theme")
                .withChecked(theme == R.style.AppTheme_Dark)
                .withIcon(R.drawable.ic_invert_colors_black_24dp)
                .withOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                        // TODO: 02/10/2018 change to darck theme and save it to settings
                        if (isChecked) {
                            settingz.edit().putInt(THEME_key, R.style.AppTheme_Dark).apply();
                        } else {
                            settingz.edit().putInt(THEME_key, R.style.AppTheme).apply();
                        }

                        //MainActivity.this.recreate ();
                        TaskStackBuilder.create(MainActivity.this)
                                .addNextIntent (new Intent (MainActivity.this,MainActivity.class))
                                .addNextIntent (getIntent ()).startActivities ();
                    }
                });
        stickyItems.add(new PrimaryDrawerItem().withName("Setting").withIcon(R.drawable.ic_settings_black_24dp));
        stickyItems.add(new PrimaryDrawerItem().withName("Log out")
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        sharedPrefrenceManager.saveSpBoolean(sp_sudahlog, false);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        return false;
                    }
                }));
        stickyItems.add(switchDrawerItem);

        //Navigation menu header
        AccountHeader header = new AccountHeaderBuilder().withActivity(this)
                .addProfiles(new ProfileDrawerItem()
                        .withEmail("figoaulia404@gmail.com")
                        .withName("figo aulia")
                        .withIcon(R.mipmap.ic_launcher_round))
                .withSavedInstance(savedInstanceState)
                .withHeaderBackground(R.drawable.ic_launcher_background)
                .withSelectionListEnabledForSingleProfile(false) // we need just one profile
                .build();

        // Navigation drawer
        new DrawerBuilder()
                .withActivity(this) // activity main
                .withToolbar(toolbar)
                .withSavedInstance(savedInstanceState)
                .withDrawerItems(iDrawerItems)
                .withTranslucentNavigationBar(true)
                .withStickyDrawerItems(stickyItems)
//                .withAccountHeader(header)
                .withOnDrawerItemClickListener(this)
                .build();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        List<Note> list = dao.getNotes();
        this.notes.addAll(list);
        this.adapter = new NotesAdapter(this, this.notes);
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
        showEmptyView();
//        adapter.notifyDataSetChanged();

        swipeToDeleteHelper.attachToRecyclerView(recyclerView);

    }

    private void showEmptyView() {
        if (notes.size() == 0) {
            this.recyclerView.setVisibility(View.GONE);
            findViewById(R.id.empty_notes_view).setVisibility(View.VISIBLE);
        } else {
            this.recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.empty_notes_view).setVisibility(View.GONE);
        }
    }

    private void onAddNewNote() {
        startActivity(new Intent(this, EditeNoteActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (t.onOptionsItemSelected(item))
            return true;


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onNoteClick(Note note) {
        Intent edit = new Intent(this, EditeNoteActivity.class);
        edit.putExtra(NOTE_EXTRA_Key, note.getId());
        startActivity(edit);


    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onNoteLongClick(Note note) {

        note.setChecked(true);
        checkedCount = 1;
        adapter.setMultiCheckedMode(true);

        adapter.setListener(new NoteEventListener() {
            @Override
            public void onNoteClick(Note note) {
                note.setChecked(!note.isChecked());
                if (note.isChecked())
                    checkedCount++;
                else checkedCount--;

                if (checkedCount > 1) {
                    actionCallback.changeShareItemVisible(false);
                } else actionCallback.changeShareItemVisible(true);

                if (checkedCount == 0) {
                    actionCallback.getAction().finish();
                }

                actionCallback.setCount(checkedCount + "/" + notes.size());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNoteLongClick(Note note) {

            }
        });

        actionCallback = new MainActionCallback() {
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.action_delete_notes)
                    onDeleteMultiNotes();
                else if (item.getItemId() == R.id.action_share_notes)
                    onShareNotes();

                mode.finish();
                return false;
            }
        };
        startActionMode(actionCallback);
//        getActionBar().hide();
//        actionBar.hide();
        fab.setVisibility(View.VISIBLE);
        actionCallback.setCount(checkedCount + "/" + notes.size());


//        Log.d(TAG, "onNoteLongClick: "+note.getId());
//        new AlertDialog.Builder(this)
//                .setTitle(R.string.app_name)
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                })
//                .setPositiveButton("Delte", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        dao.deleteNote(note);
//                        loadNotes();
//
//                    }
//                })
//                .setNegativeButton("Share", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent share = new Intent(Intent.ACTION_SEND);
//                        String text = note.getNoteText()+"\n Create on :"
//                                + NoteUtils.dateFromLong(note.getNoteDate())+"By :"
//                                +getString(R.string.app_name);
////                        Log.d(TAG, "onClick: "+ text);
//                        share.setType("text/plain");
//                        share.putExtra(Intent.EXTRA_TEXT, text);
//                        startActivity(share);
//
//                    }
//                })
//                .create()
//                .show();

    }

    private void onShareNotes()
    {
        Note note = adapter.getCheckedNotes().get(0);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        String notetext = note.getNoteText() + "\n\n Create on : " +
                NoteUtils.dateFromLong(note.getNoteDate()) + "By" + getString(R.string.app_name);
        share.putExtra(Intent.EXTRA_TEXT, notetext);
        startActivity(share);
    }

    private void onDeleteMultiNotes() {

        List<Note> checkedNote = adapter.getCheckedNotes();
        if (checkedNote.size() != 0) {
            for (Note note : checkedNote) {
                dao.deleteNote(note);
            }
            Toast.makeText(this, checkedNote.size() + "Note(s) Delete successfully !", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "No Notes selected", Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        adapter.setMultiCheckedMode(false);
        toolbar.setVisibility(View.VISIBLE);
        adapter.setListener(this);
        fab.setVisibility(View.VISIBLE);
    }

    private ItemTouchHelper swipeToDeleteHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    if (notes != null) {
                        Note swipedNoted = notes.get(viewHolder.getAdapterPosition());
                        if (swipedNoted != null) {
                            swipeToDelete(swipedNoted, viewHolder);
                        }
                    }

                }
            });

    private void swipeToDelete(final Note swipedNoted, final RecyclerView.ViewHolder viewHolder) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("Delete Note ?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dao.deleteNote(swipedNoted);
                        notes.remove(swipedNoted);
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        showEmptyView();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        recyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());

                    }
                })
                .setCancelable(false)
                .create().show();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
        if (position == 2) {
            sharedPrefrenceManager.saveSpBoolean(sp_sudahlog, false);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }

}
