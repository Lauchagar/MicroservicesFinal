package com.sistemasactivos.apirest.bff.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    
    //Metodo para manejar las excepciones de validacion
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException e) {
        // obtengo todos los errores de validacion y los guardo en una lista
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        // creo el objeto ErrorDTO y lo devuelvo
        ErrorDTO errorDTO = ErrorDTO.builder()
                .status_code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Errores de validación: " + String.join(", ", errorMessages))
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
