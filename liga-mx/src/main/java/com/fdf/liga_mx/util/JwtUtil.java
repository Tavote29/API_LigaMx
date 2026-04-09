package com.fdf.liga_mx.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private final SecretKey clave;
    private final long expirationMinutes;

    public JwtUtil(
            @Value("${app.jwt.secretkey}") String key,
            @Value("${app.jwt.expiration-minutes:60}") long expirationMinutes) {

        this.clave = Keys.hmacShaKeyFor(key.getBytes());
        this.expirationMinutes = expirationMinutes;
    }

    public String generarToken(String subject, Collection<? extends GrantedAuthority> auths) {
        String roles = auths.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant now = Instant.now();


        return Jwts.builder()
                .subject(subject)
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
                .signWith(clave)
                .compact();
    }


    private Claims extraerClaims(String token) {

        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extraerUsuario(String token) {
        return extraerClaims(token).getSubject();
    }

    public boolean validarToken(String token, String usuarioEsperado) {
        try {
            Claims claims = extraerClaims(token);

            return usuarioEsperado.equals(claims.getSubject())
                    && claims.getExpiration().after(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            log.error("Token JWT inválido o expirado: {}", e.getMessage());
            return false;
        }
    }

    public Instant getExpirationToken(String token) {
        return extraerClaims(token).getExpiration().toInstant();
    }
}