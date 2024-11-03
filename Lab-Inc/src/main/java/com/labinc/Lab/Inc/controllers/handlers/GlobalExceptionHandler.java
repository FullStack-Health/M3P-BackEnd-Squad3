package com.labinc.Lab.Inc.controllers.handlers;

import com.labinc.Lab.Inc.dtos.CustomError;
import com.labinc.Lab.Inc.dtos.FieldMessage;
import com.labinc.Lab.Inc.dtos.ValidationError;
import com.labinc.Lab.Inc.services.exceptions.ResourceAlreadyExistsException;
import com.labinc.Lab.Inc.services.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;

@RestControllerAdvice
@Tag(name = "Global Exception Handler", description = "Tratamento global de exceções para a API")
public class GlobalExceptionHandler {

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
//        return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
//    }

    @Operation(summary = "Trata HttpMessageNotReadableException", description = "Lida com entradas inválidas ou campos vazios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro na leitura da mensagem HTTP")
    })
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String customMessage = "Input error: invalid value or empty field.";
        return ResponseEntity.badRequest().body(customMessage);
    }


    //    Exception para Conflicts
    @Operation(summary = "Trata ResourceAlreadyExistsException", description = "Lida com recursos já existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Recurso já existe")
    })
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> resourceAlreadyExists(ResourceAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    //    Exception para Validations
    @Operation(summary = "Trata MethodArgumentNotValidException", description = "Lida com erros de validação nos argumentos dos métodos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // também pode utilizar UNPROCESSABLE_ENTITY - Retorna erro 422
        ValidationError error = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI());
        for (FieldError f: e.getBindingResult().getFieldErrors()){
            error.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }

    //    Exception para recurso não encontrado
    @Operation(summary = "Trata ResourceNotFoundException", description = "Lida com recursos não encontrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @Operation(summary = "Trata EntityNotFoundException", description = "Lida com entidades não encontradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada")
    })
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException e) {
        if (e.getMessage().equals("Patient not found")) {
            return new ResponseEntity<>("The patient with the given ID was not found in database.", HttpStatus.NOT_FOUND);
        }
        if (e.getMessage().equals("Appointment not found")) {
            return new ResponseEntity<>("The appointment with the given ID was not found in database.", HttpStatus.NOT_FOUND);
        }
        if (e.getMessage().equals("Exam not found")) {
            return new ResponseEntity<>("The exam with the given ID was not found in database.", HttpStatus.NOT_FOUND);
        }
        if (e.getMessage().equals("No patients found")) {
            return new ResponseEntity<>("Your search returned no results.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Trata IllegalStateException", description = "Lida com exceções de estado ilegal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Estado ilegal detectado")
    })
    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<String> handleStateExceptions(IllegalStateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @Operation(summary = "Trata BadRequestException", description = "Lida com requisições inválidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Trata ConflictException", description = "Lida com conflitos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Conflito detectado")
    })
    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<String> handleConflictException(ConflictException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @Operation(summary = "Trata exceções gerais", description = "Lida com exceções não capturadas especificamente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleGeneralException(Exception e) {
        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "Trata IOException", description = "Lida com exceções de IO")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de entrada/saída")
    })
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @Operation(summary = "Trata ConstraintViolationException", description = "Lida com violações de restrições")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Violação de restrição detectada")
    })
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FieldMessage> handleConstraintViolation(ConstraintViolationException exception) {
        FieldMessage response = new FieldMessage();
        response.setFieldName(exception.getConstraintViolations().toString().split("', propertyPath=|, rootBeanClass=")[1]);
        response.setMessage(exception.getConstraintViolations().toString().split("interpolatedMessage='|', propertyPath=")[1]);
        return ResponseEntity.badRequest().body(response);
    }

    @Operation(summary = "Trata DuplicateKeyException", description = "Lida com chaves duplicadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Chave duplicada detectada")
    })
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<FieldMessage> handleDuplicateKey(DuplicateKeyException exception) {
        FieldMessage error = new FieldMessage();
        if (exception.getMessage().contains("E-mail já cadastrado: ")) {
            error.setFieldName("email");
        } else if (exception.getMessage().contains("CPF já cadastrado: ")) {
            error.setFieldName("cpf");
        }
        error.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @Operation(summary = "Trata UsernameNotFoundException", description = "Lida com usuário não encontrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Nome de usuário não encontrado"),
            @ApiResponse(responseCode = "404", description = "E-mail não encontrado")
    })
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<FieldMessage> handleUsernameNotFound(UsernameNotFoundException exception) {
        FieldMessage error = new FieldMessage();
        error.setMessage(exception.getMessage());
        error.setFieldName("email");
        if (exception.getMessage().contains("Nome de usuário não encontrado: ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } else if (exception.getMessage().contains("E-mail não encontrado: ")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return null;
    }

    @Operation(summary = "Trata BadCredentialsException", description = "Lida com credenciais inválidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas fornecidas")
    })
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<FieldMessage> handleBadCredentials(BadCredentialsException exception) {
        FieldMessage error = new FieldMessage();
        error.setFieldName("password");
        error.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @Operation(summary = "Trata AccessDeniedException", description = "Lida com exceções de acesso negado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "Acesso negado")
    })
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<FieldMessage> handleAccessDenied(AccessDeniedException exception) {
        FieldMessage error = new FieldMessage();
        error.setFieldName("roleName");
        error.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
