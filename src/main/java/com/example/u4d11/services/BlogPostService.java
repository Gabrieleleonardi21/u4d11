package com.example.u4d11.services;

import com.example.u4d11.entities.BlogPost;
import com.example.u4d11.exceptions.NotFoundException;
import com.example.u4d11.payloads.BlogPostPayload;
import com.example.u4d11.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    public BlogPost create(BlogPostPayload payload) {
        BlogPost blogPost = new BlogPost();
        blogPost.setCategoria(payload.categoria());
        blogPost.setTitolo(payload.titolo());
        blogPost.setContenuto(payload.contenuto());
        blogPost.setTempoDiLettura(payload.tempoDiLettura());
        blogPost.setPubblicato(payload.pubblicato());
        blogPost.setCover("https://picsum.photos/200/300"); // cover sempre generata dal server
        return blogPostRepository.save(blogPost);
    }

    public BlogPost findById(UUID id) {
        return blogPostRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<BlogPost> findAll(Boolean pubblicato) {
        // controllo proprio del Service: filtro EXTRA sui post pubblicati/bozza fatto con uno Stream su findAll()
        if (pubblicato == null) {
            return blogPostRepository.findAll();
        }
        return blogPostRepository.findAll().stream()
                .filter(post -> post.isPubblicato() == pubblicato)
                .toList();
    }

    public BlogPost update(UUID id, BlogPostPayload payload) {
        BlogPost blogPost = findById(id); // controllo proprio: lancia NotFoundException se l'id non esiste
        blogPost.setCategoria(payload.categoria());
        blogPost.setTitolo(payload.titolo());
        blogPost.setContenuto(payload.contenuto());
        blogPost.setTempoDiLettura(payload.tempoDiLettura());
        blogPost.setPubblicato(payload.pubblicato());
        // cover non viene mai toccata in fase di update
        return blogPostRepository.save(blogPost);
    }

    public void delete(UUID id) {
        BlogPost blogPost = findById(id); // controllo proprio: lancia NotFoundException se l'id non esiste
        blogPostRepository.delete(blogPost);
    }
}
