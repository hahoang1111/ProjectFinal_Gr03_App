package com.example.projectfinal_gr03_app.dictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal_gr03_app.R;

import java.util.ArrayList;
import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.WordViewHolder> {

    private List<Vocabulary_Dic> vocabularyList;
    private List<Vocabulary_Dic> filteredList;

    public VocabularyAdapter(List<Vocabulary_Dic> wordList) {
        this.vocabularyList = wordList;
        this.filteredList = new ArrayList<>(wordList);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vocabulary, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Vocabulary_Dic vocab =filteredList.get(position);
        holder.tvEnglishWord.setText(vocab.getEnglishWord());
        holder.tvPhonetic.setText(vocab.getPhonetic());
        holder.tvPartOfSpeech.setText("Loại từ: " + vocab.getPartOfSpeech());
        holder.tvVietnameseMeaning.setText("Nghĩa: " + vocab.getVietnameseMeaning());
        holder.tvExample.setText("Ví dụ: " + vocab.getExample());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    public void filter(String text) {
        filteredList.clear();

        if (text == null || text.trim().isEmpty()) {
            filteredList.addAll(vocabularyList);
        } else {
            String searchText = text.toLowerCase().trim();
            for (Vocabulary_Dic vocab : vocabularyList) {
                if (vocab != null && vocab.getEnglishWord() != null &&
                        vocab.getEnglishWord().toLowerCase().contains(searchText)) {
                    filteredList.add(vocab);
                }
            }
        }

        notifyDataSetChanged();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnglishWord, tvPhonetic, tvPartOfSpeech, tvVietnameseMeaning, tvExample;
        public WordViewHolder(View itemView) {
            super(itemView);
            tvEnglishWord = itemView.findViewById(R.id.tvEnglishWord);
            tvPhonetic = itemView.findViewById(R.id.tvPhonetic);
            tvPartOfSpeech = itemView.findViewById(R.id.tvPartOfSpeech);
            tvVietnameseMeaning = itemView.findViewById(R.id.tvVietnameseMeaning);
            tvExample = itemView.findViewById(R.id.tvExample);
        }
    }
}
