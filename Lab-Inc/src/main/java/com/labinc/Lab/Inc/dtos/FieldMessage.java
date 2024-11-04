package com.labinc.Lab.Inc.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informações sobre os campos com erro")
public class FieldMessage {

    @Schema(description = "Nome do campo com erro", example = "email")
    private String fieldName;

    @Schema(description = "Mensagem de erro associada ao campo", example = "O email é obrigatório")
    private String message;

    public FieldMessage() {
    }

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
