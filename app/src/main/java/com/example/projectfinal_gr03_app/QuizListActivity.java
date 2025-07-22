package com.example.projectfinal_gr03_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class QuizListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        // Các nút để chọn bài quiz
        Button btnQuiz1 = findViewById(R.id.btnQuiz1);
        Button btnQuiz2 = findViewById(R.id.btnQuiz2);
        Button btnQuiz3 = findViewById(R.id.btnQuiz3);
        Button btnQuiz4 = findViewById(R.id.btnQuiz4);

        // Khi người dùng nhấn vào các quiz, sẽ chuyển sang màn hình QuizHandle để làm quiz
        btnQuiz1.setOnClickListener(v -> {
            Intent intent = new Intent(QuizListActivity.this, QuizHandleActivity.class);
            intent.putExtra("quiz_id", 1); // Truyền thông tin quiz 1
            startActivity(intent);
        });

        btnQuiz2.setOnClickListener(v -> {
            Intent intent = new Intent(QuizListActivity.this, QuizHandleActivity.class);
            intent.putExtra("quiz_id", 2); // Truyền thông tin quiz 2
            startActivity(intent);
        });

        btnQuiz3.setOnClickListener(v -> {
            Intent intent = new Intent(QuizListActivity.this, QuizHandleActivity.class);
            intent.putExtra("quiz_id", 3); // Truyền thông tin quiz 3
            startActivity(intent);
        });

        btnQuiz4.setOnClickListener(v -> {
            Intent intent = new Intent(QuizListActivity.this, QuizHandleActivity.class);
            intent.putExtra("quiz_id", 4); // Truyền thông tin quiz 4
            startActivity(intent);
        });
    }
}
