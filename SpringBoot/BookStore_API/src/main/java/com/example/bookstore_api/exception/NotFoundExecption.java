package com.example.bookstore_api.exception;

public class NotFoundExecption extends RuntimeException {
    public NotFoundExecption(String message){
        super(message);
    }
}
