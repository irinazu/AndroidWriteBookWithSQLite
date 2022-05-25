package com.example.writebook.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.writebook.model.Note;
import com.example.writebook.model.NoteCrossTag;
import com.example.writebook.model.NoteWithTag;
import com.example.writebook.model.Tag;
import com.example.writebook.model.TagWithNote;

import java.util.List;

@Dao
public interface NoteDAO {
    @Query("select * from note")
    List<Note> getAllNotes();

    @Insert
    public void createNote(Note...notes);

    @Delete
    public void deleteNote(Note note);

    @Transaction
    @Query("SELECT * FROM Note")
    public List<NoteWithTag> getNoteWithTag();

    @Transaction
    @Query("SELECT * FROM Note where noteId=:noteId")
    public List<NoteWithTag> getAllNoteWithTag(Long noteId);

    @Insert
    public void noteCrossTag(NoteCrossTag noteCrossTag);

    @Query("select * from note where headline=:head")
    public Note takeOnName(String head);

    @Query("select * from note where noteId=:id")
    public Note getNoteId(Long id);

    @Update
    public void updateNote(Note note);

    @Delete
    public void deleteNoteWithTag(NoteCrossTag noteCrossTag);

    @Query("select * from notecrosstag where tagId=:id")
    public NoteCrossTag getNoteCrossTag(Long id);
}
