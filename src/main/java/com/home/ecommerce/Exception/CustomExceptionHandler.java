package com.home.ecommerce.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler{
    @ExceptionHandler
    public ResponseEntity<?>  handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        UserAlreadyExistsExceptionResponse response = new UserAlreadyExistsExceptionResponse(ex.getMessage());
        return new ResponseEntity<UserAlreadyExistsExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleRoleNotFoundException(RoleNotFoundException ex){
        RoleNotFoundExceptionResponse response = new RoleNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
