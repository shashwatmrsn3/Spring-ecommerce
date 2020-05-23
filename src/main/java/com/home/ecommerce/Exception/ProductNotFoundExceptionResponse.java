package com.home.ecommerce.Exception;

public class ProductNotFoundExceptionResponse {

    private String message;

    public ProductNotFoundExceptionResponse(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
