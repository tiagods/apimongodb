package com.tiagods.apimongodb.primary.exception;


public class ClienteJaExisteException extends RuntimeException{
    public ClienteJaExisteException(String message) {
        super(message);
    };
}
