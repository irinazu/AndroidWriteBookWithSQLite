package com.example.writebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writebook.model.Tag;

import java.util.List;


public class ListTagsForDeleteAdapter extends RecyclerView.Adapter<ListTagsForDeleteAdapter.MyViewHolder>{

    public interface ItemListener {
        void onItemClick(Long position);
    }

    private Context context;
    private List<Tag> tagListFroDelete;
    private ItemListener itemListener;

    public ListTagsForDeleteAdapter(Context context,ItemListener itemListener) {
        this.context = context;
        this.itemListener=itemListener;
    }

    @NonNull
    @Override
    public ListTagsForDeleteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row_for_delete_tag, parent, false);

        return new ListTagsForDeleteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTagsForDeleteAdapter.MyViewHolder holder, int position) {
        holder.nameOfAddTag.setText(this.tagListFroDelete.get(position).getText());
        Long id=this.tagListFroDelete.get(position).getTagId();
        holder.deleteTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClick(id);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(tagListFroDelete==null){
            return 0;
        }else {
            return this.tagListFroDelete.size();
        }
    }

    public void setTagList(List<Tag> tagListFroDelete) {
        this.tagListFroDelete = tagListFroDelete;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfAddTag;
        Button deleteTag;

        public MyViewHolder(View view) {
            super(view);
            nameOfAddTag = view.findViewById(R.id.nameOfAddTag);
            deleteTag=view.findViewById(R.id.deleteTag);
        }
    }
}
