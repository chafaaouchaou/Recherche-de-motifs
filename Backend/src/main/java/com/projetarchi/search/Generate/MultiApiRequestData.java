package com.projetarchi.search.Generate;

import java.util.ArrayList;
import java.util.List;

public class MultiApiRequestData {
    private List<ApiRequestData> results;


    public MultiApiRequestData() {
    }
    public MultiApiRequestData(List<ApiRequestData> results) {
        this.results = results;
    }
    
    public List<ApiRequestData> getResults() {
        return results;
    }
    
    public void setResults(List<ApiRequestData> results) {
        this.results = results;
    }


    public void ListAlgorithmResultToMultiApiRequestData( List<AlgorithmResult> results ) {
        // for (AlgorithmResult result : results) {
        //     System.out.println(result.toString());
        // }
        List<ApiRequestData> apiRequestDataList = new ArrayList<>();
        for (AlgorithmResult result : results) {
            ApiRequestData apiRequestData = new ApiRequestData();
            apiRequestData.setAlgorithmName(result.getAlgorithmName());
            apiRequestData.setExecutionTime(result.getSearchResponse().getExecutionTime());
            apiRequestDataList.add(apiRequestData);
        }
        this.setResults(apiRequestDataList);
        return;
    }

    public void hello () {
        System.out.println("Hello");
    }

    @Override
    public String toString() {
        return "MultiApiRequestData{" +
                "results=" + results +
                '}';
    }
    
}
