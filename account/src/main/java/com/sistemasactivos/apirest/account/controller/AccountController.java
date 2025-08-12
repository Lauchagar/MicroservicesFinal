package com.sistemasactivos.apirest.account.controller;

import com.sistemasactivos.apirest.account.exception.BusinessException;
import com.sistemasactivos.apirest.account.model.Account;
import com.sistemasactivos.apirest.account.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(path="/api/v1/accounts")
public class AccountController extends BaseControllerImpl<Account, AccountServiceImpl>{
    
    @Autowired
    private AccountServiceImpl accountService;
            
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String filter){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.search(filter));
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
    @GetMapping("/searchPaged")
    public ResponseEntity<?> search(@RequestParam String filter, Pageable pageable){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.search(filter, pageable));
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
