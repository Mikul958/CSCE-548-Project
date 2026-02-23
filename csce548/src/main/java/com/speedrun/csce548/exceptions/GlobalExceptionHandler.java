package com.speedrun.csce548.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Return 400 Bad Request when IllegalArgumentException is thrown
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

        ErrorResponse badRequestError = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<ErrorResponse>(badRequestError, HttpStatus.BAD_REQUEST);
    }

    // Return 404 Not Found when EntryNotFoundException is thrown (i.e. database value with a given ID does not exist)
    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntryNotFound(EntryNotFoundException ex) {

        ErrorResponse notFoundError = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<ErrorResponse>(notFoundError, HttpStatus.NOT_FOUND);
    }

    // Return 500 InternalServerError when any uncaught exception is thrown
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleRuntime(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
