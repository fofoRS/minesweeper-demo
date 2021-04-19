package org.clone.minesweeper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Map<String,String>> apiExceptionHandler(ApiException e) {
        HttpStatus httpStatus = e.getStatus();
        if(httpStatus != null) {
            return ResponseEntity.status(e.getStatus()).body(Map.of("message",e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> badRequestHandler(IllegalArgumentException e) {
        return Map.of("message",e.getMessage());
    }
}
