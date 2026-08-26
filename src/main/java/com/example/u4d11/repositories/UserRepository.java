package com.example.u4d11.repositories;

import com.example.u4d11.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // usata dal login per recuperare l'utente a partire dall'email inserita
    Optional<User> findByEmail(String email);
}
