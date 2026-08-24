package com.example.u4d11.payloads;

// DTO usato per POST e PUT: niente id né cover, così il client non può proprio inviarli
public record BlogPostPayload(
        String categoria,
        String titolo,
        String contenuto,
        int tempoDiLettura,
        boolean pubblicato
) {
}
