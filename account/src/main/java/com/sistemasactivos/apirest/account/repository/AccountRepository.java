package com.sistemasactivos.apirest.account.repository;

import com.sistemasactivos.apirest.account.model.Account;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//CUANDO SE NECESITA ALGUNA FUNCION QUE NO ES GENERICA VAN ACA
@Repository
public interface AccountRepository extends BaseRepository<Account, Integer> {
    
    //JPA
    List<Account> findByBancoContainingOrNombreTitularContaining(String banco, String nombreTitular);
    
    //JPQL --- ligada al codigo
    @Query( value ="SELECT a FROM Account a WHERE a.banco LIKE %:filter% OR a.nombreTitular LIKE %:filter%")
    List<Account> search(@Param ("filter") String filter);
    
    //NATIVE QL --- ligada a la bd
    @Query( value ="SELECT * FROM account a WHERE a.banco LIKE %:filter% OR a.nombre_titular LIKE %:filter%", nativeQuery = true)
    List<Account> searchNative(@Param ("filter") String filter);
    
    Page<Account> findByBancoContainingOrNombreTitularContaining(String banco, String nombreTitular, Pageable pageable);
    
    //JPQL --- ligada al codigo
    @Query( value ="SELECT a FROM Account a WHERE a.banco LIKE %:filter% OR a.nombreTitular LIKE %:filter%")
    Page<Account> search(@Param ("filter") String filter, Pageable pageable);
    
    //NATIVE QL --- ligada a la bd
    @Query( value ="SELECT * FROM account a WHERE a.banco LIKE %:filter% OR a.nombre_titular LIKE %:filter%", nativeQuery = true)
    Page<Account> searchNative(@Param ("filter") String filter, Pageable pageable);
    
}
