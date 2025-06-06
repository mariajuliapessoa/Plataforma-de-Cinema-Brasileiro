package com.cesar.bracine.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;

    private static final long EXPIRATION_TIME = 86400000; // 24 horas

    public String gerarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String extrairNomeUsuario(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validarToken(String token, String username) {
        final String extractedUsername = extrairNomeUsuario(token);
        return (extractedUsername.equals(username) && !isTokenExpirado(token));
    }

    private boolean isTokenExpirado(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}