package com.example.projectfinal_gr03_app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class DictionaryA_V extends AppCompatActivity {

    private RecyclerView rvVocabulary;
    private EditText etSearch;
    private VocabularyAdapter adapter;
    private List<String> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionarya_v);

        rvVocabulary = findViewById(R.id.rvVocabulary);
        etSearch = findViewById(R.id.etSearch);

        // Danh sách từ vựng mẫu
        wordList = new ArrayList<>();
        wordList.add("apple - quả táo");
        wordList.add("book - quyển sách");
        wordList.add("computer - máy tính");
        wordList.add("dog - con chó");
        wordList.add("education - giáo dục");
        wordList.add("future - tương lai");
        wordList.add("happiness - hạnh phúc");
        wordList.add("inspiration - cảm hứng");
        wordList.add("knowledge - kiến thức");

        adapter = new VocabularyAdapter(wordList);
        rvVocabulary.setLayoutManager(new LinearLayoutManager(this));
        rvVocabulary.setAdapter(adapter);

        // Lọc từ khi tìm kiếm
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}
