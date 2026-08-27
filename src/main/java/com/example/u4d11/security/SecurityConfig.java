package com.example.u4d11.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // abilita @PreAuthorize sui metodi di controller/service
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Questo disabilita il form di login che c'è di default
        httpSecurity.formLogin(formLogin -> formLogin.disable());
        // Questo disabilita le protezioni verso CSRF che quando usiamo l'autenticazione basata su token JWT sono inutili.
        // Anzi addirittura ci complicherebbero anche il FE
        httpSecurity.csrf(csrf -> csrf.disable());
        // Disabilitiamo le sessioni. Per definizione JWT è un meccanismo SENZA SESSIONI (Stateless) quindi dobbiamo disabilitarle
        httpSecurity.sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // Siccome di default spring security mi torna 401 su TUTTI GLI ENDPOINT, tolgo questo controllo (che verrà rimpiazzato dal mio filtro custom)
        httpSecurity.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll());
        // Inserisco il filtro custom prima di quello standard di Spring Security: sarà lui a controllare il token JWT
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
