package com.sistemasactivos.apirest.account.service;

import com.sistemasactivos.apirest.account.interfaces.AccountService;
import com.sistemasactivos.apirest.account.model.Account;
import com.sistemasactivos.apirest.account.repository.AccountRepository;
import com.sistemasactivos.apirest.account.repository.BaseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Integer> implements AccountService{
    
    @Autowired
    private AccountRepository accountRepository;
    
    public AccountServiceImpl(BaseRepository<Account, Integer> baseRepository) {
        super(baseRepository);
    }
    
    @Override
    public List<Account> search(String filter) throws Exception{
        try {
            //List<Account> entities = accountRepository.findByBancoContainingOrNombreTitularContaining(filter, filter);
            //List<Account> entities = accountRepository.search(filter);
            List<Account> entities = accountRepository.searchNative(filter);
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<Account> search(String filter, Pageable pageable) throws Exception {
        try {
            Page<Account> entities = accountRepository.searchNative(filter, pageable);
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }  
    
}
