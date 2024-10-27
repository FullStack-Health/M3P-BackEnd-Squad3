package com.labinc.Lab.Inc.controllers.handlers;

import com.labinc.Lab.Inc.entities.AllowedRoles;
import com.labinc.Lab.Inc.dtos.FieldMessage;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldMessage>> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getFieldErrors();
        List<FieldMessage> responseList = fieldErrors.stream().map(FieldMessage::new).toList();
        return ResponseEntity.badRequest().body(responseList);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<FieldMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        FieldMessage response = new FieldMessage();
        try {
            response.setFieldName(exception.getCause().toString().split("\"")[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            try {
                response.setFieldName(exception.getCause().toString().split("\"")[1]);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        } catch (NullPointerException ignored) {
        }
        response.setMessage(exception.getLocalizedMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FieldMessage> handleConstraintViolation(ConstraintViolationException exception) {
        FieldMessage response = new FieldMessage();
        response.setFieldName(exception.getConstraintViolations().toString().split("', propertyPath=|, rootBeanClass=")[1]);
        response.setMessage(exception.getConstraintViolations().toString().split("interpolatedMessage='|', propertyPath=")[1]);
        return ResponseEntity.badRequest().body(response);
    }

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

    @ExceptionHandler(EnumConstantNotPresentException.class)
    public ResponseEntity<FieldMessage> handleEnumConstantNotPresent(EnumConstantNotPresentException exception) {
        FieldMessage error = new FieldMessage();
        if (exception.enumType() == AllowedRoles.class) {
            error.setFieldName("roleName");
        }
        if (Objects.equals(exception.constantName(), "PATIENT")) {
            error.setMessage("Perfil do usuário não pode ser PATIENT");
        }
        return ResponseEntity.badRequest().body(error);
    }

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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<FieldMessage> handleBadCredentials(BadCredentialsException exception) {
        FieldMessage error = new FieldMessage();
        error.setFieldName("password");
        error.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<FieldMessage> handleAccessDenied(AccessDeniedException exception) {
        FieldMessage error = new FieldMessage();
        error.setFieldName("roleName");
        error.setMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
