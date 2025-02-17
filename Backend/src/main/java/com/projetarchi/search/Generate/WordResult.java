package com.projetarchi.search.Generate;

public class WordResult {
    private String word;
    private int occurrences;
    
    public WordResult(String word, int occurrences) {
        this.word = word;
        this.occurrences = occurrences;
    }
    
    // Getters et Setters
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    @Override
    public String toString() {
        return "WordResult{" +
                "word='" + word + '\'' +
                ", occurrences=" + occurrences +
                '}';
    }
}