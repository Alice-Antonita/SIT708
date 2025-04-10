package deakin.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView resultText;
    Button newQuizBtn, finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultText = findViewById(R.id.textResult);
        newQuizBtn = findViewById(R.id.buttonNewQuiz);
        finishBtn = findViewById(R.id.buttonFinish);

        int score = getIntent().getIntExtra("score", 0);
        String name = getIntent().getStringExtra("userName");

        resultText.setText("Congratulations " + name + "!\nYOUR SCORE: " + score + "/5");

        newQuizBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        finishBtn.setOnClickListener(v -> finishAffinity());
    }
}