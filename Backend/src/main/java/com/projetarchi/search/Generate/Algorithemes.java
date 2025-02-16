package com.projetarchi.search.Generate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Algorithemes {
    private static final int NBR_CARACTERS = 256;

    // Recherche linéaire (existante)
    public SearchResponse linearSearch(String text, String[] words) {
        long startTime = System.currentTimeMillis();
        List<WordResult> wordResults = new ArrayList<>();
        int totalOccurrences = 0;
        
        for (String word : words) {
            int occurrences = countOccurrences(text, word);
            wordResults.add(new WordResult(word, occurrences));
            totalOccurrences += occurrences;
        }
        
        long endTime = System.currentTimeMillis();
        return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
    }

    // Horspool (existante)
    public SearchResponse horspoolSearch(String text, String[] words) {
        long startTime = System.currentTimeMillis();
        List<WordResult> wordResults = new ArrayList<>();
        int totalOccurrences = 0;

        for (String word : words) {
            int occurrences = horspool(text, word);
            if (occurrences > 0) {
                wordResults.add(new WordResult(word, occurrences));
                totalOccurrences += occurrences;
            }
        }

        long endTime = System.currentTimeMillis();
        return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
    }

    // Boyer-Moore (nouvelle méthode)
    public SearchResponse boyerMooreSearch(String text, String[] words) {
        long startTime = System.currentTimeMillis();
        List<WordResult> wordResults = new ArrayList<>();
        int totalOccurrences = 0;

        for (String word : words) {
            int occurrences = boyerMoore(text, word);
                wordResults.add(new WordResult(word, occurrences));
                totalOccurrences += occurrences;
        }

        long endTime = System.currentTimeMillis();
        return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
    }

    // Implémentation de Horspool (existante)
    private int horspool(String text, String pattern) {
        char[] textChars = text.toCharArray();
        char[] patternChars = pattern.toCharArray();
        int textLength = text.length();
        int patternLength = pattern.length();
        
        int[] shiftTable = new int[NBR_CARACTERS];
        createShiftTable(patternChars, patternLength, shiftTable);
        
        int occurrences = 0;
        int i = 0;
        
        while (i <= textLength - patternLength) {
            char c = textChars[i + patternLength - 1];
            
            if (patternChars[patternLength - 1] == c && 
                compareArrays(patternChars, 0, textChars, i, patternLength - 1)) {
                occurrences++;
            }
            
            i += shiftTable[c];
        }
        
        return occurrences;
    }

    // Implémentation de Boyer-Moore (nouvelle méthode)
    private int boyerMoore(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        int[] badCharTable = new int[NBR_CARACTERS];
        int[] goodSuffixTable = new int[patternLength];

        // Prétraitement des heuristiques
        heuristiqueMauvaisCaractere(pattern, badCharTable);
        heuristiqueBonSuffixe(pattern, goodSuffixTable);

        int occurrences = 0;
        int shift = 0;

        while (shift <= textLength - patternLength) {
            int j = patternLength - 1;

            // Comparaison de droite à gauche
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                // Motif trouvé
                occurrences++;
                shift += goodSuffixTable[0];
            } else {
                // Décalage basé sur les heuristiques
                shift += Math.max(goodSuffixTable[j], 
                                  badCharTable[text.charAt(shift + j)] - patternLength + 1 + j);
            }
        }

        return occurrences;
    }

    // Heuristique du mauvais caractère pour Boyer-Moore
    private void heuristiqueMauvaisCaractere(String pattern, int[] badCharTable) {
        int patternLength = pattern.length();

        for (int i = 0; i < NBR_CARACTERS; i++) {
            badCharTable[i] = patternLength;
        }

        for (int i = 0; i < patternLength - 1; i++) {
            badCharTable[pattern.charAt(i)] = patternLength - i - 1;
        }
    }

    // Heuristique du bon suffixe pour Boyer-Moore
    private void heuristiqueBonSuffixe(String pattern, int[] goodSuffixTable) {
        int patternLength = pattern.length();
        int[] suffixes = new int[patternLength];
        calculSuffixes(pattern, suffixes);

        // Initialisation des décalages par défaut
        for (int i = 0; i < patternLength; i++) {
            goodSuffixTable[i] = patternLength;
        }

        // Cas où le suffixe est également un préfixe
        int j = 0;
        for (int i = patternLength - 1; i >= 0; i--) {
            if (suffixes[i] == i + 1) {
                for (; j < patternLength - 1 - i; j++) {
                    if (goodSuffixTable[j] == patternLength) {
                        goodSuffixTable[j] = patternLength - 1 - i;
                    }
                }
            }
        }

        // Décalages pour les suffixes qui ne sont pas des préfixes
        for (int i = 0; i < patternLength - 1; i++) {
            goodSuffixTable[patternLength - 1 - suffixes[i]] = patternLength - 1 - i;
        }
    }

    // Calcul des suffixes pour l'heuristique du bon suffixe
    private void calculSuffixes(String pattern, int[] suffixes) {
        int patternLength = pattern.length();
        int f = 0;
        int g = patternLength - 1;

        suffixes[patternLength - 1] = patternLength;

        for (int i = patternLength - 2; i >= 0; i--) {
            if (i > g && suffixes[i + patternLength - 1 - f] < i - g) {
                suffixes[i] = suffixes[i + patternLength - 1 - f];
            } else {
                if (i < g) {
                    g = i;
                }
                f = i;

                while (g >= 0 && pattern.charAt(g) == pattern.charAt(g + patternLength - 1 - f)) {
                    g--;
                }

                suffixes[i] = f - g;
            }
        }
    }

    // Méthodes utilitaires pour Horspool (existantes)
    private void createShiftTable(char[] pattern, int patternLength, int[] shiftTable) {
        for (int i = 0; i < NBR_CARACTERS; i++) {
            shiftTable[i] = patternLength;
        }
        for (int i = 0; i < patternLength - 1; i++) {
            shiftTable[pattern[i]] = patternLength - 1 - i;
        }
    }

    private boolean compareArrays(char[] arr1, int start1, char[] arr2, int start2, int length) {
        for (int i = 0; i < length; i++) {
            if (arr1[start1 + i] != arr2[start2 + i]) {
                return false;
            }
        }
        return true;
    }

    // Méthode utilitaire pour compter les occurrences (existante)
    private int countOccurrences(String text, String word) {
        int count = 0;
        int lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = text.indexOf(word, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += 1;
            }
        }
        return count;
    }

