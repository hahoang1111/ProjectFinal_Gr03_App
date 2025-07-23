package com.example.projectfinal_gr03_app.dictionary;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;

import com.example.projectfinal_gr03_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DictionaryA_V extends AppCompatActivity {

    private RecyclerView rvVocabulary;
    private EditText etSearch;
    private VocabularyAdapter adapter;
    private List<Vocabulary_Dic> wordList;
    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionarya_v);

        // Khởi tạo view
        rvVocabulary = findViewById(R.id.rvVocabulary);
        etSearch = findViewById(R.id.etSearch);

        wordList = loadVocabularyFromJson();

        // Khởi tạo danh sách từ vựng mẫu

        wordList.add(new Vocabulary_Dic("Hello", "/həˈloʊ/", "Noun", "Xin chào", "Hello, how are you?"));
        wordList.add(new Vocabulary_Dic("Book", "/bʊk/", "Noun", "Sách", "I read a book."));
        wordList.add(new Vocabulary_Dic("Run", "/rʌn/", "Verb", "Chạy", "She runs every morning."));

        // Khởi tạo và thiết lập Adapter
        adapter = new VocabularyAdapter(wordList);
        rvVocabulary.setLayoutManager(new LinearLayoutManager(this));
        rvVocabulary.setAdapter(adapter);

        // Filter words on search
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filter(s != null ? s.toString() : "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private List<Vocabulary_Dic> loadVocabularyFromJson() {
        List<Vocabulary_Dic> vocabularyList = new ArrayList<>();
        try {
            // Mở file JSON từ res/raw
            InputStream inputStream = getResources().openRawResource(R.raw.vocabulary);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();
            inputStream.close();

            // Parse JSON
            JSONArray jsonArray = new JSONArray(jsonString.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                vocabularyList.add(new Vocabulary_Dic(
                        obj.optString("englishWord", ""),
                        obj.optString("phonetic", "N/A"),
                        obj.optString("partOfSpeech", "Unknown"),
                        obj.optString("vietnameseMeaning", ""),
                        obj.optString("example", "N/A")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vocabularyList;
    }

}