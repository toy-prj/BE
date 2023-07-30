package com.example.global.security;

import com.example.domain.user.domain.Role;
import com.example.domain.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String createToken(User user){

        Date expiry =  Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );

        Map<String, Object> claims = new HashMap<>();
        claims.put("mail", user.getMail());
        claims.put("role", user.getRole().toString());

        return Jwts.builder()
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes())
                        , SignatureAlgorithm.HS512
                )
                .setClaims(claims)
                .setIssuer("MovieWorld")
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .setSubject(String.valueOf(user.getId()))
                .compact();
    }

    public TokenUserInfo validateAndGetTokenUserInfo(String token){

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return TokenUserInfo.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .mail(claims.get("mail", String.class))
                .role(Role.valueOf(claims.get("role", String.class)))
                .build();
    }

}