private void pretraiterTableDecalage(String motif, int[] tableDecalage) {
    int longueurMotif = motif.length();
    
    // Initialisation avec la longueur du motif + 1
    Arrays.fill(tableDecalage, longueurMotif + 1);
    
    // Remplir la table avec les décalages appropriés
    for (int i = 0; i < longueurMotif; i++) {
        tableDecalage[motif.charAt(i)] = longueurMotif - i;
    }
}

public SearchResponse quickSearch(String text, String[] words) {
    long startTime = System.currentTimeMillis();
    List<WordResult> wordResults = new ArrayList<>();
    int totalOccurrences = 0;

    for (String word : words) {
        if (word != null && !word.isEmpty()) {
            int occurrences = quickSearchHelper(text, word);
            if (occurrences > 0) {
                wordResults.add(new WordResult(word, occurrences));
                totalOccurrences += occurrences;
            }
        }
    }

    long endTime = System.currentTimeMillis();
    return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
}

private int quickSearchHelper(String texte, String motif) {
    if (texte == null || motif == null || motif.isEmpty() || texte.length() < motif.length()) {
        return 0;
    }

    int[] tableDecalage = new int[NBR_CARACTERS];
    int nombreOccurences = 0;
    int longueurMotif = motif.length();
    int longueurTexte = texte.length();

    // Prétraitement
    pretraiterTableDecalage(motif, tableDecalage);

    // Recherche
    int j = 0;
    while (j <= longueurTexte - longueurMotif) {
        boolean match = true;
        
        // Vérifier la correspondance caractère par caractère
        for (int i = 0; i < longueurMotif; i++) {
            if (texte.charAt(j + i) != motif.charAt(i)) {
                match = false;
                break;
            }
        }
        
        if (match) {
            nombreOccurences++;
        }
        
        // Calcul du décalage sécurisé
        if (j + longueurMotif < longueurTexte) {
            j += Math.max(1, tableDecalage[texte.charAt(j + longueurMotif)]);
        } else {
     
            break;
        }
    }
    
    return nombreOccurences;
}
// Naive Search avec strncmp (sans boucle rapide ni sentinelle)
public SearchResponse naiveStrncmpNoFastLoopNoSentinel(String text, String[] words) {
    long startTime = System.currentTimeMillis();
    List<WordResult> wordResults = new ArrayList<>();
    int totalOccurrences = 0;

    for (String word : words) {
        int occurrences = naiveStrncmpNoFastLoopNoSentinelHelper(text, word);
        wordResults.add(new WordResult(word, occurrences));
        totalOccurrences += occurrences;
    }

    long endTime = System.currentTimeMillis();
    return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
}

