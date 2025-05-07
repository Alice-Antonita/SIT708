package deakin.example.newsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsDetailFragment extends Fragment {

    private static final String ARG_NEWS = "news";
    private News news;
    private RecyclerView relatedNewsRecyclerView;

    public static NewsDetailFragment newInstance(News news) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS, (Serializable) news);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            news = (News) getArguments().getSerializable(ARG_NEWS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        ImageView newsImage = view.findViewById(R.id.news_detail_image);
        TextView newsTitle = view.findViewById(R.id.news_detail_title);
        TextView newsDate = view.findViewById(R.id.news_detail_date);
        TextView newsDescription = view.findViewById(R.id.news_detail_description);
        TextView backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        if (news != null) {
            newsTitle.setText(news.getTitle());
            newsDate.setText(news.getDate());
            newsDescription.setText(news.getDescription());

            Glide.with(this)
                    .load(news.getImageUrl())
                    .centerCrop()
                    .into(newsImage);
        }

        relatedNewsRecyclerView = view.findViewById(R.id.related_news_recycler_view);
        relatedNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RelatedNewsAdapter relatedNewsAdapter = new RelatedNewsAdapter(getRelatedNews());
        relatedNewsRecyclerView.setAdapter(relatedNewsAdapter);
        return view;
    }

    private List<News> getRelatedNews() {
        List<News> relatedNews = new ArrayList<>();

        relatedNews.add(new News(
                301,
                "Mars Habitat Prototype Completed",
                "Space engineers complete the first prototype of a livable habitat module for future Mars missions.",
                "https://images.unsplash.com/photo-1590484375193-e874932dee5e",
                "May 1, 2025"
        ));

        relatedNews.add(new News(
                302,
                "Breakthrough in Wireless Charging",
                "A new long-range wireless charging technology could change the way we power devices.",
                "https://images.unsplash.com/photo-1598978465764-7db2b679c694",
                "April 30, 2025"
        ));

        return relatedNews;
    }

}
