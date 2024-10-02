package com.example.questionapp.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    // JWT token oluştur
    public String generateJwtToken(Authentication authentication) {
        String username = authentication.getName();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Token'dan kullanıcı adını al
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    // JWT Token'ı doğrula
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.err.println("Geçersiz JWT imzası: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Geçersiz JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token süresi dolmuş: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Desteklenmeyen JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT token boş: " + e.getMessage());
        }

        return false;
    }
}
