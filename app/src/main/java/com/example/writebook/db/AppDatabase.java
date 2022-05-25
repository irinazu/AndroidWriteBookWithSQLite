package com.example.writebook.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.writebook.dao.NoteDAO;
import com.example.writebook.dao.TagDAO;
import com.example.writebook.model.Note;
import com.example.writebook.model.NoteCrossTag;
import com.example.writebook.model.Tag;

@Database(entities = {Note.class, Tag.class, NoteCrossTag.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDAO noteDao();
    public abstract TagDAO tagDAO();

    private static AppDatabase appDatabase;

    public static AppDatabase getDB(Context context){
        if (appDatabase==null){
            appDatabase= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"Notebook")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

}
