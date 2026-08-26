package com.example.u4d11.payloads;

// DTO di risposta del login: il client userà questo token nell'header Authorization
public record LoginResponse(
        String accessToken
) {
}
