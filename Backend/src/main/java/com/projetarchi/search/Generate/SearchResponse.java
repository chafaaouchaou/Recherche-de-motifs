package com.projetarchi.search.Generate;

import java.util.List;

public class SearchResponse {
    private int totalWords;
    private long executionTime;
    private List<WordResult> wordResults;
    
    public SearchResponse() {
    }
    
    public SearchResponse(int totalWords, long executionTime, List<WordResult> wordResults) {
        this.totalWords = totalWords;
        this.executionTime = executionTime;
        this.wordResults = wordResults;
    }
    
    // Getters et Setters
    public int getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public List<WordResult> getWordResults() {
        return wordResults;
    }

    public void setWordResults(List<WordResult> wordResults) {
        this.wordResults = wordResults;
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
                "totalWords=" + totalWords +
                ", executionTime=" + executionTime +
                ", wordResults=" + wordResults +
                '}';
    }
}