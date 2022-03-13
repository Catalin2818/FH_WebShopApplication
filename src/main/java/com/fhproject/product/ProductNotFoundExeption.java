package com.fhproject.product;

public class ProductNotFoundExeption extends Throwable{
    public ProductNotFoundExeption(String message){
        super (message);
    }
}
