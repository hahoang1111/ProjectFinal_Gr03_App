package com.example.projectfinal_gr03_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Adapter dùng để hiển thị danh sách lịch sử dịch trong RecyclerView
public class TranslationHistoryAdapter extends RecyclerView.Adapter<TranslationHistoryAdapter.HistoryViewHolder> {

    // Danh sách các item lịch sử dịch
    private List<HistoryModel> historyList;

    // Constructor nhận danh sách lịch sử
    public TranslationHistoryAdapter(List<HistoryModel> historyList) {
        this.historyList = historyList;
    }

    // Tạo ViewHolder mới khi cần hiển thị một item mới trong danh sách
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho mỗi item trong danh sách (item_history.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    // Gán dữ liệu từ model vào ViewHolder tương ứng
    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        // Lấy item tại vị trí hiện tại
        HistoryModel item = historyList.get(position);

        // Gán dữ liệu vào TextView
        holder.txtOriginal.setText("Gốc: " + item.getOriginalText());
        holder.txtTranslated.setText("Dịch: " + item.getTranslatedText());
    }

    // Trả về số lượng item trong danh sách
    @Override
    public int getItemCount() {
        return historyList.size();
    }

    // ViewHolder đại diện cho mỗi item trong RecyclerView
    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các TextView hiển thị nội dung dịch
        TextView txtOriginal, txtTranslated;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần trong item_history.xml
            txtOriginal = itemView.findViewById(R.id.txtOriginal);
            txtTranslated = itemView.findViewById(R.id.txtTranslated);
        }
    }
}
