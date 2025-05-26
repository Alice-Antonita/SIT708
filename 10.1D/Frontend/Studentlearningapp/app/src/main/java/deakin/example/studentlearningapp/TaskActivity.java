package deakin.example.studentlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private static final String TAG = "TaskActivity";
    private LinearLayout loadingContainer;
    private RecyclerView recyclerQuiz;
    private List<QuizQuestion> questionList;
    private QuizAdapter quizAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        loadingContainer = findViewById(R.id.loadingContainer);
        recyclerQuiz = findViewById(R.id.recyclerQuiz);
        recyclerQuiz.setLayoutManager(new LinearLayoutManager(this));

        questionList = new ArrayList<>();
        quizAdapter = new QuizAdapter(this, questionList);
        recyclerQuiz.setAdapter(quizAdapter);

        findViewById(R.id.btnSubmit).setOnClickListener(v -> {
            int correct = 0;
            ArrayList<String> correctAnswers = new ArrayList<>();
            for (QuizQuestion q : questionList) {
                int selectedIndex = q.getSelectedOptionIndex();
                String selected = selectedIndex != -1 ? q.getOptions().get(selectedIndex) : "Not answered";
                correctAnswers.add("Q: " + q.getQuestion() +
                        "\nYour Answer: " + selected +
                        "\nCorrect Answer: " + q.getCorrectAnswer());
            }
            AppDatabase db = AppDatabase.getInstance(this);
            for (QuizQuestion q : questionList) {
                QuizHistory h = new QuizHistory();
                h.question      = q.getQuestion();
                h.yourAnswer    = (q.getSelectedOptionIndex() != -1)
                        ? q.getOptions().get(q.getSelectedOptionIndex())
                        : "Not answered";
                h.correctAnswer = q.getCorrectAnswer();
                h.timestamp     = System.currentTimeMillis();
                db.historyDao().insert(h);
            }

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("score", correct);
            intent.putExtra("total", questionList.size());
            intent.putStringArrayListExtra("answers", correctAnswers);
            startActivity(intent);
        });

        fetchQuizData();
    }

    private void fetchQuizData() {
        String url = "http://10.0.2.2:5000/getQuiz?topic=Web%20Development";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    loadingContainer.setVisibility(View.GONE);
                    try {
                        JSONArray quizArray = response.getJSONArray("quiz");
                        questionList.clear();

                        for (int i = 0; i < quizArray.length(); i++) {
                            JSONObject quizObj = quizArray.getJSONObject(i);
                            String question = quizObj.getString("question");
                            String correctAnswer = quizObj.getString("correct_answer");

                            JSONArray optionsJson = quizObj.getJSONArray("options");
                            List<String> options = new ArrayList<>();
                            for (int j = 0; j < optionsJson.length(); j++) {
                                options.add(optionsJson.getString(j));
                            }

                            QuizQuestion qq = new QuizQuestion(question, options, correctAnswer);
                            questionList.add(qq);
                        }

                        quizAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Quiz loaded!", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing JSON: " + e.getMessage(), e);
                        Toast.makeText(this, "Error parsing quiz.", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    loadingContainer.setVisibility(View.GONE);
                    Log.e(TAG, "Volley error: " + error.toString());
                    Toast.makeText(this, "Failed to load quiz.", Toast.LENGTH_LONG).show();
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        loadingContainer.setVisibility(View.VISIBLE);
        queue.add(jsonObjectRequest);
    }
}
