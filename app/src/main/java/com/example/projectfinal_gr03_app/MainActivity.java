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

public class MainActivity extends AppCompatActivity {

    // Khai báo các thành phần giao diện (UI)
    private TextView tvAppTitle;
    private ImageView imageView;
    private EditText etSearch;
    private CardView cardTranslate, cardDictionary, cardQuiz, cardMyWords;

    @SuppressLint("MissingInflatedId") // Báo cho Android Studio biết bỏ qua cảnh báo thiếu ID layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Gán layout XML cho màn hình MainActivity

        // Ánh xạ (liên kết) các thành phần trong layout với biến Java
        tvAppTitle = findViewById(R.id.tvAppTitle);             // Tiêu đề ứng dụng
        imageView = findViewById(R.id.imageView);               // Ảnh đầu trang hoặc ảnh quảng bá
        etSearch = findViewById(R.id.etSearch);                 // Ô tìm kiếm
        cardTranslate = findViewById(R.id.cardTranslate);       // CardView cho tính năng "Dịch văn bản"
        cardDictionary = findViewById(R.id.cardDictionary);     // CardView cho "Từ điển Anh-Việt"
        cardQuiz = findViewById(R.id.cardQuiz);                 // CardView cho "Quiz tiếng Anh"
        cardMyWords = findViewById(R.id.cardMyWords);           // CardView cho "Từ của bạn"

        // Kiểm tra nếu ImageView chưa có ảnh thì gán ảnh mặc định
        if (imageView.getDrawable() == null) {
            imageView.setImageResource(R.drawable.hkt_english_new); // Gán ảnh dự phòng nếu chưa có
        }

        // Bắt sự kiện khi người dùng nhấn vào cardTranslate → chuyển sang màn hình dịch
        cardTranslate.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, TranslateActivity.class))
        );

        // Bắt sự kiện khi người dùng nhấn vào cardDictionary → chuyển sang màn hình từ điển
        cardDictionary.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DictionaryA_V.class))
        );

        // Bắt sự kiện khi nhấn vào cardQuiz → mở màn hình quiz
        cardQuiz.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, QuizListActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace(); // In lỗi ra Logcat nếu có
                Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện khi người dùng gõ từ khóa và nhấn Enter trong ô tìm kiếm
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            String query = etSearch.getText().toString().trim(); // Lấy nội dung tìm kiếm
            if (!query.isEmpty()) {
                // Chuyển sang màn hình từ điển và truyền từ khóa tìm kiếm qua Intent
                Intent intent = new Intent(MainActivity.this, DictionaryA_V.class);
                intent.putExtra("search_query", query); // Gửi từ khóa tìm kiếm
                startActivity(intent);
            }
            return true; // Đánh dấu đã xử lý sự kiện
        });
    }
}
