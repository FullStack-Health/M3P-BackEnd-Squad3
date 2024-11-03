package com.labinc.Lab.Inc.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Informações sobre erros customizados")
public class CustomError {

    @Schema(description = "Timestamp do erro", example = "2024-01-01T12:00:00Z")
    private Instant timestamp;

    @Schema(description = "Status HTTP do erro", example = "400")
    private Integer status;

    @Schema(description = "Descrição do erro", example = "Bad Request")
    private String error;

    @Schema(description = "Caminho da requisição que gerou o erro", example = "/api/resource")
    private String path;

    public CustomError(Instant timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
