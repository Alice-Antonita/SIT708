package deakin.example.studentlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UserDatabase db = UserDatabase.getInstance(this);
        UserDao dao = db.userDao();

        User user = new User();
        user.username = ((EditText)findViewById(R.id.etUsername)).getText().toString();
        user.email = ((EditText)findViewById(R.id.etEmail)).getText().toString();
        user.password = ((EditText)findViewById(R.id.etPassword)).getText().toString();
        user.phone = ((EditText)findViewById(R.id.etPhone)).getText().toString();

        dao.insert(user);

        findViewById(R.id.btnCreateAccount).setOnClickListener(v -> {
            startActivity(new Intent(this, InterestActivity.class));
        });
    }
}