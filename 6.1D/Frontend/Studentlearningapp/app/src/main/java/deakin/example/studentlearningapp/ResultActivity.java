package deakin.example.studentlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);
        ArrayList<String> answers = getIntent().getStringArrayListExtra("answers");

        TextView tvResult = findViewById(R.id.tvResult);
        tvResult.setText("You scored " + score + " out of " + total);

        ListView lvAnswers = findViewById(R.id.lvAnswers);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, answers);
        lvAnswers.setAdapter(adapter);

        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}
