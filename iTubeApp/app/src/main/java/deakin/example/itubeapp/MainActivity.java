package deakin.example.itubeapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton, signupButton;

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.button_login);
        signupButton = findViewById(R.id.button_signup);

        userDao = AppDatabase.getInstance(this).userDao();

        loginButton.setOnClickListener(v -> login());
        signupButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
        });
    }

    private void login() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            User user = userDao.login(username, password);
            runOnUiThread(() -> {
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("userId", user.id); // pass user ID to home
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }
}
