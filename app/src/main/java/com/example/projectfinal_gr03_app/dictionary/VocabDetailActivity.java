package com.example.projectfinal_gr03_app.dictionary;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfinal_gr03_app.R;

import java.util.List;

public class VocabDetailActivity extends AppCompatActivity {

    private TextView tvWord, tvPhoneticUK, tvPhoneticUS, tvPart, tvMeaning1, tvMeaning2,
            tvExample1, tvExample2, tvSynonyms, tvAntonyms, tvGrammarNote;
    private ImageView btnPlayUK, btnPlayUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_detail); // Đảm bảo file XML này có đủ các ID cần thiết

        // Ánh xạ View
        tvWord = findViewById(R.id.tvWord);
        tvPhoneticUK = findViewById(R.id.tvPhoneticUK);
        tvPhoneticUS = findViewById(R.id.tvPhoneticUS);
        tvPart = findViewById(R.id.tvPart);
        tvMeaning1 = findViewById(R.id.tvMeaning);
        tvMeaning2 = findViewById(R.id.tvMeaning2);
        tvExample1 = findViewById(R.id.tvExample1);
        tvExample2 = findViewById(R.id.tvExample2);
        tvSynonyms = findViewById(R.id.tvSynonyms);
        tvAntonyms = findViewById(R.id.tvAntonyms);
        tvGrammarNote = findViewById(R.id.tvGrammarNote);
        btnPlayUK = findViewById(R.id.btnPlayUK);
        btnPlayUS = findViewById(R.id.btnPlayUS);

        // Nhận từ vựng từ Intent
        Vocabulary_Dic word = (Vocabulary_Dic) getIntent().getSerializableExtra("vocab");

        if (word == null) {
            Toast.makeText(this, "Không có dữ liệu từ vựng!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Gán dữ liệu
        tvWord.setText(nonNull(word.getEnglishWord()));
        tvPhoneticUK.setText("/" + nonNull(word.getPhoneticUK()) + "/");
        tvPhoneticUS.setText("/" + nonNull(word.getPhoneticUS()) + "/");
        tvPart.setText("Loại từ: " + nonNull(word.getPartOfSpeech()));

        List<String> meanings = word.getVietnameseMeanings();
        tvMeaning1.setText(meanings != null && meanings.size() > 0 ? "1. " + meanings.get(0) : "Không có nghĩa");
        tvMeaning2.setText(meanings != null && meanings.size() > 1 ? "2. " + meanings.get(1) : "");

        List<String> examples = word.getExamples();
        tvExample1.setText(examples != null && examples.size() > 0 ? "• " + examples.get(0) : "Không có ví dụ");
        tvExample2.setText(examples != null && examples.size() > 1 ? "• " + examples.get(1) : "");

        List<String> synonyms = word.getSynonyms();
        tvSynonyms.setText(synonyms != null && !synonyms.isEmpty()
                ? TextUtils.join(", ", synonyms)
                : "Không có");

        List<String> antonyms = word.getAntonyms();
        tvAntonyms.setText(antonyms != null && !antonyms.isEmpty()
                ? TextUtils.join(", ", antonyms)
                : "Không có");

        String grammarNote = word.getGrammarNote();
        tvGrammarNote.setText(!TextUtils.isEmpty(grammarNote) ? grammarNote : "Không có");

        // Phát âm (sử dụng file mẫu từ raw - thay thế nếu có file âm thanh riêng)
        btnPlayUK.setOnClickListener(v -> playSoundFromRaw(R.raw.vocabulary));
        btnPlayUS.setOnClickListener(v -> playSoundFromRaw(R.raw.vocabulary));
    }

    private void playSoundFromRaw(int resId) {
        try {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, resId);
            mediaPlayer.start();
        } catch (Exception e) {
            Toast.makeText(this, "Không phát được âm thanh", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String nonNull(String input) {
        return input != null ? input : "";
    }
}
