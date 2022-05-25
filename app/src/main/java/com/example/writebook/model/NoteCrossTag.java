package com.example.writebook.model;

import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"noteId", "tagId"})
public class NoteCrossTag {
    @NotNull
    public Long noteId;
    @NotNull
    public Long tagId;

    @NotNull
    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(@NotNull Long noteId) {
        this.noteId = noteId;
    }

    @NotNull
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(@NotNull Long tagId) {
        this.tagId = tagId;
    }
}