package com.home.ecommerce.Security;

import com.home.ecommerce.Domain.MyUserDetails;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = getTokenFromHeader(httpServletRequest);
            if(tokenProvider.validateToken(token)&&StringUtils.hasText(token)){
                User user = userService.loadUserByUsername(tokenProvider.getEmailFromJwt(token));
                UserDetails userDetails = new MyUserDetails(user);
            }
        }catch(Exception e){

        }
    }

    private String getTokenFromHeader(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token)&&token.startsWith("Bearer ")){
            return token.substring(7,token.length());
        }
        return null;
    }
}
