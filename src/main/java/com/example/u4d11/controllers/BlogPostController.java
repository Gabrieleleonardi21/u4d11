package com.example.u4d11.controllers;

import com.example.u4d11.entities.BlogPost;
import com.example.u4d11.payloads.BlogPostPayload;
import com.example.u4d11.services.BlogPostService;
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

    @Autowired
    private BlogPostService blogPostService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost create(@RequestBody BlogPostPayload payload) {
        return blogPostService.create(payload);
    }

    @GetMapping("/{id}")
    public BlogPost findById(@PathVariable UUID id) {
        return blogPostService.findById(id);
    }

    @GetMapping
    public List<BlogPost> findAll(@RequestParam(required = false) Boolean pubblicato) {
        // pubblicato assente -> tutti i post; pubblicato=true/false -> solo quelli filtrati (EXTRA con Stream)
        return blogPostService.findAll(pubblicato);
    }

    @PutMapping("/{id}")
    public BlogPost update(@PathVariable UUID id, @RequestBody BlogPostPayload payload) {
        return blogPostService.update(id, payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        blogPostService.delete(id);
    }
}
