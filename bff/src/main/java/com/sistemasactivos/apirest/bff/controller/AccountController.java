package com.sistemasactivos.apirest.bff.controller;

import com.sistemasactivos.apirest.bff.interfaces.IAccountController;
import com.sistemasactivos.apirest.bff.model.Account;
import com.sistemasactivos.apirest.bff.model.AuthCredentials;
import com.sistemasactivos.apirest.bff.model.PageResponse;
import com.sistemasactivos.apirest.bff.model.User;
import com.sistemasactivos.apirest.bff.service.AccountService;
import com.sistemasactivos.apirest.bff.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path="/accounts")
public class AccountController implements IAccountController{
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CacheService cacheService;
    
    @Override
    @PostMapping("/login")
    public Mono<String> login(@RequestBody AuthCredentials authCredentials) {    
        return accountService.login(authCredentials);
    }    
    
    @Override
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> register(@RequestBody User user, @RequestParam String token) {    
        return accountService.register(user, token);
    }  
    
    @Override
    @GetMapping("")
    public Flux<Account> getAllRecord() {    
        return accountService.findAll();
    }

    @Override
    @GetMapping("/paged")
    public Mono<PageResponse> getAllRecord(@RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "2") Integer size) {
        return accountService.findAllPaged(page, size);
    }

    @Override
    @GetMapping("/{id}")
    public Mono<Account> getRecordById(@PathVariable("id")Integer id) {
        return accountService.findById(id);
    }

    @Override
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Account> save(@RequestBody Account entity, @RequestParam String token) throws Exception{
        if(!cacheService.hasRequiredRoles(token, "POST")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene acceso");
        }
        return accountService.save(entity);
    }
    
    @Override
    @PutMapping("/{id}")
    public Mono<Account> update(@PathVariable Integer id, @RequestBody Account entity, @RequestParam String token) throws Exception{
        if(!cacheService.hasRequiredRoles(token, "PUT")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene acceso");
        }
        return accountService.update(id, entity);
    }
    
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Account> delete(@PathVariable Integer id, @RequestParam String token) throws Exception{
        if(!cacheService.hasRequiredRoles(token, "DELETE")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene acceso");
        }
        return accountService.delete(id);
    }

    @Override
    @GetMapping("/search")
    public Flux<Account> search(@RequestParam String filter) {
        return accountService.search(filter);
    }   
    
    @Override
    @GetMapping("/searchPaged")
    public Mono<PageResponse> search(@RequestParam String filter,
                                        @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "2") Integer size){
        return accountService.search(filter, page, size);
    }
    
}
