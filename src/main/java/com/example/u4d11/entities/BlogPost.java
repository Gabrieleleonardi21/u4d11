package com.example.u4d11.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
// costruttore vuoto riservato a JPA: protected così non può essere chiamato a mano da fuori il package
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class BlogPost {

    @Id
    // con Hibernate 6+, se il campo è di tipo UUID viene generato automaticamente un UUID random
    @GeneratedValue
    @Setter(AccessLevel.NONE) // l'id è generato da JPA e non deve mai essere riassegnabile dall'esterno
    private UUID id;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    @Setter(AccessLevel.NONE) // generata sempre dal server, mai dal client
    private String cover;

    @Column(nullable = false, columnDefinition = "TEXT") // TEXT: nessun limite di lunghezza sul contenuto
    private String contenuto;

    @Column(nullable = false)
    private int tempoDiLettura; // in minuti

    @Column(nullable = false)
    private boolean pubblicato; // true = pubblicato, false = bozza

    @ManyToOne
    @JoinColumn(name = "autore_id", nullable = false)
    @ToString.Exclude // evita di trascinare tutto l'autore dentro il toString del post
    private User autore;

    public BlogPost(String categoria, String titolo, String contenuto, int tempoDiLettura, boolean pubblicato) {
        this.categoria = categoria;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.tempoDiLettura = tempoDiLettura;
        this.pubblicato = pubblicato;
        this.cover = "https://picsum.photos/200/300";
    }
}
