package com.labinc.Lab.Inc.services.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção lançada quando um recurso não é encontrado")
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
