package com.home.ecommerce.Exception;

public class UserAlreadyExistsExceptionResponse {

    private String message;

    public UserAlreadyExistsExceptionResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
