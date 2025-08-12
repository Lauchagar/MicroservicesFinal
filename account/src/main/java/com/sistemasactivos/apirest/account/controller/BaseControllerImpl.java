package com.sistemasactivos.apirest.account.controller;

import com.sistemasactivos.apirest.account.exception.BusinessException;
import com.sistemasactivos.apirest.account.interfaces.BaseController;
import com.sistemasactivos.apirest.account.model.Base;
import com.sistemasactivos.apirest.account.service.BaseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public abstract class BaseControllerImpl <E extends Base, S extends BaseServiceImpl<E, Integer>> implements BaseController<E, Integer>{
    
    @Autowired
    public S service;
    
    @Override
    @GetMapping("")
    @Operation(
        description = "Obtener todos los registros",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "500", ref = "serverError")
        }
    )
    public ResponseEntity<?> getAllRecord() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {       
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    @Override
    @GetMapping("/paged")
    @Operation(
        description = "Obtener todos los registros",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "500", ref = "serverError")
        }
    )
    public ResponseEntity<?> getAllRecord(Pageable pageable) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
        } catch (Exception e) {       
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    @Override
    @GetMapping("/{id}")
    @Operation(
        description = "Obtener un registro por ID",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "serverError")
        }
    )
    public ResponseEntity<?> getRecordById(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (EntityNotFoundException e) {
            throw new BusinessException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {       
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @PostMapping("")
    @Operation(
        description = "Crear un registro",
        responses = {
            @ApiResponse(responseCode = "201", ref = "created"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "serverError")
        }
    )
    public ResponseEntity<?> save(@RequestBody E entity) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
        }catch (ValidationException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {       
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @PutMapping("/{id}")
    @Operation(
        description = "Actualizar un registro por ID",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "serverError")
        }
    )
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody E entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity));
        }catch (IllegalArgumentException e) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new BusinessException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {       
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    @Override
    @DeleteMapping("/{id}")
    @Operation(
        description = "Eliminar un registro por ID",
        responses = {
            @ApiResponse(responseCode = "204", ref = "noContent"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "serverError")
        }
    )
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        } catch (EntityNotFoundException e) {
            throw new BusinessException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {       
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
