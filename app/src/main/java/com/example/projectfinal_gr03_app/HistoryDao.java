package com.example.projectfinal_gr03_app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insert(HistoryModel history);

    @Query("SELECT * FROM translation_history ORDER BY id DESC")
    List<HistoryModel> getAllHistory();

    @Query("DELETE FROM translation_history")
    void clearHistory();
}