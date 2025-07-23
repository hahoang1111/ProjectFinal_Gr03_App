package com.example.projectfinal_gr03_app.dictionary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal_gr03_app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.WordViewHolder> {

    private final List<Vocabulary_Dic> vocabularyList;
    private final List<Vocabulary_Dic> filteredList;
    private final HashSet<String> favoriteWords = new HashSet<>();
    private final Map<String, Integer> originalPositionMap = new HashMap<>();

    public VocabularyAdapter(List<Vocabulary_Dic> wordList) {
        this.vocabularyList = wordList;
        this.filteredList = new ArrayList<>(wordList);

        // Lưu vị trí gốc của từng từ
        for (int i = 0; i < wordList.size(); i++) {
            originalPositionMap.put(wordList.get(i).getEnglishWord(), i);
        }
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vocabulary, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Vocabulary_Dic vocab = filteredList.get(position);
        Context context = holder.itemView.getContext();

        holder.tvEnglishWord.setText(nonNull(vocab.getEnglishWord()));
        holder.tvPhonetic.setText("UK: " + nonNull(vocab.getPhoneticUK()));
        holder.tvPartOfSpeech.setText("Loại từ: " + nonNull(vocab.getPartOfSpeech()));

        String firstMeaning = (vocab.getVietnameseMeanings() != null && !vocab.getVietnameseMeanings().isEmpty())
                ? vocab.getVietnameseMeanings().get(0)
                : "N/A";

        String firstExample = (vocab.getExamples() != null && !vocab.getExamples().isEmpty())
                ? vocab.getExamples().get(0)
                : "N/A";

        holder.tvVietnameseMeaning.setText("Nghĩa: " + firstMeaning);
        holder.tvExample.setText("Ví dụ: " + firstExample);

        boolean isFavorite = favoriteWords.contains(vocab.getEnglishWord());
        holder.btnBookmark.setImageResource(
                isFavorite ? R.drawable.outline_bookmark_remove_24 : R.drawable.outline_bookmarks_24
        );

        holder.btnBookmark.setOnClickListener(v -> {
            String wordKey = vocab.getEnglishWord();
            if (favoriteWords.contains(wordKey)) {
                favoriteWords.remove(wordKey);

                // Xóa khỏi filteredList
                filteredList.remove(vocab);

                // Tìm vị trí gốc để thêm lại
                int originalIndex = originalPositionMap.getOrDefault(wordKey, filteredList.size());
                if (originalIndex >= filteredList.size()) {
                    filteredList.add(vocab);
                } else {
                    filteredList.add(originalIndex, vocab);
                }

            } else {
                favoriteWords.add(wordKey);
                filteredList.remove(vocab);
                filteredList.add(0, vocab); // đưa lên đầu
            }
            notifyDataSetChanged();
        });

        holder.itemView.setOnClickListener(v -> {
            Vocabulary_Dic safeVocab = ensureNonNullLists(vocab);
            Intent intent = new Intent(context, VocabDetailActivity.class);
            intent.putExtra("vocab", safeVocab);
            context.startActivity(intent);
        });
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
                if (vocab.getEnglishWord() != null &&
                        vocab.getEnglishWord().toLowerCase().contains(searchText)) {
                    filteredList.add(vocab);
                }
            }
        }

        // Di chuyển từ yêu thích lên đầu
        filteredList.sort((a, b) -> {
            boolean aFav = favoriteWords.contains(a.getEnglishWord());
            boolean bFav = favoriteWords.contains(b.getEnglishWord());
            return Boolean.compare(!aFav, !bFav);
        });

        notifyDataSetChanged();
    }

    private Vocabulary_Dic ensureNonNullLists(Vocabulary_Dic vocab) {
        if (vocab.getVietnameseMeanings() == null) vocab.setVietnameseMeanings(new ArrayList<>());
        if (vocab.getExamples() == null) vocab.setExamples(new ArrayList<>());
        if (vocab.getSynonyms() == null) vocab.setSynonyms(new ArrayList<>());
        if (vocab.getAntonyms() == null) vocab.setAntonyms(new ArrayList<>());
        return vocab;
    }

    private String nonNull(String input) {
        return input != null ? input : "";
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnglishWord, tvPhonetic, tvPartOfSpeech, tvVietnameseMeaning, tvExample;
        ImageView btnBookmark;

        public WordViewHolder(View itemView) {
            super(itemView);
            tvEnglishWord = itemView.findViewById(R.id.tvEnglishWord);
            tvPhonetic = itemView.findViewById(R.id.tvPhonetic);
            tvPartOfSpeech = itemView.findViewById(R.id.tvPartOfSpeech);
            tvVietnameseMeaning = itemView.findViewById(R.id.tvVietnameseMeaning);
            tvExample = itemView.findViewById(R.id.tvExample);
            btnBookmark = itemView.findViewById(R.id.btnBookmark);
        }
    }
}
