package com.example.u4d11.security;

import com.example.u4d11.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTools jwtTools;
    // stesso resolver usato da Spring MVC per instradare le eccezioni verso @RestControllerAdvice:
    // ci serve perché un filtro gira PRIMA della DispatcherServlet e ExceptionsHandler non lo vedrebbe
    private final HandlerExceptionResolver exceptionResolver;

    public JwtFilter(JwtTools jwtTools, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.jwtTools = jwtTools;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new UnauthorizedException("Header Authorization mancante o non nel formato 'Bearer <token>'");
            }

            String accessToken = authHeader.substring(7); // rimuove il prefisso "Bearer "
            jwtTools.verifyToken(accessToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // login e registrazione utente: qui il client non possiede ancora un token
        String path = request.getServletPath();
        boolean isLogin = path.equals("/api/auth/login");
        boolean isRegistrazione = path.equals("/api/users") && request.getMethod().equals("POST");
        return isLogin || isRegistrazione;
    }
}
