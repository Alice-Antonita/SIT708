package deakin.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import deakin.example.itubeapp.AppDatabase;
import deakin.example.itubeapp.UserDao;
import deakin.example.itubeapp.User;

public class SignupActivity extends AppCompatActivity {

    private EditText nameInput, usernameInput, passwordInput, confirmInput;
    private Button createAccountButton;

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameInput = findViewById(R.id.input_name);
        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        confirmInput = findViewById(R.id.input_confirm_password);
        createAccountButton = findViewById(R.id.button_create_account);

        userDao = AppDatabase.getInstance(this).userDao();

        createAccountButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = nameInput.getText().toString().trim();
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirm = confirmInput.getText().toString().trim();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirm)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            if (userDao.findByUsername(username) != null) {
                runOnUiThread(() -> Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show());
            } else {
                userDao.insertUser(new User(name, username, password));
                runOnUiThread(() -> {
                    Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    finish();
                });
            }
        }).start();
    }
}
