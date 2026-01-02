package br.httpsdre.feedback_analyzer.exceptions;

public class ValidationException extends RuntimeException {
  public ValidationException() {
    super("Request validation error.");
  }
  
  public ValidationException(String message) {
    super(message);
  }
}
