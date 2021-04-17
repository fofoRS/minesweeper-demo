package org.clone.minesweeper.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
