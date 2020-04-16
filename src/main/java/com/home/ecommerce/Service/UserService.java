package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Exception.UserAlreadyExistsException;
import com.home.ecommerce.Repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser (User user){
        User user1 = userRepository.findByEmail(user.getEmail());
        if(user1 != null) throw new UserAlreadyExistsException("User with email already exists");
        if(user.getRole()=="user" ) user.setRoles();
        return userRepository.save(user);
    }
}
