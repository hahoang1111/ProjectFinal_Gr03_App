package com.example.projectfinal_gr03_app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import java.util.List;
@Dao
public interface YourWordDao {
    @Insert
    void insert(YourWordModel yourWord);

    @Query("SELECT * FROM your_words ORDER BY id DESC")
    List<YourWordModel> getAllYourWords();

    @Query("DELETE FROM your_words WHERE word = :word")
    void deleteWord(String word);

    @Query("DELETE FROM your_words")
    void clearYourWords();
}
