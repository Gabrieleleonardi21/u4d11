package com.example.u4d11.repositories;

import com.example.u4d11.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {

    // Derived query: sostituisce il filtro con Stream fatto a mano nel Service
    List<BlogPost> findByPubblicato(boolean pubblicato);

    // Query JPQL con LIKE per cercare i post il cui titolo contiene la parola chiave
    @Query("SELECT b FROM BlogPost b WHERE b.titolo LIKE %:paroleChiave%")
    List<BlogPost> cercaPerTitoloContenente(@Param("paroleChiave") String paroleChiave);

    // EXTRA: tutti i post scritti da un determinato autore
    List<BlogPost> findByAutore_Id(UUID autoreId);
}
