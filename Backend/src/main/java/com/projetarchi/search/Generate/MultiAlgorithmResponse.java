package com.projetarchi.search.Generate;

import java.util.List;

public class MultiAlgorithmResponse {
    private String generatedText;
    private List<AlgorithmResult> results;
    private String AIanalysis;
    
    public MultiAlgorithmResponse(String generatedText, List<AlgorithmResult> results, String AIanalysis) {
        this.generatedText = generatedText;
        this.results = results;
        this.AIanalysis = AIanalysis;
    }
    
    // Getters et setters

    public String getAIanalysis() {
        return AIanalysis;
    }

    public void setAIanalysis(String aIanalysis) {
        AIanalysis = aIanalysis;
    }

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