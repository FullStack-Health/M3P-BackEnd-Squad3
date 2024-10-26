package com.labinc.Lab.Inc.controllers.handlers;

import com.labinc.Lab.Inc.dtos.CustomError;
import com.labinc.Lab.Inc.dtos.ValidationError;
import com.labinc.Lab.Inc.services.exceptions.ResourceAlreadyExistsException;
import com.labinc.Lab.Inc.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
//        return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
//    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String customMessage = "Input error: invalid value or empty field.";
        return ResponseEntity.badRequest().body(customMessage);
    }


    //    Exception para Conflicts
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> resourceAlreadyExists(ResourceAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    //    Exception para Validations
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
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

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

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<String> handleStateExceptions(IllegalStateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<String> handleConflictException(ConflictException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleGeneralException(Exception e) {
        return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

