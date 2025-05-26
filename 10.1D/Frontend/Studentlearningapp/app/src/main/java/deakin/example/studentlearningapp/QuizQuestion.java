package deakin.example.studentlearningapp;

import java.util.List;

public class QuizQuestion {
    private String question;
    private List<String> options;
    private String correctAnswer;
    private int selectedOptionIndex = -1;  // -1 means not selected

    public QuizQuestion(String question, List<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() { return question; }

    public List<String> getOptions() { return options; }

    public String getCorrectAnswer() { return correctAnswer; }

    public int getSelectedOptionIndex() { return selectedOptionIndex; }

    public void setSelectedOptionIndex(int index) { this.selectedOptionIndex = index; }
}
