package com.example.projectfinal_gr03_app.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfinal_gr03_app.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DictionaryA_V extends AppCompatActivity {

    private RecyclerView rvVocabulary;
    private EditText etSearch;
    private VocabularyAdapter adapter;
    private List<Vocabulary_Dic> wordList;
    private final Handler searchHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionarya_v);

        rvVocabulary = findViewById(R.id.rvVocabulary);
        etSearch = findViewById(R.id.etSearch);

        // Load danh sách từ vựng từ JSON
        wordList = loadVocabularyFromJson();

        // Thiết lập Adapter và RecyclerView
        adapter = new VocabularyAdapter(wordList);
        rvVocabulary.setLayoutManager(new LinearLayoutManager(this));
        rvVocabulary.setAdapter(adapter);

        // Xử lý tìm kiếm
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filter(s != null ? s.toString() : "");
                }
            }
        });

        // Bắt sự kiện nút Flashcard
        MaterialButton btnFlashcard = findViewById(R.id.btnFlashcard);
        btnFlashcard.setOnClickListener(v -> {
            Intent intent = new Intent(DictionaryA_V.this, FlashcardActivity.class);
            intent.putExtra("vocab_list", new ArrayList<>(wordList));
            startActivity(intent);
        });
    }

    private List<Vocabulary_Dic> loadVocabularyFromJson() {
        List<Vocabulary_Dic> vocabularyList = new ArrayList<>();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.vocabulary);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();
            inputStream.close();

            JSONArray jsonArray = new JSONArray(jsonString.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String englishWord = obj.optString("englishWord", "");
                String phoneticUK = obj.optString("phoneticUK", "");
                String phoneticUS = obj.optString("phoneticUS", "");
                String partOfSpeech = obj.optString("partOfSpeech", "");
                String grammarNote = obj.optString("grammarNote", "");

                List<String> meanings = new ArrayList<>();
                JSONArray meaningsArray = obj.optJSONArray("vietnameseMeanings");
                if (meaningsArray != null) {
                    for (int j = 0; j < meaningsArray.length(); j++) {
                        meanings.add(meaningsArray.optString(j));
                    }
                }

                List<String> examples = new ArrayList<>();
                JSONArray examplesArray = obj.optJSONArray("examples");
                if (examplesArray != null) {
                    for (int j = 0; j < examplesArray.length(); j++) {
                        examples.add(examplesArray.optString(j));
                    }
                }

                List<String> synonyms = new ArrayList<>();
                JSONArray synonymsArray = obj.optJSONArray("synonyms");
                if (synonymsArray != null) {
                    for (int j = 0; j < synonymsArray.length(); j++) {
                        synonyms.add(synonymsArray.optString(j));
                    }
                }

                List<String> antonyms = new ArrayList<>();
                JSONArray antonymsArray = obj.optJSONArray("antonyms");
                if (antonymsArray != null) {
                    for (int j = 0; j < antonymsArray.length(); j++) {
                        antonyms.add(antonymsArray.optString(j));
                    }
                }

                Vocabulary_Dic vocab = new Vocabulary_Dic(
                        englishWord,
                        phoneticUK,
                        phoneticUS,
                        partOfSpeech,
                        meanings,
                        examples,
                        synonyms,
                        antonyms,
                        grammarNote
                );

                vocabularyList.add(vocab);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vocabularyList;
    }
}
