package com.projetarchi.projetrechrchemotifs.Produit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/produits")
public class Controlleur {


    // GET - Retrieve all Produits
    @GetMapping("")
    public String hello() {
        return "Hello from good old Spring Boot!";
    }



}
