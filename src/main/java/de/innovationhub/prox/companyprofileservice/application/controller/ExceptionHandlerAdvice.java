package de.innovationhub.prox.companyprofileservice.application.controller;

import de.innovationhub.prox.companyprofileservice.application.exception.ApiError;
import de.innovationhub.prox.companyprofileservice.application.exception.core.CustomEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

  Logger logger = LoggerFactory.getLogger(this.getClass().getName());

  @ExceptionHandler({CustomEntityNotFoundException.class})
  public ResponseEntity<ApiError> entityNotFoundExceptionHandler(CustomEntityNotFoundException e) {
    logger.error("Entity not found", e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError(HttpStatus.NOT_FOUND.value(), e.getType(), e.getMessage()));
  }

  @ExceptionHandler({HttpMessageNotReadableException.class})
  public ResponseEntity<ApiError> invalidFormatExceptionHandler(HttpMessageNotReadableException e) {
    logger.error("HTTP Message not readable", e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "HTTP Request error", e.getMessage()));
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<ApiError> entityNotFoundExceptionHandler(IllegalArgumentException e) {
    logger.error("Illegal Argument", e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ApiError(HttpStatus.BAD_REQUEST.value(), "Illegal Argument", e.getMessage()));
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ApiError> exceptionHandler(Exception e) {
    logger.error("Exception", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", e.getMessage()));
  }

}
