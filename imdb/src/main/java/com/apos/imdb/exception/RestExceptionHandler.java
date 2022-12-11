package com.apos.imdb.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String IMDB_PARSING_ERROR = "Imdb Parsing Error";

  @ExceptionHandler(value = { ImdbParsingException.class })
  public ResponseEntity<Object> handleConflict(
      ImdbParsingException ex, WebRequest request) {
    String body = IMDB_PARSING_ERROR;
    return handleExceptionInternal(ex, body,
        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

}
