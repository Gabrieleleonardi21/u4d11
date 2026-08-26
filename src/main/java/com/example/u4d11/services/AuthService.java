package com.example.u4d11.services;

import com.example.u4d11.entities.User;
import com.example.u4d11.exceptions.UnauthorizedException;
import com.example.u4d11.payloads.LoginPayload;
import com.example.u4d11.repositories.UserRepository;
import com.example.u4d11.security.JwtTools;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTools jwtTools;

    public AuthService(UserRepository userRepository, JwtTools jwtTools) {
        this.userRepository = userRepository;
        this.jwtTools = jwtTools;
    }

    public String login(LoginPayload payload) {
        User user = userRepository.findByEmail(payload.email())
                .orElseThrow(() -> new UnauthorizedException("Email o password errati"));

        if (!user.getPassword().equals(payload.password())) {
            throw new UnauthorizedException("Email o password errati");
        }

        return jwtTools.generateToken(user);
    }
}
