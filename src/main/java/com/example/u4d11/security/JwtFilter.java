package com.example.u4d11.security;

import com.example.u4d11.entities.User;
import com.example.u4d11.exceptions.UnauthorizedException;
import com.example.u4d11.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTools jwtTools;
    private final UserRepository userRepository;
    // stesso resolver usato da Spring MVC per instradare le eccezioni verso @RestControllerAdvice:
    // ci serve perché un filtro gira PRIMA della DispatcherServlet e ExceptionsHandler non lo vedrebbe
    private final HandlerExceptionResolver exceptionResolver;

    public JwtFilter(JwtTools jwtTools, UserRepository userRepository, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.jwtTools = jwtTools;
        this.userRepository = userRepository;
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
            Claims claims = jwtTools.verifyToken(accessToken);

            // il subject del token è l'id dell'utente: lo recuperiamo per popolare il SecurityContext,
            // da cui dipendono sia authentication.principal sia hasRole(...) nelle espressioni @PreAuthorize
            UUID userId = UUID.fromString(claims.getSubject());
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UnauthorizedException("L'utente associato a questo token non esiste più"));

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRuolo().name()));
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, authorities));

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
