package com.cyro.cravekart.exception;

import com.cyro.cravekart.exception.error.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiError> handleResourceNotFoundException(
      ResourceNotFoundException ex, HttpServletRequest request) {

    ApiError error =
        new ApiError(
            HttpStatus.NOT_FOUND.value(),
            "NOT_FOUND",
            ex.getMessage(),
            request.getRequestURI(),
            LocalDateTime.now());

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiError> handleBusinessException(
      BusinessException ex, HttpServletRequest request) {

    ApiError error =
        new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            "BUSINESS_ERROR",
            ex.getMessage(),
            request.getRequestURI(),
            LocalDateTime.now());

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ApiError> handleForbidden(
      ForbiddenException ex, HttpServletRequest request) {

    ApiError error =
        new ApiError(
            HttpStatus.FORBIDDEN.value(),
            "FORBIDDEN",
            ex.getMessage(),
            request.getRequestURI(),
            LocalDateTime.now());

    return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiError> handleBadCredentialsException(
      BadCredentialsException ex, HttpServletRequest request) {
    ApiError error =
        new ApiError(
            HttpStatus.UNAUTHORIZED.value(),
            "UNAUTHORIZED",
            "Invalid Username or Password",
            request.getRequestURI(),
            LocalDateTime.now());

    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {

    ApiError error =
        new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_ERROR",
            "Something went wrong",
            request.getRequestURI(),
            LocalDateTime.now());

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(
      MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  //

  //  @ExceptionHandler(UsernameNotFoundException.class)
  //  public ResponseEntity<String> handleUserNotFound(UsernameNotFoundException ex) {
  //    return ResponseEntity
  //        .status(HttpStatus.NOT_FOUND)
  //        .body(ex.getMessage());
  //  }
  //
  //  @ExceptionHandler(DataIntegrityViolationException.class)
  //  public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(
  //      DataIntegrityViolationException ex) {
  //
  //    Map<String, String> error = new HashMap<>();
  //    String message = ex.getRootCause() != null ?
  //        ex.getRootCause().getMessage() : ex.getMessage();
  //
  //    if (message != null && message.contains("users.username")) {
  //      error.put("username", "Username already exists");
  //    } else if (message != null && message.contains("users.email")) {
  //      error.put("email", "Email already exists");
  //    } else {
  //      error.put("error", "Database error");
  //    }
  //
  //    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  //  }
}
