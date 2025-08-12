package com.sa.clase.token.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.clase.token.service.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws  AuthenticationException{
        AuthCredentials authCredential = new AuthCredentials();
        
        try {
            authCredential = new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
        } catch (IOException e) {
        }
        
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredential.getEmail(),
                authCredential.getPassword(),
                Collections.emptyList()
        );
        
        System.out.println("Intentando autenticar: "+usernamePAT.getName() + " " + usernamePAT.getCredentials());
        
        return getAuthenticationManager().authenticate(usernamePAT);
                
    }
    
//    @Override
    protected void successfulAuthentication(HttpServletRequest request, 
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        
        System.out.println("Autenticaion exitosa");
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getName(), userDetails.getUsername(), userDetails.getAuthorities());
        
        System.out.println("Token: " + token);
        
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Role", " " + userDetails.getAuthorities());
        response.getWriter().flush();
        
        super.successfulAuthentication(request, response, chain, authResult);
    }
    
    
    
}
