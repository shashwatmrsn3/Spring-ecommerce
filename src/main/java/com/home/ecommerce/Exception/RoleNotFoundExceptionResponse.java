package com.home.ecommerce.Exception;

public class RoleNotFoundExceptionResponse {

    private String message;

    public RoleNotFoundExceptionResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
