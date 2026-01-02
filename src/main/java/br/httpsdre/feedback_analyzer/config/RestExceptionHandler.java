package br.httpsdre.feedback_analyzer.config;

import br.httpsdre.feedback_analyzer.dtos.ApiError;
import br.httpsdre.feedback_analyzer.exceptions.NotFoundException;
import br.httpsdre.feedback_analyzer.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> notFoundError(NotFoundException e) {
    ApiError apiError = ApiError.builder()
            .timestamp(LocalDateTime.now())
            .code(HttpStatus.NOT_FOUND.value())
            .status(HttpStatus.NOT_FOUND.name())
            .errors(List.of(e.getMessage()))
            .build();
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ApiError> validationError(ValidationException e) {
    ApiError apiError = ApiError.builder()
            .timestamp(LocalDateTime.now())
            .code(HttpStatus.BAD_REQUEST.value())
            .status(HttpStatus.BAD_REQUEST.name())
            .errors(List.of(e.getMessage()))
            .build();
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> genericException(Exception e) {
    ApiError apiError = ApiError.builder()
            .timestamp(LocalDateTime.now())
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .errors(List.of(e.getMessage()))
            .build();
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
