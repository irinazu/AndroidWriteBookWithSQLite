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
import com.example.writebook.model.Tag;

import java.util.List;

public class AllTag extends AppCompatActivity {

    private ListTagAdapter listTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tag);

        Button addNewNoteButton = findViewById(R.id.CreateTag);
        Button goToNotes = findViewById(R.id.GoToNotes);

        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AllTag.this,AddTag.class), 100);
            }
        });

        goToNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(AllTag.this, MainActivity.class);
                startActivity(intent);
            }
        });

        initRecyclerView();

        loadTagList();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.TagRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        ListTagAdapter.ItemListener stateClickListener = new ListTagAdapter.ItemListener() {

            @Override
            public void onItemClick(Long position) {
                Intent intent;
                intent = new Intent(AllTag.this, AddTag.class);
                intent.putExtra("tagId", position);
                startActivity(intent);
            }
        };
        listTagAdapter = new ListTagAdapter(this,stateClickListener);
        recyclerView.setAdapter(listTagAdapter);
    }

    private void loadTagList() {
        AppDatabase db = AppDatabase.getDB(this.getApplicationContext());
        List<Tag> tagList =db.tagDAO().getAllTags();
        listTagAdapter.setTagList(tagList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadTagList();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}