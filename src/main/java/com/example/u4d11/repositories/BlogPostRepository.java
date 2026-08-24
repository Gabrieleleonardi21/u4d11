package com.example.u4d11.repositories;

import com.example.u4d11.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {
    // CRUD e findAll già forniti da JpaRepository, non serve altro per ora
}
