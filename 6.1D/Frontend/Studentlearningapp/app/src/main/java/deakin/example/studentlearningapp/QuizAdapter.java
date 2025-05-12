package deakin.example.studentlearningapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private final Context context;
    private final List<QuizQuestion> questionList;

    public QuizAdapter(Context context, List<QuizQuestion> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_question, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        QuizQuestion question = questionList.get(position);
        holder.tvQuestion.setText("Q" + (position + 1) + ": " + question.getQuestion());

        holder.radioGroup.removeAllViews();
        List<String> options = question.getOptions();

        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(options.get(i));
            radioButton.setId(i);
            holder.radioGroup.addView(radioButton);
        }

        if (question.getSelectedOptionIndex() != -1) {
            holder.radioGroup.check(question.getSelectedOptionIndex());
        }

        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            question.setSelectedOptionIndex(checkedId);
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion;
        RadioGroup radioGroup;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            radioGroup = itemView.findViewById(R.id.radioGroupOptions);
        }
    }
}
