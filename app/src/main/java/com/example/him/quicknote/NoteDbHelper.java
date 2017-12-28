package com.example.him.quicknote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by him on 12/28/2017.
 */

    /*
        -create database
        -upgrade database
        -update note
        -delete note
        -get single note

    */
public class NoteDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="notes.db";
    private static final String TABLE_NAME="notes";
    private static final String ID="id";
    private static final String INFO="info";
    public NoteDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //creating the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table="CREATE TABLE "+TABLE_NAME+" (" +ID +" INTEGER PRIMARY KEY, "+INFO+" TEXT)";
        db.execSQL(create_table);
        Log.d(TAG, "onCreate: Database created");
    }


    //upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgrade_table="DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(upgrade_table);
        onCreate(db);
    }

    //adding note to the database with one parameter
    public void addNote(Note note) {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(INFO,note.getNote());
        sqLiteDatabase.insert(TABLE_NAME,null,values);
        sqLiteDatabase.close();
    }

    //get list of all notes
    public List<Note> getNotesList(){
        List<Note> notesList=new ArrayList<>();
        String getNotes="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(getNotes,null);
        if(cursor.moveToFirst()){
            do{
                Note note=new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setNote(cursor.getString(1));
                notesList.add(0,note);
            }while(cursor.moveToNext());
        }
        return notesList;
    }

    //update a note
    public int updateNote(Note note){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(INFO,note.getNote());
        return sqLiteDatabase.update(TABLE_NAME,values,ID+" =?",
                new String[]{String.valueOf(note.getId())});
    }

    //delete a note
    public void deleteNote(Note note){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,ID+" =?",
                new String[]{String.valueOf(note.getId())});
        sqLiteDatabase.close();
    }

    //get single note
    public Note getOneNote(int id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,new String[]{ID,INFO},ID+" =?",
                new String[]{String.valueOf(id)},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Note note=new Note(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
        return note;
    }

}
