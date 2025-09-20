package com.shiv.electronic.store.exceptions;

public class BadApiRequestException extends RuntimeException{

    public BadApiRequestException() {
        super("This is Bad Api");
    }

    public BadApiRequestException(String message){
        super(message);
    }
}
