package com.portfolio.tasksync_api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean ini diperlukan untuk proses autentikasi login nanti
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Nonaktifkan CSRF karena kita menggunakan API stateless
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Atur kebijakan sesi menjadi STATELESS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Atur perizinan untuk setiap request HTTP
                .authorizeHttpRequests(auth -> auth
                        // Izinkan semua request ke endpoint di bawah /api/v1/auth/
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Untuk semua request lainnya, perlu autentikasi
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}