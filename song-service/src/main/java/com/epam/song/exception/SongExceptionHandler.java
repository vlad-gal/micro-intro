package com.epam.song.exception;

import com.epam.song.dto.ErrorDTO;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class SongExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    Map<String, String> details =
        e.getBindingResult().getAllErrors().stream()
            .map(error -> Map.entry(((FieldError) error).getField(), error.getDefaultMessage()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    return new ResponseEntity<>(
        new ErrorDTO()
            .setErrorMessage("Validation error")
            .setDetails(details)
            .setErrorCode(HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorDTO> handleResponseStatusException(ResponseStatusException e) {
    HttpStatusCode statusCode = e.getStatusCode();
    return new ResponseEntity<>(
        new ErrorDTO().setErrorMessage(e.getReason()).setErrorCode(statusCode.value()), statusCode);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    return new ResponseEntity<>(
        new ErrorDTO()
            .setErrorMessage(
                "Invalid value '%s' for ID. Must be a positive integer".formatted(e.getValue()))
            .setErrorCode(HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({Exception.class, RuntimeException.class})
  public ResponseEntity<ErrorDTO> handleException(Throwable t) {
    return new ResponseEntity<>(
        new ErrorDTO()
            .setErrorMessage("An error occurred on the server")
            .setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
