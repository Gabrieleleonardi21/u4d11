package com.example.u4d11.payloads;

import com.example.u4d11.entities.BlogPost;

import java.util.UUID;

public record BlogPostResponse(
        UUID id,
        String categoria,
        String titolo,
        String cover,
        String contenuto,
        int tempoDiLettura,
        boolean pubblicato,
        UUID autoreId,
        String autoreNomeCompleto
) {
    public static BlogPostResponse from(BlogPost blogPost) {
        return new BlogPostResponse(
                blogPost.getId(),
                blogPost.getCategoria(),
                blogPost.getTitolo(),
                blogPost.getCover(),
                blogPost.getContenuto(),
                blogPost.getTempoDiLettura(),
                blogPost.isPubblicato(),
                blogPost.getAutore().getId(),
                blogPost.getAutore().getNome() + " " + blogPost.getAutore().getCognome()
        );
    }
}
