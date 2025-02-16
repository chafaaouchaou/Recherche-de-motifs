package com.projetarchi.search.Generate;

import java.util.Random;

public class Generate {

    public Generate() {
    }



        public String generateRandomText(int alphabetSize, int textLength) {
        StringBuilder text = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < textLength; i++) {
            char randomChar = (char) ('a' + random.nextInt(alphabetSize));
            text.append(randomChar);
        }
        
        return text.toString();
    }
    
}
