package com.example.u4d11.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(UUID id) {
        super("BlogPost con id " + id + " non trovato");
    }
}
