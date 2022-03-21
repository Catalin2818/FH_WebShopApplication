package com.fhproject.errorHandling;

public class CategoryNotFoundExeption extends Throwable{
    public CategoryNotFoundExeption(String message){
        super (message);
    }
}
