package com.example.projectfinal_gr03_app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Entity đại diện cho bảng "translation_history" trong Room Database
@Entity(tableName = "translation_history")
public class HistoryModel {
    @PrimaryKey(autoGenerate = true) // Khóa chính tự tăng
    private int id;

    private String originalText;     // Văn bản gốc
    private String translatedText;   // Văn bản đã dịch

    // Constructor khởi tạo với văn bản gốc và văn bản dịch
    public HistoryModel(String originalText, String translatedText) {
        this.originalText = originalText;
        this.translatedText = translatedText;
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOriginalText() { return originalText; }
    public void setOriginalText(String originalText) { this.originalText = originalText; }

    public String getTranslatedText() { return translatedText; }
    public void setTranslatedText(String translatedText) { this.translatedText = translatedText; }
}
