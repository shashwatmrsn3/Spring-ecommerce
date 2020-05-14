package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.MyUserDetails;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if(user==null) System.out.println("username doesnt exist");
        MyUserDetails userDetails = new MyUserDetails(user);
        return userDetails;
    }
}
