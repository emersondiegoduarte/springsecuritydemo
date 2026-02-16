package com.spring.security.springsecuritydemo.login.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String JWT_SECRET_KEY = "JWT_SECRET_KEY_ALGORITMO_PARA_TESTE_DEVE_SER_NO_AMBIENTE";
    public static final String JWT_SECRET_DEFAULT_VALUE = "34gsdsf22432sdfs245334dfdfgdf334512312";


    public String generateJwtToken(Authentication authentication){
        String jwtToken;
        String secret = JWT_SECRET_KEY;
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        var fetchedUser = (User) authentication.getPrincipal();
        jwtToken = Jwts.builder().issuer("Spring Security Demo").subject("JWT Token")
                .claim("username", fetchedUser.getUsername())
                .claim("roles", authentication.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date((new java.util.Date()).getTime() + 24 * 60 * 60 * 1000))
                .signWith(secretKey).compact();
        return jwtToken;
    }
}
