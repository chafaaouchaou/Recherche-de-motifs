package com.projetarchi.search.Generate;

public class AlgorithmResult {
    private int algorithmNumber;
    private String algorithmName;
    private SearchResponse searchResponse;
    
    public AlgorithmResult(int algorithmNumber, String algorithmName, SearchResponse searchResponse) {
        this.algorithmNumber = algorithmNumber;
        this.algorithmName = algorithmName;
        this.searchResponse = searchResponse;
    }
    
    // Getters et setters
    public int getAlgorithmNumber() {
        return algorithmNumber;
    }
    
    public void setAlgorithmNumber(int algorithmNumber) {
        this.algorithmNumber = algorithmNumber;
    }
    
    public String getAlgorithmName() {
        return algorithmName;
    }
    
    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
    
    public SearchResponse getSearchResponse() {
        return searchResponse;
    }
    
    public void setSearchResponse(SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
    }

    @Override
    public String toString() {
        return "AlgorithmResult{" +
                "algorithmNumber=" + algorithmNumber +
                ", algorithmName='" + algorithmName + '\'' +
                ", searchResponse=" + searchResponse +
                '}';
    }
}