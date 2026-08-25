package com.example.u4d11.services;

import com.example.u4d11.entities.User;
import com.example.u4d11.exceptions.NotFoundException;
import com.example.u4d11.payloads.UserPayload;
import com.example.u4d11.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    // constructor injection: con un solo costruttore Spring inietta da solo, senza @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(UserPayload payload) {
        User user = new User(payload.nome(), payload.cognome(), payload.email(), payload.password(), payload.ruolo());
        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utente con id " + id + " non trovato"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(UUID id, UserPayload payload) {
        User user = findById(id); // entità già gestita da JPA: si modifica con i setter, niente new User()
        user.setNome(payload.nome());
        user.setCognome(payload.cognome());
        user.setEmail(payload.email());
        user.setPassword(payload.password());
        user.setRuolo(payload.ruolo());
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        User user = findById(id); // controllo proprio: lancia NotFoundException se l'id non esiste
        userRepository.delete(user);
    }
}
