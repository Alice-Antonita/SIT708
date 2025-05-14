package com.example.task81candroidappexample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private EditText chatInputBox;
    private Button sendButton;
    private ProgressBar progressBar;
    private RecyclerView rvChat;
    private ChatAdapter adapter;
    private List<ChatMessage> buffer;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatInputBox = findViewById(R.id.chatInputBox);
        sendButton   = findViewById(R.id.sendButton);
        progressBar  = findViewById(R.id.progressBar);
        rvChat       = findViewById(R.id.rvChat);

        // Load username (if you still need it elsewhere)
        SharedPreferences prefs = getSharedPreferences("chat_prefs", MODE_PRIVATE);
        username = prefs.getString("username", "User");

        buffer = new ArrayList<>();
        adapter = new ChatAdapter(buffer);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(adapter);

        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String msg = chatInputBox.getText().toString().trim();
        if (msg.isEmpty()) {
            Toast.makeText(this, "Enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show user bubble immediately
        adapter.addMessage(new ChatMessage(ChatMessage.Sender.USER, msg));
        rvChat.scrollToPosition(buffer.size() - 1);
        chatInputBox.setText("");

        // Show spinner
        progressBar.setVisibility(ProgressBar.VISIBLE);

        String url = "http://10.0.2.2:5000/chat";
        // Use StringRequest to POST form data
        StringRequest req = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    // Hide spinner
                    progressBar.setVisibility(ProgressBar.GONE);
                    // response is plain text from Flask
                    adapter.addMessage(new ChatMessage(ChatMessage.Sender.BOT, response));
                    rvChat.scrollToPosition(buffer.size() - 1);
                },
                error -> {
                    progressBar.setVisibility(ProgressBar.GONE);
                    Toast.makeText(
                            this,
                            "Network error: " + error.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                // form-encode this field so Flask finds it in request.form
                Map<String, String> params = new HashMap<>();
                params.put("userMessage", msg);
                return params;
            }
        };

        // Give it a proper timeout
        req.setRetryPolicy(new DefaultRetryPolicy(
                30_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Enqueue
        Volley.newRequestQueue(this).add(req);
    }
}
