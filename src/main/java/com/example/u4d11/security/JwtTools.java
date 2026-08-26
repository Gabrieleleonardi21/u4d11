package com.example.u4d11.security;

import com.example.u4d11.entities.User;
import com.example.u4d11.exceptions.UnauthorizedException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTools {

    @Value("${spring.jwt.secret}")
    private String secret;

    // genera un token valido 24 ore con l'id dell'utente come subject
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .signWith(getSecretKey())
                .compact();
    }

    // verifica firma e scadenza; qualunque anomalia (token alterato, scaduto, malformato) diventa un 401
    public void verifyToken(String accessToken) {
        try {
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(accessToken);
        } catch (JwtException e) {
            throw new UnauthorizedException("Il token fornito non è valido o è scaduto");
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
