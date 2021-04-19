package org.clone.minesweeper.exception;

import org.clone.minesweeper.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<String> apiExceptionHandler(ApiException e) {
        HttpStatus httpStatus = e.getStatus();
        if(httpStatus != null) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void generalExceptionHandler() { }
}
