package com.example.projectfinal_gr03_app.dictionary;

public class Vocabulary_Dic {
    private String englishWord;
    private String phonetic;
    private String partOfSpeech;
    private String vietnameseMeaning;
    private String example;

    public Vocabulary_Dic(String englishWord, String phonetic, String partOfSpeech, String vietnameseMeaning, String example) {
        this.englishWord = englishWord;
        this.phonetic = phonetic;
        this.partOfSpeech = partOfSpeech;
        this.vietnameseMeaning = vietnameseMeaning;
        this.example = example;
    }

    public String getEnglishWord() { return englishWord; }
    public String getPhonetic() { return phonetic; }
    public String getPartOfSpeech() { return partOfSpeech; }
    public String getVietnameseMeaning() { return vietnameseMeaning; }
    public String getExample() { return example; }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public void setVietnameseMeaning(String vietnameseMeaning) {
        this.vietnameseMeaning = vietnameseMeaning;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
