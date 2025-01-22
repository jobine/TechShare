package com.example.account.management.exception;

import com.example.account.management.common.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseBody<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ResponseBody<String> body = new ResponseBody<>(LocalDateTime.now(), ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseBody<String>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ResponseBody<String> body = new ResponseBody<>(LocalDateTime.now(), ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBody<String>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream().map(error -> String.format("'%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining("; "));
        ResponseBody<String> body = new ResponseBody<>(LocalDateTime.now(), message);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseBody<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseBody<String> body = new ResponseBody<>(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseBody<String>> handleGenericException(RuntimeException ex) {
        ResponseBody<String> body = new ResponseBody<>(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
