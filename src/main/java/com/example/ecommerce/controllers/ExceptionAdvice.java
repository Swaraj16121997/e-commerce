package com.example.ecommerce.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

// this is a global exception handler basically used for hiding the by-default detailed error stack trace from the client while sending back the error response
@ControllerAdvice
public class ExceptionAdvice {
    @org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class})
    public ResponseEntity<String> handleException(Exception exception){
        return new ResponseEntity<>("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