// Helper pour Naive Search sans boucle rapide ni sentinelle
private int naiveStrncmpNoFastLoopNoSentinelHelper(String texte, String mot) {
    int textLength = texte.length();
    int wordLength = mot.length();
    int compteur = 0;

    // Boucle pour vérifier chaque position possible du texte
    for (int i = 0; i <= textLength - wordLength; i++) {
        // Comparaison des caractères du mot avec le texte
        if (texte.substring(i, i + wordLength).equals(mot)) {
            compteur++;
        }
    }
    return compteur;
}

// Naive Search avec boucle rapide (sans sentinelle)
public SearchResponse naiveStrncmpWithFastLoopNoSentinel(String text, String[] words) {
    long startTime = System.currentTimeMillis();
    List<WordResult> wordResults = new ArrayList<>();
    int totalOccurrences = 0;

    for (String word : words) {
        int occurrences = naiveStrncmpWithFastLoopNoSentinelHelper(text, word);
        wordResults.add(new WordResult(word, occurrences));
        totalOccurrences += occurrences;
    }

    long endTime = System.currentTimeMillis();
    return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
}

// Helper pour Naive Search avec boucle rapide (sans sentinelle)
private int naiveStrncmpWithFastLoopNoSentinelHelper(String texte, String mot) {
    int textLength = texte.length();
    int wordLength = mot.length();
    int compteur = 0;

    // On récupère le premier caractère du mot pour la boucle rapide
    char premierCaractere = mot.charAt(0);

    // Boucle pour chaque position possible dans le texte
    for (int i = 0; i <= textLength - wordLength;) {
        // Boucle rapide : on avance 'i' jusqu'à trouver le premier caractère du mot
        while (i <= textLength - wordLength && texte.charAt(i) != premierCaractere) {
            i++;
        }

        // Vérification si la boucle rapide a dépassé les limites
        if (i > textLength - wordLength) break;

        // Comparaison des caractères du mot avec le texte
        if (texte.substring(i, i + wordLength).equals(mot)) {
            compteur++;
        }

        // On avance à la prochaine position pour la recherche
        i++;
    }

    return compteur;
}

// Naive Search avec boucle rapide et sentinelle
public SearchResponse naiveStrncmpWithFastLoopWithSentinel(String text, String[] words) {
    long startTime = System.currentTimeMillis();
    List<WordResult> wordResults = new ArrayList<>();
    int totalOccurrences = 0;

    for (String word : words) {
        int occurrences = naiveStrncmpWithFastLoopWithSentinelHelper(text, word);
        wordResults.add(new WordResult(word, occurrences));
        totalOccurrences += occurrences;
    }

    long endTime = System.currentTimeMillis();
    return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
}

