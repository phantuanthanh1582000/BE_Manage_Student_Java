package com.example.studentmanagement.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.studentmanagement.dto.ResponseWrapper;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleRuntimeException(RuntimeException ex) {
        ResponseWrapper<Object> response = new ResponseWrapper<>(0, ex.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }
}
