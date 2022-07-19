package ru.yastrebov.agreement.exception.handler;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class WrongIdExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> otherHandleException(RuntimeException exception, WebRequest webRequest) {

        String bodyOfResponse = "Record with this Id not found";

        return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }
}
