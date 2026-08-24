package com.example.u4d11.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost {

    @Id
    // con Hibernate 6+, se il campo è di tipo UUID viene generato automaticamente un UUID random
    @GeneratedValue
    private UUID id;

    private String categoria;
    private String titolo;
    private String cover; // generata sempre dal server, mai dal client
    private String contenuto;
    private int tempoDiLettura; // in minuti
    private boolean pubblicato; // true = pubblicato, false = bozza
}
