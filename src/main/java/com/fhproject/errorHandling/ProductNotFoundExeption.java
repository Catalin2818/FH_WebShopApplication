package com.fhproject.errorHandling;

public class ProductNotFoundExeption extends Throwable{
    public ProductNotFoundExeption(String message){
        super (message);
    }
}
