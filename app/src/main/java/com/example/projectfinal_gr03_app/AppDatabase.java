package com.example.projectfinal_gr03_app;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

// Định nghĩa Room Database chứa bảng HistoryModel
@Database(entities = {HistoryModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Truy cập DAO để thao tác với bảng
    public abstract HistoryDao historyDao();

    // Singleton Instance
    private static volatile AppDatabase INSTANCE;

    // Lấy instance duy nhất của Room Database
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Tạo database với tên "translation_db"
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "translation_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
