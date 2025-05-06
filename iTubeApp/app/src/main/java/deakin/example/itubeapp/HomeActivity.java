package deakin.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private EditText urlInput;
    private Button playButton, addToPlaylistButton, myPlaylistButton;

    private int userId;
    private PlaylistDao playlistDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userId = getIntent().getIntExtra("userId", -1);

        urlInput = findViewById(R.id.input_url);
        playButton = findViewById(R.id.button_play);
        addToPlaylistButton = findViewById(R.id.button_add_to_playlist);
        myPlaylistButton = findViewById(R.id.button_my_playlist);

        playlistDao = AppDatabase.getInstance(this).playlistDao();

        playButton.setOnClickListener(v -> {
            String url = urlInput.getText().toString().trim();
            if (!url.isEmpty()) {
                Intent intent = new Intent(HomeActivity.this, VideoPlayerActivity.class);
                intent.putExtra("videoUrl", url);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please enter a YouTube URL", Toast.LENGTH_SHORT).show();
            }
        });

        addToPlaylistButton.setOnClickListener(v -> {
            String url = urlInput.getText().toString().trim();
            if (!url.isEmpty()) {
                new Thread(() -> {
                    playlistDao.addToPlaylist(new PlaylistItem(userId, url));
                    runOnUiThread(() -> Toast.makeText(this, "Added to playlist", Toast.LENGTH_SHORT).show());
                }).start();
            }
        });

        myPlaylistButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PlaylistActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }
}
