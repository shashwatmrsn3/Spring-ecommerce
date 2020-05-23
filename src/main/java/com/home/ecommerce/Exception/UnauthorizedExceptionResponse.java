package com.home.ecommerce.Exception;

public class UnauthorizedExceptionResponse {

    private String message;

    public UnauthorizedExceptionResponse(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
