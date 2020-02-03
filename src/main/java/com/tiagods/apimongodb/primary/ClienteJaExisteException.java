package com.tiagods.apimongodb.primary;


public class ClienteJaExisteException extends RuntimeException{
    public ClienteJaExisteException(String message) {
        super(message);
    };
}
