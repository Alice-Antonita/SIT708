package deakin.example.studentlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;

public class InterestActivity extends AppCompatActivity {

    String[] topics = {"Algorithms", "Data Structures", "Web Development", "Software Testing"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        GridLayout grid = findViewById(R.id.gridInterests);

        for (String topic : topics) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(topic);
            grid.addView(checkBox);
        }

        findViewById(R.id.btnNext).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
        });
    }
}
