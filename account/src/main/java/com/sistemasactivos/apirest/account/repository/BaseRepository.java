package com.sistemasactivos.apirest.account.repository;

import com.sistemasactivos.apirest.account.model.Base;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

//ACA VAN TODAS LAS FUNCIONES GENERICAS

@NoRepositoryBean
public interface BaseRepository <E extends Base, ID extends Serializable> extends JpaRepository<E, ID>{
    
}
