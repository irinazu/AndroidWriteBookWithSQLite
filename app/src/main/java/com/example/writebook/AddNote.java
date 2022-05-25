package com.example.writebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.writebook.db.AppDatabase;
import com.example.writebook.model.Note;
import com.example.writebook.model.NoteCrossTag;
import com.example.writebook.model.NoteWithTag;
import com.example.writebook.model.Tag;
import com.example.writebook.model.TagWithNote;

import java.util.ArrayList;
import java.util.List;

public class AddNote extends AppCompatActivity {

    private ListTagsForAddAdapter listTagsForAddAdapter;
    private ListTagsForDeleteAdapter listTagsForDeleteAdapter;
    List<Long> longListId=new ArrayList<>();
    List<Long> longListIdDelete=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Button saveButton =  findViewById(R.id.saveButton);
        Button cancelButton =  findViewById(R.id.cancelButton);
        Button deleteButton =  findViewById(R.id.deleteButton);

        Bundle arguments = getIntent().getExtras();
        Long nodeId=null;
        if(arguments!=null){
            String id = arguments.get("noteId").toString();
            nodeId=Long.parseLong(id);
        }

        final EditText headline =findViewById(R.id.headline);
        final EditText textnote=findViewById(R.id.textnote);

        if(nodeId!=null){

            AppDatabase db  = AppDatabase.getDB(this.getApplicationContext());
            Note note=db.noteDao().getNoteId(nodeId);
            headline.setText(note.getHeadline());
            textnote.setText(note.getText());
            deleteButton.setVisibility(View.VISIBLE);

        }
        EditText headlineA =findViewById(R.id.headline);
        EditText textnoteA=findViewById(R.id.textnote);
        final Long newId=nodeId;

        if(nodeId!=null){
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateNote(newId,headlineA.getText().toString(), textnoteA.getText().toString());
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteNote(newId);
                }
            });

        }
        else {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNewNote(headlineA.getText().toString(), textnoteA.getText().toString());
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(AddNote.this,MainActivity.class);
                    startActivity(intent);
                }
            });
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddNote.this,MainActivity.class);
                startActivity(intent);
            }
        });

        initRecyclerView();

        initRecyclerViewForDelete();

        loadTagsListForAdd(newId);

        if(newId!=null){
            loadTagsListForDelete(newId);
        }
    }

    void saveNewNote(String headline,String textnote){
        AppDatabase db  = AppDatabase.getDB(this.getApplicationContext());

        Note note = new Note();
        note.headline = headline;
        note.text = textnote;
        db.noteDao().createNote(note);
        note=db.noteDao().takeOnName(headline);

        NoteCrossTag noteCrossTag=new NoteCrossTag();
        noteCrossTag.setNoteId(note.getNoteId());

        List<Tag> tagList=new ArrayList<>();
        for(int i=0;i<longListId.size();i++){
            tagList.add(db.tagDAO().getTagId(longListId.get(i)));
        }
        for(int i=0;i<longListId.size();i++){
            noteCrossTag.setTagId(longListId.get(i));
            db.noteDao().noteCrossTag(noteCrossTag);
        }

        if(longListIdDelete.size()!=0){
            for(int i=0;i<longListIdDelete.size();i++){
                NoteCrossTag noteCrossTag1=db.noteDao().getNoteCrossTag(longListIdDelete.get(i));
                db.noteDao().deleteNoteWithTag(noteCrossTag1);
            }
        }

        tagList.clear();
        longListIdDelete.clear();

        finish();
        Intent intent = new Intent(AddNote.this, MainActivity.class);
        startActivity(intent);
    }

    void updateNote(Long id,String headline,String text){
        AppDatabase db  = AppDatabase.getDB(this.getApplicationContext());
        Note note=db.noteDao().getNoteId(id);
        note.setText(text);
        note.setHeadline(headline);
        db.noteDao().updateNote(note);

        NoteCrossTag noteCrossTag=new NoteCrossTag();
        noteCrossTag.setNoteId(note.getNoteId());

        List<Tag> tagList=new ArrayList<>();
        for(int i=0;i<longListId.size();i++){
            tagList.add(db.tagDAO().getTagId(longListId.get(i)));
        }
        for(int i=0;i<longListId.size();i++){
            noteCrossTag.setTagId(longListId.get(i));
            db.noteDao().noteCrossTag(noteCrossTag);

        }
        if(longListIdDelete.size()!=0){
            for(int i=0;i<longListIdDelete.size();i++){
                NoteCrossTag noteCrossTag1=db.noteDao().getNoteCrossTag(longListIdDelete.get(i));
                db.noteDao().deleteNoteWithTag(noteCrossTag1);
            }
        }
        tagList.clear();
        longListIdDelete.clear();
        finish();
        Intent intent = new Intent(AddNote.this, MainActivity.class);
        startActivity(intent);
    }


    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewForAddTagsInNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ListTagsForAddAdapter.ItemListener stateClickListener = new ListTagsForAddAdapter.ItemListener() {

            @Override
            public void onItemClick(Long position) {
                longListId.add(position);
            }
        };
        listTagsForAddAdapter = new ListTagsForAddAdapter(this,stateClickListener);
        recyclerView.setAdapter(listTagsForAddAdapter);
    }

    private void initRecyclerViewForDelete() {
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewForDeleteTagsFromNote);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ListTagsForDeleteAdapter.ItemListener stateClickListener = new ListTagsForDeleteAdapter.ItemListener() {

            @Override
            public void onItemClick(Long position) {
                System.out.println(position+"POSITION");
                System.out.println(longListId.size());
                longListIdDelete.add(position);
                longListId.remove(position);
                System.out.println(longListId.size());

            }
        };
        listTagsForDeleteAdapter = new ListTagsForDeleteAdapter(this,stateClickListener);
        recyclerView.setAdapter(listTagsForDeleteAdapter);
    }


    private void loadTagsListForAdd(Long id) {
        AppDatabase db = AppDatabase.getDB(this.getApplicationContext());
        List<Tag> tags =db.tagDAO().getAllTags();
        List<NoteWithTag> note=db.noteDao().getAllNoteWithTag(id);

        if(id==null){
            listTagsForAddAdapter.setTagList(tags);
        }else {
            List<Tag> tagsD =note.get(0).tags;

            List<Tag> forSave=new ArrayList<>();
            boolean flag=true;
            for(Tag tag:tags){
                for(Tag tag1:tagsD){
                    if(tag.getTagId().equals(tag1.getTagId())){
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    forSave.add(tag);
                }
                flag=true;
            }
            listTagsForAddAdapter.setTagList(forSave);
        }
    }

    private void loadTagsListForDelete(Long id) {
        AppDatabase db = AppDatabase.getDB(this.getApplicationContext());
        List<NoteWithTag> note=db.noteDao().getAllNoteWithTag(id);

        List<Tag> tags =note.get(0).tags;
        for (Tag tag:tags){
            System.out.println(tag.getText());
        }

        listTagsForDeleteAdapter.setTagList(tags);
    }

    public void deleteNote(Long aLong){
        AppDatabase db = AppDatabase.getDB(this.getApplicationContext());
        Note note=db.noteDao().getNoteId(aLong);
        db.noteDao().deleteNote(note);
        finish();
        Intent intent = new Intent(AddNote.this, MainActivity.class);
        startActivity(intent);
    }
}