package com.example.projectfinal_gr03_app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "translation_history")
public class HistoryModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String originalText;
    private String translatedText;

    public HistoryModel(String originalText, String translatedText) {
        this.originalText = originalText;
        this.translatedText = translatedText;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getOriginalText() { return originalText; }
    public void setOriginalText(String originalText) { this.originalText = originalText; }
    public String getTranslatedText() { return translatedText; }
    public void setTranslatedText(String translatedText) { this.translatedText = translatedText; }
}