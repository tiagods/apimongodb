package com.tiagods.apimongodb.exception;

public class ClienteProdutoNotFoundException extends RuntimeException {
    public ClienteProdutoNotFoundException(String message) {
        super(message);
    }
}
