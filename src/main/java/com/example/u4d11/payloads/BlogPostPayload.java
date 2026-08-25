package com.example.u4d11.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

// DTO usato per POST e PUT: niente id né cover, così il client non può proprio inviarli
public record BlogPostPayload(
        @NotBlank(message = "La categoria è obbligatoria")
        String categoria,
        @NotBlank(message = "Il titolo è obbligatorio")
        String titolo,
        @NotBlank(message = "Il contenuto è obbligatorio")
        String contenuto,
        @Min(value = 1, message = "Il tempo di lettura deve essere di almeno 1 minuto")
        int tempoDiLettura,
        boolean pubblicato,
        @NotNull(message = "L'id dell'autore è obbligatorio")
        UUID autoreId
) {
}
