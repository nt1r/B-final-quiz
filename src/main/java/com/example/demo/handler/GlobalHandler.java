package com.example.demo.handler;

import com.example.demo.dto.ErrorResponse;
import com.example.demo.exception.TraineeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidationException(MethodArgumentNotValidException exception) {
        Map<String, String> details = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ErrorResponse.builder()
                .message("Invalid values.")
                .details(details)
                .build();
    }

    @ExceptionHandler(TraineeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTraineeNotFoundException(TraineeNotFoundException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }
}
