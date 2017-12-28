package com.example.him.quicknote;

/**
 * Created by him on 12/28/2017.
 */

public class Note {

    int id;
    String note;

    public Note(){}

    public Note(String note){
        this.note=note;
    }
    public Note(int id, String note) {
        this.id = id;
        this.note = note;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
