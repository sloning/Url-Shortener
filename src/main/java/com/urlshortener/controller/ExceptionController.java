package com.urlshortener.controller;

import com.urlshortener.dto.response.ErrorResponse;
import com.urlshortener.exception.EntityAlreadyExistsException;
import com.urlshortener.exception.EntityNotDeletedException;
import com.urlshortener.exception.EntityNotFoundException;
import com.urlshortener.exception.EntityNotSavedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(Exception ex) {
        return ErrorResponse.badRequest(ex, 1);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ErrorResponse handleEntityAlreadyExistsException(Exception ex) {
        return ErrorResponse.duplicateEntity(ex, 2);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotSavedException.class)
    public ErrorResponse handleEntityNotSavedException(Exception ex) {
        return ErrorResponse.badRequest(ex, 3);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotDeletedException.class)
    public ErrorResponse handleEntityNotDeletedException(Exception ex) {
        return ErrorResponse.badRequest(ex, 4);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(Exception ex) {
        return ErrorResponse.validationException(ex, 5);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse handleBadCredentialsException(Exception ex) {
        return ErrorResponse.wrongCredentials(ex, 6);
    }
}
