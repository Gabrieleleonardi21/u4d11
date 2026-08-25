package com.example.u4d11.exceptions;

import com.example.u4d11.payloads.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException e) {
        return ErrorResponse.of("La risorsa che stai cercando non esiste: " + e.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    // scatta quando @Valid sul payload trova campi non validi (es. titolo vuoto)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException e) {
        String errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Errore di validazione");
        return ErrorResponse.of(errors, HttpStatus.BAD_REQUEST.value());
    }

    // UUID non valido nel path variable (es. "b5e5bada988l" invece di UUID corretto)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String message = String.format("Il valore '%s' per '%s' non è valido. Atteso: %s",
                e.getValue(), e.getName(), e.getRequiredType().getSimpleName());
        return ErrorResponse.of(message, HttpStatus.BAD_REQUEST.value());
    }

    // scatta quando il database rifiuta i dati per un vincolo violato (es. email UNIQUE già registrata)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrity(DataIntegrityViolationException e) {
        return ErrorResponse.of("Dati in conflitto con quelli già presenti: l'email potrebbe essere già registrata", HttpStatus.CONFLICT.value());
    }

    // rete di sicurezza per ogni eccezione non gestita sopra: mai far uscire la Whitelabel Error Page di Spring
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception e) {
        return ErrorResponse.of("Si è verificato un errore imprevisto, riprova più tardi", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
