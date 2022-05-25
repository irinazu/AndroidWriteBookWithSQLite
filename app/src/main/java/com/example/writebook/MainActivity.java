package com.example.writebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.writebook.db.AppDatabase;
import com.example.writebook.model.Note;
import com.example.writebook.model.NoteWithTag;
import com.example.writebook.model.Tag;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListNoteAdapter listNoteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addNewNoteButton = findViewById(R.id.CreateNote);
        Button goToTags = findViewById(R.id.GoToTags);

        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddNote.class), 100);
            }
        });

        goToTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, AllTag.class);
                startActivity(intent);
            }
        });

        initRecyclerView();

        loadNoteList();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.MainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ListNoteAdapter.ItemListener stateClickListener = new ListNoteAdapter.ItemListener() {

            @Override
            public void onItemClick(Long position) {
                Intent intent;
                intent = new Intent(MainActivity.this, AddNote.class);
                intent.putExtra("noteId", position);
                startActivity(intent);
            }
        };
        listNoteAdapter = new ListNoteAdapter(this,stateClickListener);
        recyclerView.setAdapter(listNoteAdapter);


    }

    private void loadNoteList() {
        AppDatabase db = AppDatabase.getDB(this.getApplicationContext());
        List<NoteWithTag> tagListForOneNote =db.noteDao().getNoteWithTag();

        listNoteAdapter.setNoteWithTagsList(tagListForOneNote);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadNoteList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}