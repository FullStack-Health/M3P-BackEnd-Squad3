package com.labinc.Lab.Inc.dtos;

import org.springframework.validation.FieldError;

public class FieldMessage {

    private String fieldName;
    private String message;

    public FieldMessage() {
    }

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public FieldMessage(FieldError fieldError) {
        this.fieldName = fieldError.getField();
        this.message = fieldError.getDefaultMessage();
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
