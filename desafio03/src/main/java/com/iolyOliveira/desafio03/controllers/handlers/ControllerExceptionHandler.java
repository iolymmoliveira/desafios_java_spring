package com.iolyOliveira.desafio03.controllers.handlers;

import com.iolyOliveira.desafio03.DTO.CustomError;
import com.iolyOliveira.desafio03.DTO.ValidationError;
import com.iolyOliveira.desafio03.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFoundException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError err = new CustomError(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidation(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError(
                Instant.now(),
                status.value(),
                "Dados inválidos",
                request.getRequestURI()
        );

        for(FieldError f : e.getBindingResult().getFieldErrors()){
            err.addFieldError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomError> dataIntegrityViolation(
            DataIntegrityViolationException e,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        String message = "Violação de integridade de dados";
        if (e.getMessage().contains("cpf")) {
            message = "CPF já cadastrado";
        }

        CustomError err = new CustomError(
                Instant.now(),
                status.value(),
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }
}
