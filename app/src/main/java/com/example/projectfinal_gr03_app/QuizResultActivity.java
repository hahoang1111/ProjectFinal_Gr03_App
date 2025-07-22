package com.example.projectfinal_gr03_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuizResultActivity extends AppCompatActivity {

    private TextView tvResult, tvScore, tvIncorrectAnswers, tvAnswerList;
    private Button btnBackToMain,btnRetryQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        // Ánh xạ các view
        tvResult = findViewById(R.id.tvResult);
        tvScore = findViewById(R.id.tvScore);
        tvIncorrectAnswers = findViewById(R.id.tvIncorrectAnswers);
        tvAnswerList = findViewById(R.id.tvAnswerList);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnRetryQuiz = findViewById(R.id.btnRetryQuiz);


        // Nhận điểm số và các câu sai từ QuizHandleActivity
        int score = getIntent().getIntExtra("score", 0);
        ArrayList<String> incorrectQuestions = getIntent().getStringArrayListExtra("incorrect_questions");

        tvResult.setText("Your score");

        // Nếu người dùng làm đúng hết
        if (incorrectQuestions == null || incorrectQuestions.isEmpty()) {
            tvScore.setText("Score: " + score);
            tvScore.setTextColor(getResources().getColor(android.R.color.holo_orange_light));  // Màu vàng sáng cho điểm số
            tvIncorrectAnswers.setText("You got all answers correct!");
            tvIncorrectAnswers.setTextColor(getResources().getColor(android.R.color.holo_orange_light));  // Màu vàng sáng
        } else {
            // Nếu có câu sai
            tvScore.setText("Score: " + score);
            tvScore.setTextColor(getResources().getColor(android.R.color.holo_red_light));  // Màu đỏ cho điểm số khi có sai
            tvIncorrectAnswers.setText("Incorrect Answers:");
            tvIncorrectAnswers.setTextColor(getResources().getColor(android.R.color.holo_red_light));  // Màu đỏ cho câu trả lời sai

            StringBuilder incorrectAnswers = new StringBuilder();
            for (String question : incorrectQuestions) {
                incorrectAnswers.append(question).append("\n");
            }
            tvAnswerList.setText(incorrectAnswers.toString());
            tvAnswerList.setTextColor(getResources().getColor(android.R.color.holo_red_light));  // Màu đỏ cho câu trả lời sai
        }

        btnBackToMain.setOnClickListener(v -> {
            // Điều hướng về MainActivity
            Intent intent = new Intent(QuizResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Đóng QuizResultActivity
        });
        btnRetryQuiz.setOnClickListener(v -> {
            // Quay lại màn hình QuizHandleActivity để làm lại quiz
            Intent retryIntent = new Intent(QuizResultActivity.this, QuizListActivity.class);
            startActivity(retryIntent);
            finish();  // Đóng màn hình kết quả để quay lại QuizListActivity
        });

    }

}
