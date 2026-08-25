package com.example.u4d11.services;

import com.example.u4d11.entities.BlogPost;
import com.example.u4d11.entities.User;
import com.example.u4d11.exceptions.NotFoundException;
import com.example.u4d11.payloads.BlogPostPayload;
import com.example.u4d11.repositories.BlogPostRepository;
import com.example.u4d11.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;

    // constructor injection: con un solo costruttore Spring inietta da solo, senza @Autowired
    public BlogPostService(BlogPostRepository blogPostRepository, UserRepository userRepository) {
        this.blogPostRepository = blogPostRepository;
        this.userRepository = userRepository;
    }

    // il costruttore vuoto di BlogPost è riservato a JPA: qui si usa sempre quello con parametri
    public BlogPost create(BlogPostPayload payload) {
        User autore = findAutore(payload.autoreId());
        BlogPost blogPost = new BlogPost(payload.categoria(),
                payload.titolo(),
                payload.contenuto(),
                payload.tempoDiLettura(), payload.pubblicato());
        blogPost.setAutore(autore);
        return blogPostRepository.save(blogPost);
    }

    public BlogPost findById(UUID id) {
        return blogPostRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<BlogPost> findAll(Boolean pubblicato) {
        // Derived query al posto dello Stream: EXTRA sui post pubblicati/bozza
        if (pubblicato == null) {
            return blogPostRepository.findAll();
        }
        return blogPostRepository.findByPubblicato(pubblicato);
    }

    public List<BlogPost> cercaPerTitolo(String paroleChiave) {
        return blogPostRepository.cercaPerTitoloContenente(paroleChiave);
    }

    public List<BlogPost> findByAutore(UUID autoreId) {
        return blogPostRepository.findByAutore_Id(autoreId);
    }

    public BlogPost update(UUID id, BlogPostPayload payload) {
        BlogPost blogPost = findById(id); // entità già gestita da JPA: si modifica con i setter, niente new BlogPost()
        blogPost.setCategoria(payload.categoria());
        blogPost.setTitolo(payload.titolo());
        blogPost.setContenuto(payload.contenuto());
        blogPost.setTempoDiLettura(payload.tempoDiLettura());
        blogPost.setPubblicato(payload.pubblicato());
        blogPost.setAutore(findAutore(payload.autoreId()));
        // cover non viene mai toccata in fase di update
        return blogPostRepository.save(blogPost);
    }

    public void delete(UUID id) {
        BlogPost blogPost = findById(id); // controllo proprio: lancia NotFoundException se l'id non esiste
        blogPostRepository.delete(blogPost);
    }

    private User findAutore(UUID autoreId) {
        return userRepository.findById(autoreId)
                .orElseThrow(() -> new NotFoundException("Autore con id " + autoreId + " non trovato"));
    }
}
