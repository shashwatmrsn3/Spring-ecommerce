package com.home.ecommerce.Exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message){
        super(message);
    }
}
