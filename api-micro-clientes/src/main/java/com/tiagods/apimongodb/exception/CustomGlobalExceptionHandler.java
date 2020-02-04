package com.tiagods.apimongodb.exception;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClienteJaExisteException.class)
    public ResponseEntity<?> clienteJaExisteException(ClienteJaExisteException ex,HttpServletResponse response){
        return buildResponseEntity(HttpStatus.FORBIDDEN,Arrays.asList(ex.getMessage()));
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<?> clientNotFoundException(ClienteNotFoundException ex,HttpServletResponse response) {
        return buildResponseEntity(HttpStatus.NOT_FOUND,Arrays.asList(ex.getMessage()));
    }
    private ResponseEntity<?> buildResponseEntity(HttpStatus status, List<String> erros) {
        ResultError resultError = new ResultError(new Date(),status.value(), erros);
        return new ResponseEntity<>(resultError, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ResultError error = new ResultError();
        error.setTimestamp(new Date());
        error.setStatus(status.value());
        error.setErrors(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(error, headers, status);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ResultError{
        private Date timestamp;
        private int status;
        private List<String> errors;
    }
}
