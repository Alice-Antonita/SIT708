package deakin.example.quizapp;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    public static List<Question> getQuestions() {
        List<Question> list = new ArrayList<>();
        list.add(new Question("What is Android?", "OS", "Browser", "Game", "OS"));
        list.add(new Question("What is Java?", "OS", "Language", "Software", "Language"));
        list.add(new Question("Android is based on?", "Linux", "Mac", "iOS", "Linux"));
        list.add(new Question("APK stands for?", "Android Package", "App Kernel", "Advanced Package", "Android Package"));
        list.add(new Question("Main component to build UI?", "Activity", "Service", "Broadcast", "Activity"));
        return list;
    }
}