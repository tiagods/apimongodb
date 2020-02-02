package com.tiagods.apimongodb.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ClienteJaExisteException extends RuntimeException{
    public ClienteJaExisteException(String message) {
        super(message);
    };
}
