package com.labinc.Lab.Inc.services.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Exceção lançada quando ocorre um erro de banco de dados")
public class DataBaseException  extends RuntimeException{

    public DataBaseException(String message){
        super(message);
    }
}
