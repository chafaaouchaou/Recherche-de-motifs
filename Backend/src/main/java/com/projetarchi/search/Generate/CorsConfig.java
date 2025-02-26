package com.projetarchi.search.Generate;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") 
                .allowedMethods("*") 
                .allowedHeaders("*");
    }
}



// package com.projetarchi.search.Generate;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CorsConfig implements WebMvcConfigurer {
//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/**")
//                 .allowedOrigins("https://pattern-matching.chafaaouchaou.online") // Autoriser uniquement cette origine
//                 .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Définir les méthodes autorisées
//                 .allowedHeaders("*")
//                 .allowCredentials(true); // Permettre l'envoi des cookies si besoin
//     }
// }
