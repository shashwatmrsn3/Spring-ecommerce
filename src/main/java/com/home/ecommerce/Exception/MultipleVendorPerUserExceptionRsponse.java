package com.home.ecommerce.Exception;

public class MultipleVendorPerUserExceptionRsponse {
    private String message;

    public MultipleVendorPerUserExceptionRsponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
