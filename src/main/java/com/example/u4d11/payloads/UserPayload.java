package com.example.u4d11.payloads;

import com.example.u4d11.entities.Ruolo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

// DTO usato per POST e PUT di User
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
        String password,
        @NotNull(message = "Il ruolo è obbligatorio (USER o ADMIN)")
        Ruolo ruolo
) {
}
