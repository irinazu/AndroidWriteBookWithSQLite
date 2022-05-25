package com.example.writebook.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    public Long noteId;
    public String date;
    public String headline;
    public String text;


    public String getDate() {
        return date;
    }

    public String getHeadline() {
        return headline;
    }

    public String getText() {
        return text;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setText(String text) {
        this.text = text;
    }
}
