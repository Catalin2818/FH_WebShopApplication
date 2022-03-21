package com.fhproject.errorHandling;

public class UserNotFoundExeption extends Throwable {
    public UserNotFoundExeption(String message) {
        super(message);
    }
}
