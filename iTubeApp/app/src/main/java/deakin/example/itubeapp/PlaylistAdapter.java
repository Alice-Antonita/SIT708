package deakin.example.itubeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private List<PlaylistItem> items;

    public PlaylistAdapter(List<PlaylistItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        PlaylistItem item = items.get(position);
        holder.urlText.setText(item.videoUrl);

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("videoUrl", item.videoUrl);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView urlText;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            urlText = itemView.findViewById(R.id.text_video_url);
        }
    }
}
