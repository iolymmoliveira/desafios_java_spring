package com.iolyOliveira.desafio03.DTO;

import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.List;

public class ValidationError extends CustomError {

    private List<FieldMessage> fieldErrors;

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getFieldErrors() {
        return fieldErrors;
    }

    public void addFieldError(String fieldName, String message) {
        fieldErrors.add(new FieldMessage(fieldName, message));
    }
}
