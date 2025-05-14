package com.example.task81candroidappexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        btnGo      = findViewById(R.id.btnGo);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etUsername.getText().toString().trim();
                if (user.isEmpty()) {
                    Toast.makeText(LoginActivity.this,
                            "Enter a username",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save username in SharedPreferences
                SharedPreferences prefs = getSharedPreferences("chat_prefs", MODE_PRIVATE);
                prefs.edit()
                        .putString("username", user)
                        .apply();

                // Launch chat screen
                Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
