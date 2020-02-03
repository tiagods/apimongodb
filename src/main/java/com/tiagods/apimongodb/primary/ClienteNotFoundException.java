package com.tiagods.apimongodb.primary;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String message){
        super(message);
    }
}
