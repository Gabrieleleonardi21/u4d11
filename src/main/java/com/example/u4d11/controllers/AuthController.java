package com.example.u4d11.controllers;

import com.example.u4d11.payloads.LoginPayload;
import com.example.u4d11.payloads.LoginResponse;
import com.example.u4d11.services.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginPayload payload) {
        return new LoginResponse(authService.login(payload));
    }
}
