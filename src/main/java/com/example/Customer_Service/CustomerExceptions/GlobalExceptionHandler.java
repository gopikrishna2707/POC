package com.example.Customer_Service.CustomerExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *
     * @param exception used for if duplicate email is there
     * @return error response with status
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> duplicateEmailHandling(CustomException exception){

        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     *
     * @param ex used to get message from exception
     * @return ex response with status
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handlingDeleteCustomer(NotFoundException ex){
        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     *
     * @param ex to get message from exception
     * @return ex response with status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleArgumentException(MethodArgumentNotValidException ex){

        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ":"+ error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(messages);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleUnavailableEx(ServiceUnavailableException ex){

        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response,HttpStatus.SERVICE_UNAVAILABLE);
    }
}
