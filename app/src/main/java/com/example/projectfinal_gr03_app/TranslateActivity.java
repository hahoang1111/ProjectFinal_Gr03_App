package com.example.projectfinal_gr03_app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.nl.translate.Translation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TranslateActivity extends AppCompatActivity {

    // Các thành phần giao diện và biến cần thiết
    private Spinner spinnerSource, spinnerTarget;
    private EditText edtInput;
    private Button btnTranslate, btnSwap, btnSpeak, btnCopy, btnClearHistory;
    private TextView txtResult;
    private TextToSpeech tts;
    private RecyclerView recyclerHistory;
    private HistoryAdapter historyAdapter;
    private ArrayList<HistoryModel> historyList;
    private AppDatabase database;
    private ExecutorService executorService;

    // Danh sách ngôn ngữ hiển thị cho Spinner
    private String[] languages = {"Việt", "Anh", "Nhật", "Hàn"};

    // Phương thức chuyển tên ngôn ngữ sang mã ngôn ngữ chuẩn của ML Kit
    private String getLangCode(String lang) {
        switch (lang) {
            case "Việt": return TranslateLanguage.VIETNAMESE;
            case "Anh": return TranslateLanguage.ENGLISH;
            case "Nhật": return TranslateLanguage.JAPANESE;
            case "Hàn": return TranslateLanguage.KOREAN;
            default: return TranslateLanguage.ENGLISH;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate);

        // Ánh xạ giao diện
        spinnerSource = findViewById(R.id.spinnerSource);
        spinnerTarget = findViewById(R.id.spinnerTarget);
        edtInput = findViewById(R.id.edtInput);
        btnTranslate = findViewById(R.id.btnTranslate);
        btnSwap = findViewById(R.id.btnSwap);
        btnSpeak = findViewById(R.id.btnSpeak);
        btnCopy = findViewById(R.id.btnCopy);
        btnClearHistory = findViewById(R.id.btnClearHistory);
        txtResult = findViewById(R.id.txtResult);
        recyclerHistory = findViewById(R.id.recyclerHistory);

        // Khởi tạo database và luồng thực thi riêng
        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        // Cấu hình Spinner cho ngôn ngữ
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSource.setAdapter(adapter);
        spinnerTarget.setAdapter(adapter);

        // Cấu hình RecyclerView để hiển thị lịch sử dịch
        historyList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(historyList);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerHistory.setAdapter(historyAdapter);

        // Tải dữ liệu lịch sử dịch từ database (Room)
        executorService.execute(() -> {
            List<HistoryModel> history = database.historyDao().getAllHistory();
            runOnUiThread(() -> {
                historyList.clear();
                historyList.addAll(history);
                historyAdapter.notifyDataSetChanged();
            });
        });

        // Khởi tạo chức năng Text-to-Speech
        tts = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(Locale.US); // Đặt ngôn ngữ đọc là tiếng Anh
            }
        });

        // Xử lý nút "Dịch"
        btnTranslate.setOnClickListener(v -> {
            String inputText = edtInput.getText().toString().trim();
            if (inputText.isEmpty()) {
                txtResult.setText("Vui lòng nhập văn bản cần dịch!");
                return;
            }

            String sourceLang = getLangCode(spinnerSource.getSelectedItem().toString());
            String targetLang = getLangCode(spinnerTarget.getSelectedItem().toString());

            if (sourceLang.equals(targetLang)) {
                txtResult.setText("Ngôn ngữ nguồn và đích phải khác nhau");
                return;
            }

            // Tạo đối tượng Translator với ngôn ngữ nguồn và đích
            TranslatorOptions options = new TranslatorOptions.Builder()
                    .setSourceLanguage(sourceLang)
                    .setTargetLanguage(targetLang)
                    .build();

            Translator translator = Translation.getClient(options);
            txtResult.setText("Đang dịch...");

            // Tải mô hình dịch nếu cần (chỉ khi chưa tải)
            translator.downloadModelIfNeeded(new DownloadConditions.Builder().requireWifi().build())
                    .addOnSuccessListener(unused -> {
                        translator.translate(inputText)
                                .addOnSuccessListener(translatedText -> {
                                    // Hiển thị kết quả dịch
                                    txtResult.setText(translatedText);

                                    // Lưu vào lịch sử (Room DB)
                                    HistoryModel history = new HistoryModel(inputText, translatedText);
                                    executorService.execute(() -> {
                                        database.historyDao().insert(history);
                                        List<HistoryModel> updatedHistory = database.historyDao().getAllHistory();
                                        runOnUiThread(() -> {
                                            historyList.clear();
                                            historyList.addAll(updatedHistory);
                                            historyAdapter.notifyDataSetChanged();
                                            recyclerHistory.scrollToPosition(0); // Cuộn lên đầu
                                        });
                                    });
                                })
                                .addOnFailureListener(e -> txtResult.setText("Lỗi dịch: " + e.getMessage()));
                    })
                    .addOnFailureListener(e -> txtResult.setText("Không thể tải model: " + e.getMessage()));
        });

        // Xử lý nút "Hoán đổi ngôn ngữ"
        btnSwap.setOnClickListener(v -> {
            int src = spinnerSource.getSelectedItemPosition();
            int tgt = spinnerTarget.getSelectedItemPosition();
            spinnerSource.setSelection(tgt);
            spinnerTarget.setSelection(src);
        });

        // Xử lý nút "Phát âm"
        btnSpeak.setOnClickListener(v -> {
            String text = txtResult.getText().toString();
            if (!text.isEmpty() && !text.equals("Đang dịch...")) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        // Xử lý nút "Sao chép"
        btnCopy.setOnClickListener(v -> {
            String translatedText = txtResult.getText().toString();
            if (!translatedText.isEmpty() && !translatedText.equals("Đang dịch...")) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Translated Text", translatedText);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Đã sao chép", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút "Xóa lịch sử"
        btnClearHistory.setOnClickListener(v -> {
            executorService.execute(() -> {
                database.historyDao().clearHistory(); // Xóa toàn bộ
                runOnUiThread(() -> {
                    historyList.clear();
                    historyAdapter.notifyDataSetChanged();
                });
            });
        });
    }

    // Giải phóng tài nguyên khi Activity bị hủy
    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        executorService.shutdown(); // Dừng Executor để tránh rò rỉ bộ nhớ
        super.onDestroy();
    }
}
