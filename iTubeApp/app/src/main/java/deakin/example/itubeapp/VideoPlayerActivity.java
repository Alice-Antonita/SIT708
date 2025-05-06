package deakin.example.itubeapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {

    private WebView webView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        webView = findViewById(R.id.web_view);
        backButton = findViewById(R.id.button_back);

        String videoUrl = getIntent().getStringExtra("videoUrl");

        if (videoUrl == null || videoUrl.trim().isEmpty()) {
            Toast.makeText(this, "Invalid video URL", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String videoId = extractVideoId(videoUrl);
        if (videoId.isEmpty()) {
            Toast.makeText(this, "Unable to extract video ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupWebView(videoId);

        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void setupWebView(String videoId) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String html = "<html><body style=\"margin:0;padding:0;\">" +
                "<iframe width=\"100%\" height=\"100%\" " +
                "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=1\" " +
                "frameborder=\"0\" allowfullscreen allow=\"autoplay\"></iframe>" +
                "</body></html>";

        webView.loadData(html, "text/html", "utf-8");
    }

    private String extractVideoId(String url) {
        if (url.contains("youtu.be/")) {
            return url.substring(url.lastIndexOf("/") + 1).split("\\?")[0];
        } else if (url.contains("watch?v=")) {
            return url.split("watch\\?v=")[1].split("&")[0];
        }
        return "";
    }
}
