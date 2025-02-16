package com.projetarchi.search.Generate;

import java.util.List;

public class MultiAlgorithmResponse {
    private String generatedText;
    private List<AlgorithmResult> results;
    
    public MultiAlgorithmResponse(String generatedText, List<AlgorithmResult> results) {
        this.generatedText = generatedText;
        this.results = results;
    }
    
    // Getters et setters
    public String getGeneratedText() {
        return generatedText;
    }
    
    public void setGeneratedText(String generatedText) {
        this.generatedText = generatedText;
    }
    
    public List<AlgorithmResult> getResults() {
        return results;
    }
    
    public void setResults(List<AlgorithmResult> results) {
        this.results = results;
    }
}