package com.projetarchi.search.Generate;

public class RequestData {

    private Integer tailleTexte;
    private Integer tailleAlphabet;
    private Integer[] algoNumber;
    private String[] words;
    
    // Add new constructors
    public RequestData() {
    }
    
    public RequestData(Integer tailleTexte, Integer tailleAlphabet, Integer[] algoNumber, String[] words) {
        this.tailleTexte = tailleTexte;
        this.tailleAlphabet = tailleAlphabet;
        this.algoNumber = algoNumber;
        this.words = words;
    }
    
    // Add getters and setters for new fields
    public Integer[] getAlgoNumber() {
        return algoNumber;
    }
    
    public void setAlgoNumber(Integer[] algoNumber) {
        this.algoNumber = algoNumber;
    }
    
    public String[] getWords() {
        return words;
    }
    
    public void setWords(String[] words) {
        this.words = words;
    }

    public RequestData(Integer tailleTexte, Integer tailleAlphabet) {
        this.tailleTexte = tailleTexte;
        this.tailleAlphabet = tailleAlphabet;
    }

    public Integer getTailleTexte() {
        return tailleTexte;
    }

    public void setTailleTexte(Integer tailleTexte) {
        this.tailleTexte = tailleTexte;
    }

    public Integer getTailleAlphabet() {
        return tailleAlphabet;
    }

    public void setTailleAlphabet(Integer tailleAlphabet) {
        this.tailleAlphabet = tailleAlphabet;
    }

}
