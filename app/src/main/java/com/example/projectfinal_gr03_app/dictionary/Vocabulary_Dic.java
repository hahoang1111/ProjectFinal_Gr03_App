package com.example.projectfinal_gr03_app.dictionary;

import java.io.Serializable;
import java.util.List;

public class Vocabulary_Dic implements Serializable {

    private String englishWord;
    private String phoneticUK;
    private String phoneticUS;
    private String partOfSpeech;
    private List<String> vietnameseMeanings; // Nhiều nghĩa
    private List<String> examples;           // Nhiều ví dụ
    private List<String> synonyms;           // Từ đồng nghĩa
    private List<String> antonyms;           // Từ trái nghĩa
    private String grammarNote;              // Ghi chú ngữ pháp (nếu có)

    // Constructors
    public Vocabulary_Dic(String englishWord,
                          String phoneticUK,
                          String phoneticUS,
                          String partOfSpeech,
                          List<String> vietnameseMeanings,
                          List<String> examples,
                          List<String> synonyms,
                          List<String> antonyms,
                          String grammarNote) {
        this.englishWord = englishWord;
        this.phoneticUK = phoneticUK;
        this.phoneticUS = phoneticUS;
        this.partOfSpeech = partOfSpeech;
        this.vietnameseMeanings = vietnameseMeanings;
        this.examples = examples;
        this.synonyms = synonyms;
        this.antonyms = antonyms;
        this.grammarNote = grammarNote;
    }

    // Getters and setters
    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getPhoneticUK() {
        return phoneticUK;
    }

    public void setPhoneticUK(String phoneticUK) {
        this.phoneticUK = phoneticUK;
    }

    public String getPhoneticUS() {
        return phoneticUS;
    }

    public void setPhoneticUS(String phoneticUS) {
        this.phoneticUS = phoneticUS;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<String> getVietnameseMeanings() {
        return vietnameseMeanings;
    }

    public void setVietnameseMeanings(List<String> vietnameseMeanings) {
        this.vietnameseMeanings = vietnameseMeanings;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }

    public String getGrammarNote() {
        return grammarNote;
    }

    public void setGrammarNote(String grammarNote) {
        this.grammarNote = grammarNote;
    }
}
