package com.example.projectfinal_gr03_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText; // Import EditText
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class YourWordActivity extends AppCompatActivity {

    private RecyclerView rvYourWords;
    private Button btnClearYourWords;
    private YourWordAdapter yourWordAdapter;
    private ArrayList<YourWordModel> yourWordList;
    private AppDatabase database;
    private ExecutorService executorService;

    // Các thành phần mới
    private EditText edtEnglishWord;
    private EditText edtVietnameseTranslation;
    private Button btnSaveNewWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_word);

        rvYourWords = findViewById(R.id.rvYourWords);
        btnClearYourWords = findViewById(R.id.btnClearYourWords);

        // Ánh xạ các thành phần mới
        edtEnglishWord = findViewById(R.id.edtEnglishWord);
        edtVietnameseTranslation = findViewById(R.id.edtVietnameseTranslation);
        btnSaveNewWord = findViewById(R.id.btnSaveNewWord);


        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        yourWordList = new ArrayList<>();
        yourWordAdapter = new YourWordAdapter(yourWordList);
        rvYourWords.setLayoutManager(new LinearLayoutManager(this));
        rvYourWords.setAdapter(yourWordAdapter);

        loadYourWords(); // Tải các từ đã lưu khi Activity được tạo

        // Xử lý sự kiện click cho nút "Lưu từ mới"
        btnSaveNewWord.setOnClickListener(v -> {
            String englishWord = edtEnglishWord.getText().toString().trim();
            String vietnameseTranslation = edtVietnameseTranslation.getText().toString().trim();

            if (englishWord.isEmpty() || vietnameseTranslation.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ từ tiếng Anh và bản dịch tiếng Việt", Toast.LENGTH_SHORT).show();
                return;
            }

            YourWordModel newWord = new YourWordModel(englishWord, vietnameseTranslation);

            executorService.execute(() -> {
                database.yourWordDao().insert(newWord);
                runOnUiThread(() -> {
                    Toast.makeText(YourWordActivity.this, "Đã lưu từ mới: " + englishWord, Toast.LENGTH_SHORT).show();
                    edtEnglishWord.setText(""); // Xóa nội dung sau khi lưu
                    edtVietnameseTranslation.setText(""); // Xóa nội dung sau khi lưu
                    loadYourWords(); // Tải lại danh sách để hiển thị từ vừa thêm
                });
            });
        });

        // Xử lý sự kiện click cho nút "Xóa tất cả"
        btnClearYourWords.setOnClickListener(v -> {
            executorService.execute(() -> {
                database.yourWordDao().clearYourWords();
                runOnUiThread(() -> {
                    yourWordList.clear();
                    yourWordAdapter.notifyDataSetChanged();
                    Toast.makeText(YourWordActivity.this, "Đã xóa tất cả từ đã lưu", Toast.LENGTH_SHORT).show();
                });
            });
        });
    }

    private void loadYourWords() {
        executorService.execute(() -> {
            List<YourWordModel> words = database.yourWordDao().getAllYourWords();
            runOnUiThread(() -> {
                yourWordList.clear();
                yourWordList.addAll(words);
                yourWordAdapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}