package com.example.u4d11.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// DTO usato per il login: solo le credenziali, niente altro
public record LoginPayload(
        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è in un formato valido")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        String password
) {
}
