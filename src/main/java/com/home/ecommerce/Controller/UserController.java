package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Security.LoginRequest;
import com.home.ecommerce.Security.LoginSuccessResponse;
import com.home.ecommerce.Security.TokenProvider;
import com.home.ecommerce.Service.MyUserDetailsService;
import com.home.ecommerce.Service.UserService;
import com.home.ecommerce.Service.ValidationErrorService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody  User user, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.validationErrorService(result);
        if (errorMap != null) return  errorMap;
        User user1 = userService.registerUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,BindingResult result) throws Exception{

        ResponseEntity<?> errorMap = validationErrorService.validationErrorService(result);

        if(errorMap != null) return errorMap;
        String email = request.getEmail();
        String password = request.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        User user  = userService.loadUserByUsername(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return new ResponseEntity<LoginSuccessResponse>(new LoginSuccessResponse(true,jwt),HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public String welcome(){
        return "welcome";
    }


}
