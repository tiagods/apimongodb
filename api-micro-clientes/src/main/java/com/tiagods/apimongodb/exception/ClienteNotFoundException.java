package com.tiagods.apimongodb.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String message){
        super(message);
    }
}
