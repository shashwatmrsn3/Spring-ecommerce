package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Service.UserService;
import com.home.ecommerce.Service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ValidationErrorService validationErrorService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody  User user, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.validationErrorService(result);
        if (errorMap != null) return  errorMap;
        User user1 = userService.registerUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @GetMapping("/dashboard")
    public String welcome(){
        return "welcome";
    }
}
