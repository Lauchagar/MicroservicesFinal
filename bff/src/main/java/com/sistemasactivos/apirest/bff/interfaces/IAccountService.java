package com.sistemasactivos.apirest.bff.interfaces;

import com.sistemasactivos.apirest.bff.model.Account;
import com.sistemasactivos.apirest.bff.model.AuthCredentials;
import com.sistemasactivos.apirest.bff.model.PageResponse;
import com.sistemasactivos.apirest.bff.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IAccountService {
    Mono<String> login(AuthCredentials authCredentials) throws Exception;
    Mono<Void> register(User user, String token) throws Exception;
    Flux<Account> findAll();
    Mono<Account> findById(Integer id) throws Exception;
    Mono<Account> save(Account entity) throws Exception;
    Mono<Account> update(Integer id, Account entity) throws Exception;
    Mono<Account> delete(Integer id) throws Exception;
    Flux<Account> search(String filter) throws Exception;
    Mono<PageResponse> search(String filter, Integer pageNumber, Integer size) throws Exception;
    Mono<PageResponse> findAllPaged(Integer pageNumber, Integer size) throws Exception;
}
