package deakin.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.ViewHolder> {

    private List<News> topStories;
    private OnNewsClickListener listener;

    public interface OnNewsClickListener {
        void onNewsClick(News news);
    }

    public TopStoriesAdapter(List<News> topStories, OnNewsClickListener listener) {
        this.topStories = topStories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News story = topStories.get(position);
        holder.title.setText(story.getTitle());

        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(story.getImageUrl())
                .centerCrop()
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> listener.onNewsClick(story));
    }

    @Override
    public int getItemCount() {
        return topStories.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.top_story_image);
            title = itemView.findViewById(R.id.top_story_title);
        }
    }
}
