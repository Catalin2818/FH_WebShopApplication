package com.fhproject.shoppingCart;

public class ShoppingCartNotFoundExeption extends Throwable{
    public ShoppingCartNotFoundExeption(String message){
        super(message);
    }
}
