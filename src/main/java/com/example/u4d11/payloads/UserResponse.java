package com.example.u4d11.payloads;

import com.example.u4d11.entities.Ruolo;
import com.example.u4d11.entities.User;

import java.util.UUID;

// DTO di risposta: niente password, non va mai restituita al client
public record UserResponse(
        UUID id,
        String nome,
        String cognome,
        String email,
        Ruolo ruolo
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getNome(), user.getCognome(), user.getEmail(), user.getRuolo());
    }
}
