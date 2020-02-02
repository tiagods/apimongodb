package com.tiagods.apimongodb.secundary;

public class ProdutoJaExisteException extends RuntimeException {
    public ProdutoJaExisteException(String message){
        super(message);
    }
}
