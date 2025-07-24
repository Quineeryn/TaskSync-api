package com.portfolio.tasksync_api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Mengambil secret key dari application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    // Mengambil waktu kedaluwarsa token dari application.properties
    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Anda bisa menambahkan data tambahan (claims) ke token di sini jika perlu
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // Subject token (biasanya username atau email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Waktu token dibuat
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Waktu kedaluwarsa
                .signWith(SignatureAlgorithm.HS256, secretKey) // Tanda tangan dengan secret key
                .compact();
    }

    // Method untuk validasi token bisa ditambahkan di sini nanti
}