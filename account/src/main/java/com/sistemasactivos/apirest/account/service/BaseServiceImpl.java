package com.sistemasactivos.apirest.account.service;

import com.sistemasactivos.apirest.account.interfaces.BaseService;
import com.sistemasactivos.apirest.account.model.Base;
import com.sistemasactivos.apirest.account.repository.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public abstract class BaseServiceImpl <E extends Base, ID extends Serializable> implements BaseService <E,ID>{
    
    protected BaseRepository<E, ID > baseRepository;
    
    public BaseServiceImpl (BaseRepository<E, ID > baseRepository){
        this.baseRepository= baseRepository;
    }
    
    @Override
    public List<E> findAll() throws Exception {
        try{
            List<E> entities = baseRepository.findAll();
            return entities;
        }catch (Exception e){
            throw new Exception (e.getMessage());
        }
    }
    
    @Override
    public Page<E> findAll(Pageable pageable) throws Exception {
        try{
            Page<E> entities = baseRepository.findAll(pageable);
            return entities;
        }catch (Exception e){
            throw new Exception (e.getMessage());
        }
    }
    
    @Override
    public E findById(ID id) throws Exception, EntityNotFoundException {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            //Verifica si es nulo
            if(!entityOptional.isPresent()){
                throw new EntityNotFoundException("No existe registro con el id: " + id);
            }
            return entityOptional.get();
        }catch (EntityNotFoundException e) {
            throw e;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    @Transactional
    @Override
    public E save(E entity) throws Exception, ValidationException {
       
        try {
            entity = baseRepository.save(entity);
            return entity;
        }catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    @Transactional
    @Override
    public E update(ID id, E entity) throws Exception, EntityNotFoundException, IllegalArgumentException {
        try {

            Optional<E> entityOptional = baseRepository.findById(id);
            //Verifica si es nulo
            if(!entityOptional.isPresent()){
                throw new EntityNotFoundException("No existe registro con el id: " + id);
            }
            
            E existEntity = entityOptional.get();
            //Verifica ID
            if(!Objects.equals(entity.getId(), existEntity.getId())){
                throw new IllegalArgumentException("Formato de la solicitud incorrecta");
            }
            E appo = baseRepository.save(entity);
            return appo;
        }catch (IllegalArgumentException | EntityNotFoundException e) {
            throw e;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    @Transactional
    @Override
    public boolean delete(ID id) throws Exception, EntityNotFoundException {
        try {
            //Verifica ID
            if(!baseRepository.existsById(id)){
                throw new EntityNotFoundException("No existe registro con el id: " + id);
            }
            baseRepository.deleteById(id);
            return true;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }    
}

