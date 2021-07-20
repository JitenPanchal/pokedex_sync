package com.truelayer.translation.interceptors;

import com.truelayer.translation.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalInterceptor {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
