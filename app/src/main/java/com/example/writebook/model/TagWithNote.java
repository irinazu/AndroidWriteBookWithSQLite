package com.example.writebook.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TagWithNote {
    @Embedded
    public Tag tag;
    @Relation(
            parentColumn = "tagId",
            entityColumn = "noteId",
            associateBy = @Junction(NoteCrossTag.class)
    )
    public List<Note> notes;
}

