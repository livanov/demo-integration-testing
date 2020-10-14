package com.livanov.demo.integrationtesting.infrastructure.web;

import com.livanov.demo.integrationtesting.domain.IpDetailsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.Collections.singletonMap;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(IpDetailsNotFoundException.class)
    public ResponseEntity<?> onNotFound(IpDetailsNotFoundException ex) {
        return new ResponseEntity<>(
                singletonMap("error", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
