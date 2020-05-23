package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PrincipalService {

    @Autowired
    private UserService userService;

    public String getCurrentPrincipaUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        return  username;
    }

    public User getCurrentPrincipal(){
        String username = getCurrentPrincipaUsername();
        User user = userService.loadUserByUsername(username);
        return user;
    }
}
