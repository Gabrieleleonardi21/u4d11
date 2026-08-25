package com.example.u4d11.controllers;

import com.example.u4d11.payloads.BlogPostPayload;
import com.example.u4d11.payloads.BlogPostResponse;
import com.example.u4d11.services.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/blogposts")
public class BlogPostController {
// @Column(columnDefinition = "TEXT") per non avere limiti di testo
    @Autowired
    private BlogPostService blogPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostResponse create(@RequestBody @Valid BlogPostPayload payload) {
        return BlogPostResponse.from(blogPostService.create(payload));
    }

    @GetMapping("/{id}")
    public BlogPostResponse findById(@PathVariable UUID id) {
        return BlogPostResponse.from(blogPostService.findById(id));
    }

    @GetMapping
    public List<BlogPostResponse> findAll(@RequestParam(required = false) Boolean pubblicato) {
        // pubblicato assente -> tutti i post; pubblicato=true/false -> solo quelli filtrati (Derived Query)
        return blogPostService.findAll(pubblicato).stream().map(BlogPostResponse::from).toList();
    }

    // query JPQL con LIKE sul titolo
    @GetMapping("/ricerca")
    public List<BlogPostResponse> cercaPerTitolo(@RequestParam String paroleChiave) {
        return blogPostService.cercaPerTitolo(paroleChiave).stream().map(BlogPostResponse::from).toList();
    }

    // EXTRA: tutti i post di un determinato autore
    @GetMapping("/autore/{autoreId}")
    public List<BlogPostResponse> findByAutore(@PathVariable UUID autoreId) {
        return blogPostService.findByAutore(autoreId).stream().map(BlogPostResponse::from).toList();
    }

    @PutMapping("/{id}")
    public BlogPostResponse update(@PathVariable UUID id, @RequestBody @Valid BlogPostPayload payload) {
        return BlogPostResponse.from(blogPostService.update(id, payload));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        blogPostService.delete(id);
    }
}
