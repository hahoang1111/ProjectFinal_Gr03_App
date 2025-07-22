package com.example.projectfinal_gr03_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class QuizHandleActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    private int questionIndex = 0;
    private int score = 0;
    private List<String> incorrectQuestions = new ArrayList<>();

    private String[] questions;
    private String[][] options;
    private String[] correctAnswers;
    private String quizTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_handle);

        tvQuestion = findViewById(R.id.tvQuestion);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);

        int quizId = getIntent().getIntExtra("quiz_id", 0);

        // Chọn bài quiz theo quizId
        switch (quizId) {
            case 1:
                setUpBasicQuiz();
                quizTitle = "Basic Quiz";
                tvQuestion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.basic_quiz_color));  // Màu xanh cho câu hỏi
                break;
            case 2:
                setUpIntermediateQuiz();
                quizTitle = "Intermediate Quiz";
                tvQuestion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.intermediate_quiz_color));  // Màu cam cho câu hỏi
                break;
            case 3:
                setUpAdvancedQuiz();
                quizTitle = "Advanced Quiz";
                tvQuestion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.advanced_quiz_color));  // Màu xanh lá cho câu hỏi
                break;
            case 4:
                setUpExpertQuiz();
                quizTitle = "Expert Quiz";
                tvQuestion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.expert_quiz_color));  // Màu đỏ cho câu hỏi
                break;
            default:
                setUpBasicQuiz();
                quizTitle = "Basic Quiz";
                tvQuestion.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.basic_quiz_color));  // Màu xanh cho câu hỏi
                break;
        }

        loadQuestion();

        btnOption1.setOnClickListener(v -> checkAnswer(btnOption1));
        btnOption2.setOnClickListener(v -> checkAnswer(btnOption2));
        btnOption3.setOnClickListener(v -> checkAnswer(btnOption3));
        btnOption4.setOnClickListener(v -> checkAnswer(btnOption4));
    }

    private void setUpBasicQuiz() {
        questions = new String[]{
                "What is the synonym of 'Happy'?",
                "What is the opposite of 'Hot'?",
                "What is the synonym of 'Quick'?",
                "What is the opposite of 'Light'?",
                "What is the synonym of 'Beautiful'?",
                "What is the opposite of 'Cold'?",
                "What is the synonym of 'Strong'?",
                "What is the opposite of 'Big'?",
                "What is the synonym of 'Smart'?",
                "What is the opposite of 'Slow'?"
        };

        options = new String[][]{
                {"Sad", "Joyful", "Angry", "Bored"},
                {"Cold", "Warm", "Steamy", "Chilly"},
                {"Rapid", "Slow", "Heavy", "Loud"},
                {"Dark", "Light", "Bright", "Heavy"},
                {"Pretty", "Ugly", "Large", "Tall"},
                {"Hot", "Cold", "Warm", "Cool"},
                {"Weak", "Powerful", "Soft", "Flexible"},
                {"Small", "Big", "Huge", "Large"},
                {"Clever", "Stupid", "Bright", "Smart"},
                {"Fast", "Slow", "Rapid", "Quick"}
        };

        correctAnswers = new String[]{
                "Joyful", "Cold", "Rapid", "Dark", "Pretty", "Hot", "Powerful", "Small", "Clever", "Fast"
        };
    }

    private void setUpIntermediateQuiz() {
        questions = new String[]{
                "What is the synonym of 'Quick'?",
                "What is the opposite of 'Cold'?",
                "What is the synonym of 'Strong'?",
                "What is the opposite of 'Small'?",
                "What is the synonym of 'Beautiful'?",
                "What is the opposite of 'Light'?",
                "What is the synonym of 'Intelligent'?",
                "What is the opposite of 'Tall'?",
                "What is the synonym of 'Happy'?",
                "What is the opposite of 'Fast'?"
        };

        options = new String[][]{
                {"Rapid", "Slow", "Heavy", "Loud"},
                {"Hot", "Cold", "Warm", "Cool"},
                {"Weak", "Powerful", "Soft", "Flexible"},
                {"Large", "Small", "Big", "Short"},
                {"Pretty", "Ugly", "Large", "Tall"},
                {"Bright", "Dark", "Heavy", "Warm"},
                {"Clever", "Stupid", "Bright", "Smart"},
                {"Short", "Tall", "Small", "Wide"},
                {"Joyful", "Sad", "Angry", "Bored"},
                {"Slow", "Fast", "Rapid", "Quick"}
        };

        correctAnswers = new String[]{
                "Rapid", "Hot", "Powerful", "Large", "Pretty", "Dark", "Clever", "Short", "Joyful", "Slow"
        };
    }

    private void setUpAdvancedQuiz() {
        questions = new String[]{
                "What is the synonym of 'Beautiful'?",
                "What is the opposite of 'Light'?",
                "What is the synonym of 'Smart'?",
                "What is the opposite of 'Big'?",
                "What is the synonym of 'Quick'?",
                "What is the opposite of 'Cold'?",
                "What is the synonym of 'Powerful'?",
                "What is the opposite of 'Hot'?",
                "What is the synonym of 'Bright'?",
                "What is the opposite of 'Small'?"
        };

        options = new String[][]{
                {"Pretty", "Ugly", "Large", "Tall"},
                {"Dark", "Light", "Bright", "Heavy"},
                {"Clever", "Stupid", "Bright", "Smart"},
                {"Small", "Big", "Huge", "Large"},
                {"Rapid", "Slow", "Steady", "Lazy"},
                {"Hot", "Cold", "Warm", "Cool"},
                {"Weak", "Strong", "Fragile", "Feeble"},
                {"Cold", "Hot", "Warm", "Chilly"},
                {"Shiny", "Dull", "Bright", "Weak"},
                {"Big", "Small", "Tiny", "Huge"}
        };

        correctAnswers = new String[]{
                "Pretty", "Dark", "Clever", "Small", "Rapid", "Hot", "Strong", "Cold", "Bright", "Big"
        };
    }

    private void setUpExpertQuiz() {
        questions = new String[]{
                "What is the opposite of 'Fast'?",
                "What is the synonym of 'Rich'?",
                "What is the opposite of 'Big'?",
                "What is the synonym of 'Strong'?",
                "What is the opposite of 'Cold'?",
                "What is the synonym of 'Smart'?",
                "What is the opposite of 'Hot'?",
                "What is the synonym of 'Clever'?",
                "What is the opposite of 'Heavy'?",
                "What is the synonym of 'Weak'?"
        };

        options = new String[][]{
                {"Slow", "Quick", "Rapid", "Rush"},
                {"Wealthy", "Poor", "Broke", "Rich"},
                {"Small", "Big", "Huge", "Large"},
                {"Powerful", "Weak", "Fragile", "Feeble"},
                {"Hot", "Cold", "Warm", "Cool"},
                {"Bright", "Clever", "Stupid", "Dull"},
                {"Cold", "Hot", "Warm", "Chilly"},
                {"Intelligent", "Stupid", "Clever", "Boring"},
                {"Light", "Heavy", "Strong", "Weak"},
                {"Strong", "Weak", "Fragile", "Feeble"}
        };

        correctAnswers = new String[]{
                "Slow", "Wealthy", "Small", "Powerful", "Hot", "Clever", "Cold", "Intelligent", "Light", "Weak"
        };
    }

    private void loadQuestion() {
        tvQuestion.setText(questions[questionIndex]);
        btnOption1.setText(options[questionIndex][0]);
        btnOption2.setText(options[questionIndex][1]);
        btnOption3.setText(options[questionIndex][2]);
        btnOption4.setText(options[questionIndex][3]);
    }

    private void checkAnswer(Button selectedOption) {
        String selectedAnswer = selectedOption.getText().toString();
        if (selectedAnswer.equals(correctAnswers[questionIndex])) {
            score++;
        } else {
            incorrectQuestions.add(questions[questionIndex] + " (Answer: " + correctAnswers[questionIndex] + ")");
        }

        questionIndex++;
        if (questionIndex < questions.length) {
            loadQuestion();
        } else {
            showResult();
        }
    }


    private void showResult() {
        Toast.makeText(this, quizTitle + " - Your score: " + score + "/" + questions.length, Toast.LENGTH_LONG).show();
        if (!incorrectQuestions.isEmpty()) {
            StringBuilder incorrectAnswers = new StringBuilder("Incorrect Answers:\n");
            for (String question : incorrectQuestions) {
                incorrectAnswers.append(question).append("\n");
            }
            Toast.makeText(this, incorrectAnswers.toString(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You got all answers correct!", Toast.LENGTH_LONG).show();
        }

        // Chuyển sang màn hình kết quả quiz
        Intent resultIntent = new Intent(QuizHandleActivity.this, QuizResultActivity.class);
        resultIntent.putExtra("score", score);
        resultIntent.putStringArrayListExtra("incorrect_questions", (ArrayList<String>) incorrectQuestions);
        startActivity(resultIntent);
        finish();
    }
}
