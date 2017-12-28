package com.example.him.quicknote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by him on 12/28/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyNotesHolder> {

    private List<Note> noteList;
    Note note;
    Context context;
    public class MyNotesHolder extends RecyclerView.ViewHolder{

        public TextView info;
        public MyNotesHolder(View view){

            super(view);
            info=(TextView)view.findViewById(R.id.infoText);
            note=new Note();

        }
    }
    @Override
    public MyNotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card,parent,false);
        final MyNotesHolder myNotesHolder=new MyNotesHolder(view);
        return myNotesHolder;

    }

    public NoteAdapter(Context context,List<Note> noteList){

        this.context=context;
        this.noteList=noteList;

    }
    @Override
    public void onBindViewHolder(MyNotesHolder holder, int position) {

        Note note=noteList.get(position);
        holder.info.setText(note.getNote());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

}
