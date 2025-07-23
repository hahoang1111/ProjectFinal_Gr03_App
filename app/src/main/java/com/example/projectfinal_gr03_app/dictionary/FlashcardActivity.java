package com.example.projectfinal_gr03_app.dictionary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfinal_gr03_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FlashcardActivity extends AppCompatActivity {

    private TextView tvEnglishWord, tvMeaning;
    private Button btnShowMeaning, btnNext, btnPrevious;

    private List<Vocabulary_Dic> wordList;
    private int currentIndex = 0;
    private boolean isMeaningShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard); // Đảm bảo bạn tạo file XML này

        // Ánh xạ view
        tvEnglishWord = findViewById(R.id.tvEnglishWord);
        tvMeaning = findViewById(R.id.tvMeaning);
        btnShowMeaning = findViewById(R.id.btnShowMeaning);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);

        wordList = loadVocabularyFromJson();
        if (!wordList.isEmpty()) {
            displayCurrentWord();
        }

        btnShowMeaning.setOnClickListener(v -> toggleMeaning());

        btnNext.setOnClickListener(v -> {
            if (currentIndex < wordList.size() - 1) {
                currentIndex++;
                displayCurrentWord();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                displayCurrentWord();
            }
        });
    }

    private void displayCurrentWord() {
        isMeaningShown = false;
        Vocabulary_Dic vocab = wordList.get(currentIndex);
        tvEnglishWord.setText(vocab.getEnglishWord());
        tvMeaning.setVisibility(View.GONE);
        btnShowMeaning.setText("Hiển thị nghĩa");
    }

    private void toggleMeaning() {
        if (!isMeaningShown) {
            Vocabulary_Dic vocab = wordList.get(currentIndex);
            String meaning = (vocab.getVietnameseMeanings() != null && !vocab.getVietnameseMeanings().isEmpty())
                    ? vocab.getVietnameseMeanings().get(0)
                    : "Chưa có nghĩa";

            tvMeaning.setText(meaning);
            tvMeaning.setVisibility(View.VISIBLE);
            btnShowMeaning.setText("Ẩn nghĩa");
            isMeaningShown = true;
        } else {
            tvMeaning.setVisibility(View.GONE);
            btnShowMeaning.setText("Hiển thị nghĩa");
            isMeaningShown = false;
        }
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
