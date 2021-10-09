package com.gocity.leisure.pass.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GoCityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleInternalServer(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Unknown server error";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value
            = {GoCityProductNotFoundException.class})
    protected ResponseEntity<Object> handleProductNotFound(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Product Not found";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = {GoCityCategoryNotFoundException.class})
    protected ResponseEntity<Object> handleCategoryNotFound(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Category Not found";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}

