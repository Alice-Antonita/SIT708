package deakin.example.itubeapp;

import android.os.Bundle;

import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private PlaylistDao playlistDao;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        userId = getIntent().getIntExtra("userId", -1);
        recyclerView = findViewById(R.id.recycler_playlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        playlistDao = AppDatabase.getInstance(this).playlistDao();

        new Thread(() -> {
            List<PlaylistItem> items = playlistDao.getUserPlaylist(userId);
            runOnUiThread(() -> {
                adapter = new PlaylistAdapter(items);
                recyclerView.setAdapter(adapter);
            });
        }).start();

        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> onBackPressed());

    }
}
