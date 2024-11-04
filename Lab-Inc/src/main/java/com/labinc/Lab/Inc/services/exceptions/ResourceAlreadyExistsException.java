package com.labinc.Lab.Inc.services.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção lançada quando um recurso já existe")
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