// Helper pour Naive Search avec boucle rapide et sentinelle
private int naiveStrncmpWithFastLoopWithSentinelHelper(String texte, String mot) {
    int textLength = texte.length();
    int wordLength = mot.length();
    int compteur = 0;

    // Créer une copie du texte avec la sentinelle (le mot ajouté à la fin)
    String textAvecSentinelle = texte + mot;

    // On récupère le premier caractère du mot pour la boucle rapide
    char premierCaractere = mot.charAt(0);
    int i = 0;

    // Boucle pour chaque position possible dans le texte (avec sentinelle)
    while (true) {
        // Boucle rapide : on avance 'i' jusqu'à trouver le premier caractère du mot
        while (textAvecSentinelle.charAt(i) != premierCaractere) {
            i++;
        }

        // Comparaison des caractères du mot avec le texte
        if (textAvecSentinelle.substring(i, i + wordLength).equals(mot)) {
            // Vérification si l'occurrence est dans la partie initiale du texte
            if (i <= textLength - wordLength) {
                compteur++;
            } else {
                // Occurrence dans la sentinelle, on arrête
                break;
            }
        }

        // On avance à la prochaine position pour la recherche
        i++;
    }

    return compteur;


}





  //////////////////////////////////
  //////////////////////////////////
  //////////////////////////////////
  //////////////////////////////////
  //////////////////////////////////
  //////////////////////////////////
  //////////////////////////////////
  //////////////////////////////////
  //////////////////////////////////


  // Ajout des nouvelles méthodes pour les recherches naïves
public SearchResponse naiveInternalNoFastLoopNoSentinel(String text, String[] words) {
    long startTime = System.currentTimeMillis();
    List<WordResult> wordResults = new ArrayList<>();
    int totalOccurrences = 0;

    for (String word : words) {
        int occurrences = countOccurrencesNaiveNoFastLoop(text, word);
        wordResults.add(new WordResult(word, occurrences));
        totalOccurrences += occurrences;
    }

    long endTime = System.currentTimeMillis();
    return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
}

private int countOccurrencesNaiveNoFastLoop(String text, String word) {
    int textLength = text.length();
    int wordLength = word.length();
    int counter = 0;
    int j;

    for (int i = 0; i <= textLength - wordLength; i++) {
        for (j = 0; j < wordLength; j++) {
            if (text.charAt(i + j) != word.charAt(j)) {
                break;
            }
        }
        if (j == wordLength) {
            counter++;
        }
    }
    return counter;
}

public SearchResponse naiveInternalWithFastLoopNoSentinel(String text, String[] words) {
    long startTime = System.currentTimeMillis();
    List<WordResult> wordResults = new ArrayList<>();
    int totalOccurrences = 0;

    for (String word : words) {
        int occurrences = countOccurrencesNaiveFastLoop(text, word);
        wordResults.add(new WordResult(word, occurrences));
        totalOccurrences += occurrences;
    }

    long endTime = System.currentTimeMillis();
    return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
}

private int countOccurrencesNaiveFastLoop(String text, String word) {
    int textLength = text.length();
    int wordLength = word.length();
    int counter = 0;
    int j;
    
    char firstChar = word.charAt(0);

    for (int i = 0; i <= textLength - wordLength;) {
        while (i <= textLength - wordLength && text.charAt(i) != firstChar) {
            i++;
        }
        
        if (i > textLength - wordLength) break;

        for (j = 0; j < wordLength; j++) {
            if (text.charAt(i + j) != word.charAt(j)) {
                break;
            }
        }

        if (j == wordLength) {
            counter++;
        }
        i++;
    }
    return counter;
}

public SearchResponse naiveInternalWithFastLoopWithSentinel(String text, String[] words) {
    long startTime = System.currentTimeMillis();
    List<WordResult> wordResults = new ArrayList<>();
    int totalOccurrences = 0;

    for (String word : words) {
        int occurrences = countOccurrencesNaiveFastLoopSentinel(text, word);
        wordResults.add(new WordResult(word, occurrences));
        totalOccurrences += occurrences;
    }

    long endTime = System.currentTimeMillis();
    return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
}

