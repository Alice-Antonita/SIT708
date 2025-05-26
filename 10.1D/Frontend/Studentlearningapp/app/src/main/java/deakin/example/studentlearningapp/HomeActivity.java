package deakin.example.studentlearningapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.cardTask).setOnClickListener(v -> {
            startActivity(new Intent(this, TaskActivity.class));
        });
    }
}
