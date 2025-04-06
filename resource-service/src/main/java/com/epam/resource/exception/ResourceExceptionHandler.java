package com.epam.resource.exception;

import com.epam.resource.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorDTO> handleResponseStatusException(ResponseStatusException e) {
    HttpStatusCode statusCode = e.getStatusCode();
    return new ResponseEntity<>(new ErrorDTO(e.getReason(), statusCode.value()), statusCode);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    return new ResponseEntity<>(
        new ErrorDTO(
            "Invalid value '%s' for ID. Must be a positive integer".formatted(e.getValue()),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({Exception.class, RuntimeException.class})
  public ResponseEntity<ErrorDTO> handleException(Throwable t) {
    return new ResponseEntity<>(
        new ErrorDTO("An error occurred on the server", HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
