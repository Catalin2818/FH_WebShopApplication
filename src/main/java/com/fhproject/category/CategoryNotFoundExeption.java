package com.fhproject.category;

public class CategoryNotFoundExeption extends Throwable{
    public CategoryNotFoundExeption(String message){
        super (message);
    }
}
