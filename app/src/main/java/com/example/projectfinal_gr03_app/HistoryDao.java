package com.example.projectfinal_gr03_app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// DAO quản lý thao tác với bảng translation_history
@Dao
public interface HistoryDao {

    @Insert
    void insert(HistoryModel history); // Thêm bản ghi mới

    @Query("SELECT * FROM translation_history ORDER BY id DESC")
    List<HistoryModel> getAllHistory(); // Lấy toàn bộ lịch sử (mới nhất trước)

    @Query("DELETE FROM translation_history")
    void clearHistory(); // Xóa toàn bộ lịch sử
}
