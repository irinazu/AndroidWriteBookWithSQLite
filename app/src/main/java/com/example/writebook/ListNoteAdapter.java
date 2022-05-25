package com.example.writebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writebook.model.Note;
import com.example.writebook.model.NoteWithTag;
import com.example.writebook.model.Tag;

import java.util.List;

public class ListNoteAdapter extends RecyclerView.Adapter<ListNoteAdapter.MyViewHolder> {
    private Context context;
    private List<NoteWithTag> noteWithTags;
    private ItemListener itemListener;

    @NonNull
    @Override
    public ListNoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNoteAdapter.MyViewHolder holder, int position) {
        holder.tvHeadline.setText(this.noteWithTags.get(position).note.getHeadline());
        holder.tvTextNote.setText(this.noteWithTags.get(position).note.getText());
        Long aLong=noteWithTags.get(position).note.getNoteId();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClick(aLong);
            }
        });

        StringBuilder stringBuilder=new StringBuilder();
        for(Tag tag:this.noteWithTags.get(position).tags){
            stringBuilder.append(tag.getText()+", ");
        }
        holder.tvTagsForOneNote.setText(stringBuilder.toString());
    }

    @Override
    public int getItemCount() {
        if(noteWithTags!=null){
            return this.noteWithTags.size();
        }
        return 0;
    }

    public ListNoteAdapter(Context context,ItemListener itemListener) {
        this.context = context;
        this.itemListener=itemListener;
    }

    public void setNoteWithTagsList(List<NoteWithTag> tagListForOneNote) {
        this.noteWithTags=tagListForOneNote;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvHeadline;
        TextView tvTextNote;
        TextView tvTagsForOneNote;
        LinearLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);
            tvHeadline = view.findViewById(R.id.tvHeadline);
            tvTextNote = view.findViewById(R.id.tvTextNote);
            tvTagsForOneNote=view.findViewById(R.id.tagsForOneNote);
            linearLayout=view.findViewById(R.id.linearLayoutWithNote);
        }
    }

    public interface ItemListener {
        void onItemClick(Long position);
    }
}
