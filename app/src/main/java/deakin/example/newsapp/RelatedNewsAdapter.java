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

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.ViewHolder> {

    private List<News> relatedNews;

    public RelatedNewsAdapter(List<News> relatedNews) {
        this.relatedNews = relatedNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_related_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = relatedNews.get(position);
        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        holder.date.setText(news.getDate());

        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(news.getImageUrl())
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return relatedNews.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView description;
        TextView date;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.related_news_image);
            title = itemView.findViewById(R.id.related_news_title);
            description = itemView.findViewById(R.id.related_news_description);
            date = itemView.findViewById(R.id.related_news_date);
        }
    }
}
