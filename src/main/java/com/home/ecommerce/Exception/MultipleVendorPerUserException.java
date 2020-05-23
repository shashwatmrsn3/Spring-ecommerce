package com.home.ecommerce.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MultipleVendorPerUserException  extends RuntimeException{

    public MultipleVendorPerUserException(String message) {
        super(message);
    }
}
