package com.example.account.management.exception;

import com.example.account.management.common.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseBody<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
//        Map<String, Object> body = new HashMap<>();
//
//        body.put("timestamp", LocalDateTime.now());
//        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
//        body.put("message", ex.getMessage());
        ResponseBody<String> body = new ResponseBody<>(LocalDateTime.now(), ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseBody<String>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
//        Map<String, Object> body = new HashMap<>();
//
//        body.put("timestamp", LocalDateTime.now());
//        body.put("error", HttpStatus.CONFLICT.getReasonPhrase());
//        body.put("message", ex.getMessage());

        ResponseBody<String> body = new ResponseBody<>(LocalDateTime.now(), ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBody<String>> handleValidationException(MethodArgumentNotValidException ex) {
//        Map<String, String> body = new HashMap<>();
//
        String message = ex.getBindingResult().getFieldErrors().stream().map(error -> String.format("'%s': %s", error.getField(), error.getDefaultMessage())).collect(Collectors.joining("; "));
        ResponseBody<String> body = new ResponseBody<>(LocalDateTime.now(), message);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseBody<String>> handleGenericException(RuntimeException ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
//        body.put("message", ex.getMessage());
        ResponseBody<String> body = new ResponseBody<>(LocalDateTime.now(), ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
