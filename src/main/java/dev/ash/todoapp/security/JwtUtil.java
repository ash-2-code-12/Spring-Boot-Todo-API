package dev.ash.todoapp.security;

import dev.ash.todoapp.model.AppUser;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtUtil {
    private final Key SECRET_KEY;
    private final long EXPIRATION_MS;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration-ms}") long expirationMs){
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());;
        this.EXPIRATION_MS = expirationMs;
    }

    public String generateToken(AppUser user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", List.of(user.getRole()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token)   // parses and validates token
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return !isExpired(token);
        } catch (JwtException e) {
            log.info("JWT validation failed");
            return false;
        }
    }

    public  boolean isExpired(String token) {
        boolean expired = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date());

        if(expired) log.info("token expired: {} \n JWT validation failed", expired);
        return expired;
    }
}
