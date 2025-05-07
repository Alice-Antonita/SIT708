package deakin.example.newsapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private OnNewsClickListener listener;

    public interface OnNewsClickListener {
        void onNewsClicked(News news);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsClickListener) {
            listener = (OnNewsClickListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnNewsClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerViews
        RecyclerView topStoriesRecyclerView = view.findViewById(R.id.top_stories_recycler_view);
        RecyclerView newsRecyclerView = view.findViewById(R.id.news_recycler_view);

        // Set layout managers
        LinearLayoutManager topStoriesLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager newsLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false);

        topStoriesRecyclerView.setLayoutManager(topStoriesLayoutManager);
        newsRecyclerView.setLayoutManager(newsLayoutManager);

        // Set adapters
        TopStoriesAdapter topStoriesAdapter = new TopStoriesAdapter(getDummyTopStories(), news -> listener.onNewsClicked(news));
        NewsAdapter newsAdapter = new NewsAdapter(getDummyNews(), news -> listener.onNewsClicked(news));

        topStoriesRecyclerView.setAdapter(topStoriesAdapter);
        newsRecyclerView.setAdapter(newsAdapter);

        return view;
    }

    private List<News> getDummyTopStories() {
        List<News> topStories = new ArrayList<>();

        topStories.add(new News(
                1,
                "First 3D-Printed House Built in Under 24 Hours",
                "Engineers complete the world's fastest-printed home using sustainable materials and automated robotics.",
                "https://images.unsplash.com/photo-1600585154340-be6161a56a0c",
                "April 30, 2025"
        ));

        topStories.add(new News(
                2,
                "Solar-Powered Car Breaks World Record",
                "A student-built solar vehicle travels over 1,500km on a single charge in record-setting demonstration.",
                "https://images.unsplash.com/photo-1509391366360-2e959784a276",
                "April 29, 2025"
        ));

        return topStories;
    }


    private List<News> getDummyNews() {
        List<News> news = new ArrayList<>();

        news.add(new News(
                3,
                "Digital Nomads Boost Local Economies in Rural Towns",
                "Remote workers flock to small communities, bringing both opportunity and challenges.",
                "https://images.unsplash.com/photo-1584203603521-53b2473cdb60",
                "April 30, 2025"
        ));

        news.add(new News(
                4,
                "New VR Therapy Shows Promise for PTSD Treatment",
                "Clinical trials reveal positive results using virtual reality exposure to treat trauma symptoms.",
                "https://images.unsplash.com/photo-1605647540924-852290f6b0d5",
                "April 28, 2025"
        ));

        return news;
    }

}
