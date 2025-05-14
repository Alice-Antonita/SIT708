package com.example.task81candroidappexample;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ChatMessage> messages;

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @Override public int getItemViewType(int pos) {
        return messages.get(pos).getSender() == ChatMessage.Sender.USER
                ? R.layout.item_user_bubble
                : R.layout.item_bot_bubble;
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new RecyclerView.ViewHolder(v) {} ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        TextView tv = holder.itemView.findViewById(R.id.tvMessage);
        tv.setText(messages.get(pos).getText());
    }

    @Override public int getItemCount() { return messages.size(); }

    // helper to add a message
    public void addMessage(ChatMessage msg) {
        messages.add(msg);
        notifyItemInserted(messages.size() - 1);
    }
}
