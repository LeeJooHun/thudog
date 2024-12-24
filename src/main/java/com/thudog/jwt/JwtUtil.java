package com.thudog.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private String secretKey = System.getenv("JWT_SECRET_KEY");

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 1주일
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    public Claims extractClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes()); // secretKey를 적절한 방식으로 변환합니다.
        return Jwts.parserBuilder()
                .setSigningKey(key) // 변경된 부분: Key 객체를 사용합니다.
                .build() // parserBuilder로 Parser를 빌드합니다.
                .parseClaimsJws(token) // 토큰 파싱
                .getBody(); // Claims 추출
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }
}