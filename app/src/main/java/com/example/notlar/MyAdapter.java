package com.example.notlar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final ItemClickListener itemClickListener;

    private List<MyData> dataList;





    public MyAdapter(List<MyData> dataList,ItemClickListener itemClickListener) {
        this.dataList = dataList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyData data = dataList.get(position);
        holder.bindData(data);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView notes,noteTitle;

        public MyViewHolder(@NonNull View itemView,ItemClickListener itemClickListener) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.itemTitle);
            notes = itemView.findViewById(R.id.itemNotes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            itemClickListener.onItemClick(pos);
                        }
                    }
                }
            });
        }


        public void bindData(MyData data) {
            noteTitle.setText(data.getTxtTitle());
            notes.setText(data.getTxtNotes());
        }
    }


}