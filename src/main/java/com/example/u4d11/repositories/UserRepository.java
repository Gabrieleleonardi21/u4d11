package com.example.u4d11.repositories;

import com.example.u4d11.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // CRUD e findAll già forniti da JpaRepository, non serve altro per ora
}
