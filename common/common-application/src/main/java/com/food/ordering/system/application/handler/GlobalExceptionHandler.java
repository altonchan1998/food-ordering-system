package com.food.ordering.system.application.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .message("Unexpected error")
                .build();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ValidationException validationException) {
//        if (validationException instanceof ConstraintViolationException) {
//            String violations = extractViolationsFromException((ConstraintViolationException) validationException);
//            log.error(violations, validationException);
//            return ErrorDTO.builder()
//                    .code(HttpStatus.BAD_REQUEST.toString())
//                    .message(violations)
//                    .build();
//        }
        String exceptionMessage = validationException.getMessage();
        log.error(exceptionMessage, validationException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .message(exceptionMessage)
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ConstraintViolationException constraintViolationException) {
            String violations = extractViolationsFromException(constraintViolationException);
            log.error(violations, constraintViolationException);
            return ErrorDTO.builder()
                    .code(HttpStatus.BAD_REQUEST.toString())
                    .message(violations)
                    .build();
    }

    private String extractViolationsFromException(ConstraintViolationException constraintViolationException) {
        return constraintViolationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
    }
}
