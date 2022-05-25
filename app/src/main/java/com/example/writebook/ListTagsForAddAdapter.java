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

public class ListTagsForAddAdapter extends RecyclerView.Adapter<ListTagsForAddAdapter.MyViewHolder>{

    public interface ItemListener {
        void onItemClick(Long position);
    }

    private Context context;
    private List<Tag> tagList;
    private ItemListener itemListener;

    public ListTagsForAddAdapter(Context context,ItemListener itemListener) {
        this.context = context;
        this.itemListener=itemListener;
    }

    @NonNull
    @Override
    public ListTagsForAddAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row_for_add_tags, parent, false);

        return new ListTagsForAddAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTagsForAddAdapter.MyViewHolder holder, int position) {
        holder.nameOfAddTag.setText(this.tagList.get(position).getText());
        Long id=this.tagList.get(position).getTagId();
        holder.addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addTag.setEnabled(false);
                itemListener.onItemClick(id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.tagList.size();
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfAddTag;
        Button addTag;

        public MyViewHolder(View view) {
            super(view);
            nameOfAddTag = view.findViewById(R.id.nameOfAddTag);
            addTag=view.findViewById(R.id.addTag);
        }
    }

}
