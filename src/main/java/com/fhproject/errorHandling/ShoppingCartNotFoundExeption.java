package com.fhproject.errorHandling;

public class ShoppingCartNotFoundExeption extends Throwable{
    public ShoppingCartNotFoundExeption(String message){
        super(message);
    }
}
