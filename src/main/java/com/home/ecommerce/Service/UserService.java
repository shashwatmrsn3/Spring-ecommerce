package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.Role;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Exception.RoleNotFoundException;
import com.home.ecommerce.Exception.UserAlreadyExistsException;
import com.home.ecommerce.Repositroy.RoleRepository;
import com.home.ecommerce.Repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser (User user){
        User user1 = userRepository.findByEmail(user.getEmail());
        if(user1 != null) throw new UserAlreadyExistsException("User with email already exists");
        user.setActive(true);
        String roleName = user.getRole();
        Role role = roleRepository.findByRole(roleName);
        if(role==null){
            throw new RoleNotFoundException("Role doesnt exist");
        }

        user.setRole(user.getRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User loadUserByUsername(String username){
        return userRepository.findByEmail(username);
    }
}
