package com.gymtracker.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private SecretKey jwtSecretKey;

    private final long jwtExpirationMs = 86400000; // 24 ore

    @PostConstruct
    public void init() {
        jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateJwtToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecretKey)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}