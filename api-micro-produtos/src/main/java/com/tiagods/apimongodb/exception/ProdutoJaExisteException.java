package com.tiagods.apimongodb.exception;

public class ProdutoJaExisteException extends RuntimeException {
    public ProdutoJaExisteException(String message){
        super(message);
    }
}
