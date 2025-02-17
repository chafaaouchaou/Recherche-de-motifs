package com.projetarchi.search.Generate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/search")
public class SearchController {

    @PostMapping("/text")
    public ResponseEntity<MultiAlgorithmResponse> searchText(@RequestBody RequestData requestData) {
        if (requestData.getWords() == null || requestData.getTailleTexte() == null || 
            requestData.getTailleAlphabet() == null || requestData.getAlgoNumber() == null) {
            return ResponseEntity.badRequest().build();
        }

        Generate generate = new Generate();
        Algorithemes algorithemes = new Algorithemes();
        String generatedText = generate.generateRandomText(requestData.getTailleAlphabet(), requestData.getTailleTexte());
        
        List<AlgorithmResult> results = new ArrayList<>();
        
        // Parcourir tous les algorithmes demandés
        for (Integer algoNumber : requestData.getAlgoNumber()) {
            SearchResponse response;
            String algoName;
            
            switch (algoNumber) {
                case 1:
                    response = algorithemes.linearSearch(generatedText, requestData.getWords());
                    algoName = "Recherche linéaire";
                    break;
                case 2:
                    response = algorithemes.boyerMooreSearch(generatedText, requestData.getWords());
                    algoName = "Boyer-Moore";
                    break;
                case 3:
                    response = algorithemes.horspoolSearch(generatedText, requestData.getWords());
                    algoName = "Horspool";
                    break;
                case 4:
                    response = algorithemes.quickSearch(generatedText, requestData.getWords());
                    algoName = "Quick Search";
                    break;
                case 5:
                    response = algorithemes.naiveStrncmpNoFastLoopNoSentinel(generatedText, requestData.getWords());
                    algoName = "Naive Search Strncmp (sans boucle rapide ni sentinelle)";
                    break;
                case 6:
                    response = algorithemes.naiveStrncmpWithFastLoopNoSentinel(generatedText, requestData.getWords());
                    algoName = "Naive Search Strncmp (avec boucle rapide, sans sentinelle)";
                    break;
                case 7:
                    response = algorithemes.naiveStrncmpWithFastLoopWithSentinel(generatedText, requestData.getWords());
                    algoName = "Naive Search Strncmp (avec boucle rapide et sentinelle)";
                    break;

                    case 8:
                    response = algorithemes.morrisPratt(generatedText, requestData.getWords());
                    algoName = "Morris-Pratt";
                    break;

                    case 9:
                    response = algorithemes.knuthMorrisPratt(generatedText, requestData.getWords());
                    algoName = "Knuth-Morris-Pratt";
                    break;

                    case 10:
                    response = algorithemes.naiveInternalWithFastLoopWithSentinel(generatedText, requestData.getWords());
                    algoName = "Naive Internal With Fast Loop With Sentinel";
                    break;

                    case 11:
                    response = algorithemes.naiveInternalWithFastLoopNoSentinel(generatedText, requestData.getWords());
                    algoName = "Naive Internal With Fast Loop ";
                    break;
                    
                    case 12:
                    response = algorithemes.naiveInternalNoFastLoopNoSentinel(generatedText, requestData.getWords());
                    algoName = "Naive Internal";
                    break;

                default:
                    continue; // Ignorer les numéros d'algorithmes invalides
            }
            
            results.add(new AlgorithmResult(algoNumber, algoName, response));
        }
        
        MultiApiRequestData multiApiRequestData = new MultiApiRequestData();
        multiApiRequestData.ListAlgorithmResultToMultiApiRequestData(results);
        // System.out.println(multiApiRequestData.toString());
        GeminiApiClient client = new GeminiApiClient();
        client.processAndGenerateContent(multiApiRequestData);
        // System.out.println(client.getApiresponse());

        MultiAlgorithmResponse multiResponse = new MultiAlgorithmResponse(generatedText, results,client.getApiresponse());

        return ResponseEntity.ok(multiResponse);
    }
}