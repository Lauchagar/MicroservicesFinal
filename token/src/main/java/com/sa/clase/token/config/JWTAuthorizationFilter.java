package com.sa.clase.token.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    
    @Value("4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud")
    private String secretKeyToken;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Entro a doFilterInternal");

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.replace("Bearer ", "");
            Collection<GrantedAuthority> authorities = extractAuthoritiesFromToken(token);
            if (authorities != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    
    private Collection<GrantedAuthority> extractAuthoritiesFromToken(String token) {
        try {
            System.out.println("Entro a extractAuthoritiesFromToken");
            Claims claims = Jwts.parser()
                .setSigningKey(secretKeyToken.getBytes())
                .parseClaimsJws(token)
                .getBody();

            System.out.println("Claims: " + claims);
            
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            List<String> roles = (List<String>) claims.get("roles");
            
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            
            System.out.println("Authorities: " + authorities);
            
            return authorities;

        } catch (Exception e) {
            return null; // En caso de error, devuelve null
        }
    }
    
}
