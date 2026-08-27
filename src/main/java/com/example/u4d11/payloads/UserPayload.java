package com.example.u4d11.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// DTO usato per POST e PUT di User: il ruolo non è impostabile dal client (sempre USER in creazione,
// invariato in aggiornamento) per evitare che un utente si auto-assegni ADMIN
public record UserPayload(
        @NotBlank(message = "Il nome è obbligatorio")
        String nome,
        @NotBlank(message = "Il cognome è obbligatorio")
        String cognome,
        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è in un formato valido")
        String email,
        @NotBlank(message = "La password è obbligatoria")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
                message = "La password deve contenere almeno 8 caratteri, con almeno una lettera e un numero"
        )
        String password
) {
}
