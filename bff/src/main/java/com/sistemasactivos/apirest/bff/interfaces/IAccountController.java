package com.sistemasactivos.apirest.bff.interfaces;

import com.sistemasactivos.apirest.bff.model.Account;
import com.sistemasactivos.apirest.bff.model.AuthCredentials;
import com.sistemasactivos.apirest.bff.model.PageResponse;
import com.sistemasactivos.apirest.bff.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IAccountController {
    Mono<String> login(@RequestBody AuthCredentials authCredentials);
    Mono<Void> register(@RequestBody User user, @RequestParam String token);
    Flux<Account> getAllRecord();
    Mono<PageResponse> getAllRecord(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                @RequestParam(defaultValue = "2") Integer size);
    Mono<Account> getRecordById(@PathVariable("id") Integer id);
    Mono<Account> save(@RequestBody Account entity, String token) throws Exception;
    Mono<Account> update(@PathVariable Integer id, @RequestBody Account entity, String token) throws Exception;
    Mono<Account> delete(@PathVariable Integer id, String token) throws Exception;
    Flux<Account> search(@RequestParam String filter);
    Mono<PageResponse> search(@RequestParam String filter,
                                        @RequestParam(defaultValue = "0") Integer pageNumber,
                                            @RequestParam(defaultValue = "2") Integer size);

}
