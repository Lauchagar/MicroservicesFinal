package com.sa.clase.token.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Collections;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;


public class TokenUtils {
    
    //se crea una clave secreta para firmar el token(se puede recurrir a paginas que generan claves aleatorias
    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    private final static Long ACCESS_TOKEN_LIFE = 2592000L;

    public static String createToken(String name, String email, Collection<? extends GrantedAuthority> roles){
        
        long expirationTime = ACCESS_TOKEN_LIFE * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        
        //Convierto set de roles a List de roles
        List<String> roleName = roles.stream()
                .map(GrantedAuthority :: getAuthority)
                .collect(Collectors.toList());
        //Agrego en las clains extra el nombre y los roles
        Map<String, Object> extra = new HashMap<>();
        extra.put("name", name);
        extra.put("roles", roleName);
        
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();                
    }
    //Se obtiene la autenticacion del token, esto se ejecuta cuando se recibe una peticion
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        String email;
        
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            email = claims.getSubject();
                   
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            return null;
        }
    }
    
}
