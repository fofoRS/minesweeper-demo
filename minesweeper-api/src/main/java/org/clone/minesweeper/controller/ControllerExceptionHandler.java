package org.clone.minesweeper.controller;

import org.clone.minesweeper.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<String> illegalArgumentExceptionHandler(ApiException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
