package com.example.writebook.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class NoteWithTag {
    @Embedded
    public Note note;
    @Relation(
            parentColumn = "noteId",
            entityColumn = "tagId",
            associateBy = @Junction(NoteCrossTag.class)
    )
    public List<Tag> tags;
}