package com.example.projectfinal_gr03_app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "your_words")
public class YourWordModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String word;
    private String translation;

    public YourWordModel(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
