package com.example.writebook.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.writebook.model.Note;
import com.example.writebook.model.NoteCrossTag;
import com.example.writebook.model.Tag;
import com.example.writebook.model.TagWithNote;

import java.util.List;

@Dao
public interface TagDAO {
    @Query("select * from tag")
    public List<Tag> getAllTags();

    @Query("select * from tag where tagId=:id")
    public Tag getTagId(Long id);

    @Insert
    public void createTag(Tag...tags);

    @Delete
    public void deleteTag(Tag tag);

    @Transaction
    @Query("SELECT * FROM Tag")
    public List<TagWithNote> getTagWithNote();

   /* void createTagWithNote(Tag tag, List<Note> notes){
        NoteCrossTag noteCrossTag=new NoteCrossTag();
        noteCrossTag.
        for(Note note : notes){
            note.se(user.getId());
        }

        _insertAll(pets);
    }*/
    @Transaction
    @Query("SELECT * FROM Tag where tagId=:tagId")
    public List<TagWithNote> getAllTagWithNote(Integer tagId);

    @Update
    public void updateTag(Tag tag);
}
