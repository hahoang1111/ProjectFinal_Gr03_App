package com.example.projectfinal_gr03_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.projectfinal_gr03_app.dictionary.DictionaryA_V;

public class MainActivity extends AppCompatActivity {

    private TextView tvAppTitle;
    private ImageView imageView;
    private EditText etSearch;
    private CardView cardTranslate, cardDictionary, cardQuiz, cardMyWords;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        tvAppTitle = findViewById(R.id.tvAppTitle);
        imageView = findViewById(R.id.imageView);
        etSearch = findViewById(R.id.etSearch);
        cardTranslate = findViewById(R.id.cardTranslate);
        cardDictionary = findViewById(R.id.cardDictionary);
        cardQuiz = findViewById(R.id.cardQuiz);
        cardMyWords = findViewById(R.id.cardMyWords);

        // Kiểm tra ảnh động lực
        if (imageView.getDrawable() == null) {
            imageView.setImageResource(R.drawable.hkt_english_new); // Ảnh dự phòng
        }

        // Xử lý sự kiện click cho các CardView
        cardTranslate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TranslateActivity.class)));
        cardDictionary.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DictionaryA_V.class)));
        cardQuiz.setOnClickListener(v -> {
            try {
                // Mở màn hình quiz
                Intent intent = new Intent(MainActivity.this, QuizListActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });


        // Xử lý tìm kiếm (tùy chọn, nếu cần)
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            String query = etSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                // Chuyển query đến DictionaryA_V hoặc xử lý tìm kiếm
                Intent intent = new Intent(MainActivity.this, DictionaryA_V.class);
                intent.putExtra("search_query", query);
                startActivity(intent);
            }
            return true;
        });
    }
}