private int countOccurrencesNaiveFastLoopSentinel(String text, String word) {
    int textLength = text.length();
    int wordLength = word.length();
    int counter = 0;
    
    String textWithSentinel = text + word;
    char firstChar = word.charAt(0);
    int i = 0;
    
    while (true) {
        while (textWithSentinel.charAt(i) != firstChar) {
            i++;
        }

        boolean found = true;
        for (int j = 0; j < wordLength; j++) {
            if (textWithSentinel.charAt(i + j) != word.charAt(j)) {
                found = false;
                break;
            }
        }

        if (found) {
            if (i <= textLength - wordLength) {
                counter++;
            } else {
                break;
            }
        }
        i++;
    }
    return counter;
}

public SearchResponse morrisPratt(String text, String[] words) {
    long startTime = System.currentTimeMillis();
    List<WordResult> wordResults = new ArrayList<>();
    int totalOccurrences = 0;

    for (String word : words) {
        int occurrences = searchMP(text, word);
        wordResults.add(new WordResult(word, occurrences));
        totalOccurrences += occurrences;
    }

    long endTime = System.currentTimeMillis();
    return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
}

private int searchMP(String text, String pattern) {
    int[] next = new int[NBR_CARACTERS];
    int patternLength = pattern.length();
    int textLength = text.length();
    int textIndex = 0;
    int patternIndex = 0;
    int counter = 0;

    computeMPNext(pattern, patternLength, next);

    while (textIndex < textLength) {
        while (patternIndex > -1 && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
            patternIndex = next[patternIndex];
        }
        patternIndex++;
        textIndex++;
        
        if (patternIndex >= patternLength) {
            counter++;
            patternIndex = next[patternIndex];
        }
    }
    return counter;
}

private void computeMPNext(String pattern, int patternLength, int[] next) {
    int patternIndex = 0;
    int prefixLength = next[0] = -1;

    while (patternIndex < patternLength) {
        while (prefixLength > -1 && pattern.charAt(patternIndex) != pattern.charAt(prefixLength)) {
            prefixLength = next[prefixLength];
        }
        next[++patternIndex] = ++prefixLength;
    }
}

public SearchResponse knuthMorrisPratt(String text, String[] words) {
    long startTime = System.currentTimeMillis();
    List<WordResult> wordResults = new ArrayList<>();
    int totalOccurrences = 0;

    for (String word : words) {
        int occurrences = searchKMP(text, word);
        wordResults.add(new WordResult(word, occurrences));
        totalOccurrences += occurrences;
    }

    long endTime = System.currentTimeMillis();
    return new SearchResponse(totalOccurrences, endTime - startTime, wordResults);
}

private int searchKMP(String text, String pattern) {
    int[] next = new int[NBR_CARACTERS];
    int counter = 0;
    int textLength = text.length();
    int patternLength = pattern.length();
    int textIndex = 0;
    int patternIndex = 0;

    computeKMPNext(pattern, patternLength, next);

    while (textIndex < textLength) {
        while (patternIndex > -1 && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
            patternIndex = next[patternIndex];
        }
        textIndex++;
        patternIndex++;

        if (patternIndex >= patternLength) {
            counter++;
            patternIndex = next[patternIndex];
        }
    }
    return counter;
}

private void computeKMPNext(String pattern, int patternLength, int[] next) {
    int patternIndex = 0;
    int prefixLength = next[0] = -1;

    while (patternIndex < patternLength) {
        while (prefixLength > -1 && pattern.charAt(patternIndex) != pattern.charAt(prefixLength)) {
            prefixLength = next[prefixLength];
        }
        patternIndex++;
        prefixLength++;

        if (patternIndex < patternLength && pattern.charAt(patternIndex) == pattern.charAt(prefixLength)) {
            next[patternIndex] = next[prefixLength];
        } else {
            next[patternIndex] = prefixLength;
        }
    }
}




}