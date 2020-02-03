package com.tiagods.apimongodb.exception;

import lombok.Data;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProdutoJaExisteException.class)
    public void produtoJaExisteException(HttpServletResponse response) throws Exception{
        response.sendError(HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public void produtoNotFoundException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
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
    public class ResultError{
        private Date timestamp;
        private int status;
        private List<String> errors;
    }
}
