package com.example.week7sqliteandroom_workshop_starter_code;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    public interface OnClickListener {
        void onItemClick(LostFoundItem item);
    }

    private final List<LostFoundItem> items;
    private final OnClickListener listener;

    public ItemAdapter(List<LostFoundItem> items, OnClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvType, tvDesc, tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvRowType);
            tvDesc = itemView.findViewById(R.id.tvRowDesc);
            tvDate = itemView.findViewById(R.id.tvRowDate);

            itemView.setOnClickListener(v ->
                    listener.onItemClick(items.get(getAdapterPosition()))
            );
        }

        void bind(LostFoundItem item) {
            tvType.setText(item.getType());
            tvDesc.setText(item.getDescription());
            tvDate.setText(item.getDate());
        }
    }
}