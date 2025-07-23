package com.example.projectfinal_gr03_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Adapter cho RecyclerView hiển thị danh sách lịch sử dịch
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryModel> historyList;

    public HistoryAdapter(List<HistoryModel> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        // Gán dữ liệu gốc và bản dịch vào TextView
        HistoryModel item = historyList.get(position);
        holder.txtOriginal.setText("Gốc: " + item.getOriginalText());
        holder.txtTranslated.setText("Dịch: " + item.getTranslatedText());
    }

    @Override
    public int getItemCount() {
        return historyList.size(); // Trả về số item trong danh sách
    }

    // ViewHolder chứa các thành phần của từng item
    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtOriginal, txtTranslated;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOriginal = itemView.findViewById(R.id.txtOriginal);
            txtTranslated = itemView.findViewById(R.id.txtTranslated);
        }
    }
}
