package com.projetarchi.search.Generate;

public class ApiRequestData{
    private String algorithmName;
    private long executionTime;

    public ApiRequestData() {
    }
    public ApiRequestData(String algorithmName, long executionTime) {
        this.algorithmName = algorithmName;
        this.executionTime = executionTime;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public String toString() {
        return "ApiRequestData{" +
                "algorithmName='" + algorithmName + '\'' +
                ", executionTime=" + executionTime +
                '}';
    }
}


