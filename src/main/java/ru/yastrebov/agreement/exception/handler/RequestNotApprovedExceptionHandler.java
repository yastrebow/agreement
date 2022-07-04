package ru.yastrebov.agreement.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RequestNotApprovedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = MethodNotAllowedException.class)
    protected ResponseEntity<Object> handleException(RuntimeException exception, WebRequest webRequest) {

        String bodyOfResponse = "The request has status 'not approved'";

        return handleExceptionInternal(exception, bodyOfResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, webRequest);
    }
}
