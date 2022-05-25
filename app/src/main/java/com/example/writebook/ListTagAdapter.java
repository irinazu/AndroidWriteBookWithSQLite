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
import com.example.writebook.model.Tag;

import java.util.List;



public class ListTagAdapter extends RecyclerView.Adapter<ListTagAdapter.MyViewHolder> {

    private Context context;
    private List<Tag> tagList;
    private ItemListener itemListener;

    @NonNull
    @Override
    public ListTagAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row_tag, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTagAdapter.MyViewHolder holder, int position) {
        holder.tvTextTag.setText(this.tagList.get(position).getText());
        Long aLong=this.tagList.get(position).getTagId();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemClick(aLong);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.tagList.size();
    }

    public ListTagAdapter(Context context, ItemListener itemListener) {
        this.context = context;
        this.itemListener=itemListener;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTextTag;
        LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            tvTextTag = view.findViewById(R.id.tvTag);
            linearLayout=view.findViewById(R.id.linearLayoutWithTag);

        }
    }

    public interface ItemListener {
        void onItemClick(Long position);
    }
}
