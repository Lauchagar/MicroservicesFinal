package com.sistemasactivos.apirest.bff.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemasactivos.apirest.bff.exception.BusinessException;
import com.sistemasactivos.apirest.bff.exception.ErrorDTO;
import com.sistemasactivos.apirest.bff.interfaces.IAccountService;
import com.sistemasactivos.apirest.bff.model.Account;
import com.sistemasactivos.apirest.bff.model.AuthCredentials;
import com.sistemasactivos.apirest.bff.model.PageResponse;
import com.sistemasactivos.apirest.bff.model.User;
import java.util.List;
import org.springframework.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService implements IAccountService{

    private final WebClient webClientAccount;
    private final WebClient webClientToken;
    
    @Autowired
    private CacheManager cacheManager;
    
    public AccountService(@Qualifier("webClientAccount")WebClient webClientAccount, 
                            @Qualifier("webClientToken")WebClient webClientToken) {
        this.webClientAccount = webClientAccount;
        this.webClientToken = webClientToken;
    }
    
    @Override
    public Mono<String> login(AuthCredentials authCredentials) {
        return webClientToken.post()
            .uri("/login")
            .bodyValue(authCredentials)
            .retrieve()
            .toEntity(String.class)
            .flatMap(response -> {
                HttpHeaders headers = response.getHeaders();
                String bearerToken = headers.getFirst(HttpHeaders.AUTHORIZATION);
                List<String> roles = headers.get("Role");
                if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                    String token = bearerToken.replace("Bearer ", "");
                    return saveCache(token, roles).thenReturn(token);
                }
                else {
                    return Mono.error(new RuntimeException("Token no encontrado"));
                }
            });        
    }
    
    private Mono<List<String>> saveCache(String token, List<String> roles) {
        Cache cache = cacheManager.getCache("jwtTokens");
        if (cache != null) {
            cache.put(token, roles);
            return Mono.just(roles);
        } else {
            return Mono.error(new RuntimeException("Error al acceder a la caché"));
        }
    }
    
    @Override
    public Mono<Void> register(User user, String token) {
        return webClientToken.post()
            .uri("/register")
            .header("Authorization", "Bearer " + token)
            .bodyValue(user)
            .retrieve()
            .bodyToMono(Void.class)
            .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException); 
    }
    
    @Override
    public Flux<Account> findAll() {
        
        return webClientAccount.get()
                .uri("/accounts")      
                .retrieve()
                .bodyToFlux(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);        
    }

    @Override
    public Mono<Account> findById(Integer id){
        
        return webClientAccount.get()
                .uri("/accounts/"+id)      
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException); 
    }
    
    @Override
    public Mono<Account> save(Account entity) {
        return webClientAccount.post()
                .uri("/accounts")
                .bodyValue(entity)
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }

    @Override
    public Mono<Account> update(Integer id, Account entity) {
        return webClientAccount.put()
                .uri("/accounts/"+id)
                .bodyValue(entity)
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }

    @Override
    public Mono<Account> delete(Integer id) {
        return webClientAccount.delete()
                .uri("/accounts/"+id)      
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    @Override
    public Flux<Account> search(String filter) {
        return webClientAccount.get()
                .uri("/accounts/search?filter="+filter)      
                .retrieve()
                .bodyToFlux(Account.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);
    }
    
    @Override
    public Mono<PageResponse> findAllPaged(Integer page, Integer size) {  
        return webClientAccount.get()
                .uri("/accounts/paged?page={page}&size={pageSize}", page, size)      
                .retrieve()
                .bodyToMono(PageResponse.class)
                .onErrorMap(WebClientResponseException.class, this::handleWebClientResponseException);     
    }

    @Override
    public Mono<PageResponse> search(String filter, Integer page, Integer size) {
        
        return webClientAccount.get()
                .uri("/accounts/searchPaged?filter={filter}&page={page}&size={pageSize}", filter, page, size)      
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
