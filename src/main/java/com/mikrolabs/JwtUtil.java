package com.mikrolabs;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
  private static final String SECRET = "UIstzOjEbDvGZpQgpVicHFITexjyDwca";
  private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
  private static final long EXPIRATION_MS = 1000 * 60 * 60;

  public static String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(KEY)
                .compact();
    }

    // Valida e extrai o subject (username)
    public static String validateToken(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Verifica se o token é válido
    public static boolean isValid(String token) {
        try {
            validateToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
