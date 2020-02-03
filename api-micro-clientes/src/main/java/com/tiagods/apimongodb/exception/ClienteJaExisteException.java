package com.tiagods.apimongodb.exception;


public class ClienteJaExisteException extends RuntimeException{
    public ClienteJaExisteException(String message) {
        super(message);
    };
}
