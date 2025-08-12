package com.sistemasactivos.accountweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemasactivos.accountweb.exception.BusinessException;
import com.sistemasactivos.accountweb.exception.ErrorDTO;
import com.sistemasactivos.accountweb.model.Account;
import com.sistemasactivos.accountweb.model.AuthCredentials;
import com.sistemasactivos.accountweb.model.PageResponse;
import com.sistemasactivos.accountweb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
public class AccountService {
    @Autowired
    WebClient webClient;
    
    public Mono<String> login(AuthCredentials entity){
        return webClient.post()
                .uri("/accounts/login")   
                .bodyValue(entity)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    public Mono<Void> register(User entity, String token){
        return webClient.post()
                .uri("/accounts/register?token="+token)   
                .bodyValue(entity)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    public Flux<Account> findAll() {
        return webClient.get()
                .uri("/accounts")   
                .retrieve()
                .bodyToFlux(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    public Mono<PageResponse> findAllPaged(Integer page, Integer size) {
        return webClient.get()
                .uri("/accounts/paged?page={page}&size={pageSize}",page, size)   
                .retrieve()
                .bodyToMono(PageResponse.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    public Mono<Account> findById(Integer id) {
        return webClient.get()
                .uri("/accounts/"+id)  
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    public Mono<Account> save(Account entity, String token) {
        return webClient.post()
                .uri("/accounts?token="+token)  
                .bodyValue(entity)
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    public Mono<Account> update(Integer id, Account entity, String token) {
        return webClient.put()
                .uri("/accounts/"+id+"?token="+token)  
                .bodyValue(entity)
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    public Mono<Account> delete(Integer id, String token) {
        return webClient.delete()
                .uri("/accounts/"+id+"?token="+token)  
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    public Flux<Account> findAllSearch(String filter) {
        return webClient.get()
                .uri("/accounts/search?filter={filter}", filter)   
                .retrieve()
                .bodyToFlux(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    public Mono<PageResponse> findAllSearchPaged(String filter, Integer page, Integer size) {
        return webClient.get()
                .uri("/accounts/searchPaged?filter={filter}&page={page}&size={pageSize}", filter,page, size)   
                .retrieve()
                .bodyToMono(PageResponse.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    
    
    private BusinessException handleWebClientResponseException(WebClientResponseException ex) {
        try {
            ErrorDTO errorResponse = new ObjectMapper().readValue(ex.getResponseBodyAsString(), ErrorDTO.class);
            return new BusinessException(HttpStatus.valueOf(ex.getRawStatusCode()), errorResponse.getMessage());
        } catch (JsonProcessingException e) {
            return new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la respuesta.");
        }
    }
}
