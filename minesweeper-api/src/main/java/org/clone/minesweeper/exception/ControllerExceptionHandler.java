package org.clone.minesweeper.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.clone.minesweeper.model.web.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {


    @ApiResponses(value = {
            @ApiResponse(
                    description = "Fallback code when no http status is present.",
                    responseCode="500"),
            @ApiResponse(
                    description = "Response Code when a input validation fails.",
                    responseCode = "400"),
            @ApiResponse(
                    description = "Response Code when game requested is not found",
                    responseCode = "404"),
            @ApiResponse(
                    description = "Response Code when Game is over" +
                                  " and not further operations are allowed.",
                    responseCode = "422")})
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiErrorResponse> apiExceptionHandler(ApiException e) {
        HttpStatus httpStatus = e.getStatus();
        if(httpStatus != null) {
            return ResponseEntity.status(e.getStatus()).body(new ApiErrorResponse(e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse badRequestHandler(IllegalArgumentException e) {
        return new ApiErrorResponse(e.getMessage());
    }
}
