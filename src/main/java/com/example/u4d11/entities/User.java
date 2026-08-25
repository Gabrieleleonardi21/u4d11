package com.example.u4d11.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "users")
// costruttore vuoto riservato a JPA: protected così non può essere chiamato a mano da fuori il package
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE) // l'id è generato da JPA e non deve mai essere riassegnabile dall'esterno
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false, unique = true) // due utenti non possono registrarsi con la stessa email
    private String email;

    @Column(nullable = false)
    @JsonIgnore // la password non deve MAI far parte di un JSON
    @ToString.Exclude // ...e nemmeno finire nei log tramite toString()
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ruolo ruolo;

    public User(String nome, String cognome, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruolo = Ruolo.USER;
    }
}
