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
import com.example.writebook.model.NoteWithTag;
import com.example.writebook.model.Tag;
import com.example.writebook.model.TagWithNote;

import java.util.ArrayList;
import java.util.List;

public class AddTag extends AppCompatActivity {

    private ListNoteAdapter listNoteAdapter;
    List<Long> longListId=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        Button cancelButton =  findViewById(R.id.cancelTagButton);
        Button deleteButton =  findViewById(R.id.deleteTagButton);
        Button saveButton = findViewById(R.id.saveTagButton);

        Bundle arguments = getIntent().getExtras();
        Long tagId=null;
        if(arguments!=null){
            String id = arguments.get("tagId").toString();
            tagId=Long.parseLong(id);
        }

        final EditText textTag = findViewById(R.id.insertTagText);

        if(tagId!=null){

            AppDatabase db  = AppDatabase.getDB(this.getApplicationContext());
            Tag tag=db.tagDAO().getTagId(tagId);
            textTag.setText(tag.getText());
            deleteButton.setVisibility(View.VISIBLE);

        }
        EditText textTagA = findViewById(R.id.insertTagText);
        final Long newId=tagId;

        if(tagId!=null){
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateTag(newId,textTagA.getText().toString());
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteTag(newId);
                }
            });

        }
        else {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveNewTag(textTag.getText().toString());
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(AddTag.this,AllTag.class);
                    startActivity(intent);
                }
            });
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddTag.this,AllTag.class);
                startActivity(intent);
            }
        });

        initRecyclerView();

        if(tagId!=null){
            loadNoteList(tagId);
        }
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.NoteInTagActivityRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ListNoteAdapter.ItemListener stateClickListener = new ListNoteAdapter.ItemListener() {

            @Override
            public void onItemClick(Long position) {
                Intent intent;
                intent = new Intent(AddTag.this, AddNote.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        };
        listNoteAdapter = new ListNoteAdapter(this,stateClickListener);
        recyclerView.setAdapter(listNoteAdapter);


    }

    private void loadNoteList(Long aLong) {
        AppDatabase db = AppDatabase.getDB(this.getApplicationContext());
        List<NoteWithTag> tagListForOneNote =db.noteDao().getNoteWithTag();
        List<NoteWithTag> forSave=new ArrayList<>();

        for (int i=0;i<tagListForOneNote.size();i++){
            for (int j=0;j<tagListForOneNote.get(i).tags.size();j++){
                if(tagListForOneNote.get(i).tags.get(j).getTagId().equals(aLong)){
                    forSave.add(tagListForOneNote.get(i));
                    break;
                }
            }
        }
        listNoteAdapter.setNoteWithTagsList(forSave);
    }

    void saveNewTag(String textTag){
        AppDatabase db  = AppDatabase.getDB(this.getApplicationContext());

        Tag tag = new Tag();
        tag.setText(textTag);
        db.tagDAO().createTag(tag);

        finish();
        Intent intent = new Intent(AddTag.this, AllTag.class);
        startActivity(intent);
    }

    void updateTag(Long id,String text){
        AppDatabase db  = AppDatabase.getDB(this.getApplicationContext());
        Tag tag=db.tagDAO().getTagId(id);
        tag.setText(text);
        db.tagDAO().updateTag(tag);
        Intent intent = new Intent(AddTag.this, AllTag.class);
        startActivity(intent);
    }

    void deleteTag(Long id){
        AppDatabase db  = AppDatabase.getDB(this.getApplicationContext());
        Tag tag=db.tagDAO().getTagId(id);
        db.tagDAO().deleteTag(tag);
        Intent intent = new Intent(AddTag.this, AllTag.class);
        startActivity(intent);
    }
}