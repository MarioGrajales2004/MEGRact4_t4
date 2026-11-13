package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Genera, firma y valida tokens JWT con HMAC-SHA256.
 * La clave secreta sale de app.jwt.secret (en el VPS se pasa con la variable JWT_SECRET).
 */
@Component
public class JwtTokenProvider {

    private final SecretKey clave;
    private final long expiracionMs;

    public JwtTokenProvider(@Value("${app.jwt.secret}") String secreto,
                            @Value("${app.jwt.expiration-ms}") long expiracionMs) {
        if (secreto.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException("app.jwt.secret debe tener al menos 32 caracteres");
        }
        this.clave = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.expiracionMs = expiracionMs;
    }

    /** Crea un token: subject = email, más claims con el nombre y el rol. */
    public String generarToken(Usuario usuario) {
        Date ahora = new Date();
        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("nombre", usuario.getNombre())
                .claim("rol", usuario.getRol())
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + expiracionMs))
                .signWith(clave)
                .compact();
    }

    /** Devuelve el email del token si la firma y la expiración son válidas; si no, null. */
    public String obtenerEmailSiEsValido(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(clave).build().parseSignedClaims(token).getPayload();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null; // firma incorrecta, token expirado o mal formado
        }
    }

    public long getExpiracionSegundos() {
        return expiracionMs / 1000;
    }
}
