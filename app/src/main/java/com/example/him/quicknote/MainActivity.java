package com.example.him.quicknote;

import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    NoteDbHelper noteDbHelper;
    String infoString;
    Note note;
    NoteAdapter noteAdapter;
    RxPermissions rxPermissions;
    List<Note> noteList;
    @BindView(R.id.noteEdittext)  EditText infoText;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setBackend();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void saveNote(View view){

        //asking for permissions using reactive permission library
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        infoString = infoText.getText().toString();
                        noteDbHelper.addNote(new Note(infoString));
                        noteAdapter.notifyDataSetChanged();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Give the permission(s)", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void setBackend(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        note=new Note();
        noteDbHelper=new NoteDbHelper(this);
        noteList=noteDbHelper.getNotesList();
        noteAdapter=new NoteAdapter(this,noteList);
        rxPermissions=new RxPermissions(this);
        noteAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(noteAdapter);

    }

}
