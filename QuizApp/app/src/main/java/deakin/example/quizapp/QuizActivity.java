package deakin.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    TextView questionText, progressText;
    RadioGroup answersGroup;
    RadioButton option1, option2, option3;
    Button submitBtn, nextBtn;
    ProgressBar progressBar;

    List<Question> questions;
    int currentQuestionIndex = 0;
    int score = 0;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.textQuestion);
        progressText = findViewById(R.id.textProgress);
        answersGroup = findViewById(R.id.radioGroup);
        option1 = findViewById(R.id.radio1);
        option2 = findViewById(R.id.radio2);
        option3 = findViewById(R.id.radio3);
        submitBtn = findViewById(R.id.buttonSubmit);
        nextBtn = findViewById(R.id.buttonNext);
        progressBar = findViewById(R.id.progressBar);

        userName = getIntent().getStringExtra("userName");
        questions = QuestionBank.getQuestions();

        loadQuestion();

        submitBtn.setOnClickListener(v -> checkAnswer());
        nextBtn.setOnClickListener(v -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                loadQuestion();
            } else {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("userName", userName);
                startActivity(intent);
                finish();
            }
        });
    }

    void loadQuestion() {
        answersGroup.clearCheck();
        resetColors();
        Question q = questions.get(currentQuestionIndex);
        questionText.setText(q.getQuestion());
        option1.setText(q.getOption1());
        option2.setText(q.getOption2());
        option3.setText(q.getOption3());

        int progress = (currentQuestionIndex + 1) * 100 / questions.size();
        progressBar.setProgress(progress);
        progressText.setText((currentQuestionIndex + 1) + "/" + questions.size());

        submitBtn.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.GONE);
    }

    void checkAnswer() {
        int selectedId = answersGroup.getCheckedRadioButtonId();
        if (selectedId == -1) return;

        RadioButton selectedBtn = findViewById(selectedId);
        Question q = questions.get(currentQuestionIndex);

        if (selectedBtn.getText().equals(q.getCorrectAnswer())) {
            selectedBtn.setBackgroundColor(Color.GREEN);
            score++;
        } else {
            selectedBtn.setBackgroundColor(Color.RED);
            if (option1.getText().equals(q.getCorrectAnswer())) option1.setBackgroundColor(Color.GREEN);
            if (option2.getText().equals(q.getCorrectAnswer())) option2.setBackgroundColor(Color.GREEN);
            if (option3.getText().equals(q.getCorrectAnswer())) option3.setBackgroundColor(Color.GREEN);
        }
        submitBtn.setVisibility(View.GONE);
        nextBtn.setVisibility(View.VISIBLE);
    }

    void resetColors() {
        option1.setBackgroundColor(Color.TRANSPARENT);
        option2.setBackgroundColor(Color.TRANSPARENT);
        option3.setBackgroundColor(Color.TRANSPARENT);
    }
}
