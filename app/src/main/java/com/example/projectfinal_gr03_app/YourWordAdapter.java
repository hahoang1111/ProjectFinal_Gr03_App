package com.example.projectfinal_gr03_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class YourWordAdapter extends RecyclerView.Adapter<YourWordAdapter.YourWordViewHolder>{
    // Danh sách sẽ chứa các đối tượng YourWordModel
    private List<YourWordModel> yourWordList;

    // Constructor nhận một List<YourWordModel>
    public YourWordAdapter(List<YourWordModel> yourWordList) {
        this.yourWordList = yourWordList;
    }

    @NonNull
    @Override
    public YourWordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng layout item_your_word.xml để hiển thị mỗi mục
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_your_word, parent, false);
        return new YourWordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YourWordViewHolder holder, int position) {
        // Lấy đối tượng YourWordModel tại vị trí hiện tại
        YourWordModel item = yourWordList.get(position);

        // Đặt văn bản cho các TextView
        // Sử dụng getWord() và getTranslation() từ YourWordModel
        holder.txtWord.setText("Word: " + item.getWord());
        holder.txtTranslation.setText("Translation: " + item.getTranslation());
    }

    @Override
    public int getItemCount() {
        return yourWordList.size();
    }

    // ViewHolder class để giữ các tham chiếu đến các view trong mỗi mục danh sách
    static class YourWordViewHolder extends RecyclerView.ViewHolder {
        TextView txtWord, txtTranslation;

        public YourWordViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWord = itemView.findViewById(R.id.txtYourWord); // ID của TextView cho từ
            txtTranslation = itemView.findViewById(R.id.txtYourWordTranslation); // ID của TextView cho bản dịch
        }
    }
}

