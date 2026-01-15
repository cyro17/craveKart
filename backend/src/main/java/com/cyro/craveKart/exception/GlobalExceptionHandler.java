package com.cyro.cravekart.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<String> handleUserNotFound(UsernameNotFoundException ex) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(
      DataIntegrityViolationException ex) {

    Map<String, String> error = new HashMap<>();
    String message = ex.getRootCause() != null ?
        ex.getRootCause().getMessage() : ex.getMessage();

    if (message != null && message.contains("users.username")) {
      error.put("username", "Username already exists");
    } else if (message != null && message.contains("users.email")) {
      error.put("email", "Email already exists");
    } else {
      error.put("error", "Database error");
    }

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }
}
