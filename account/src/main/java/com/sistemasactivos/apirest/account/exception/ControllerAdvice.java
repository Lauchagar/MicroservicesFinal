package com.sistemasactivos.apirest.account.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    
    @ExceptionHandler( value = BusinessException.class)
    public ResponseEntity<ErrorDTO> businnesExceptionHandler(BusinessException e){
        ErrorDTO errorDTO = ErrorDTO.builder()
                .status_code(String.valueOf(e.getHttpStatus().value()))
                .status(e.getHttpStatus().getReasonPhrase())
                .message(e.getMessage()).build();
        return new ResponseEntity<>(errorDTO, e.getHttpStatus());
    }
    
}
