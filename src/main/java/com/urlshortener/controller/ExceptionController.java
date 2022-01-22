package com.urlshortener.controller;

import com.urlshortener.dto.ErrorResponse;
import com.urlshortener.exception.EntityAlreadyExistsException;
import com.urlshortener.exception.EntityNotDeletedException;
import com.urlshortener.exception.EntityNotFoundException;
import com.urlshortener.exception.EntityNotSavedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 1, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistsException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 2, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotSavedException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotSavedException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 3, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotDeletedException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotDeletedException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 4, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 5, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(400, 6, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
