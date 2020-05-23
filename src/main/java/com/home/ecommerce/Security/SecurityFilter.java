package com.home.ecommerce.Security;

import com.home.ecommerce.Domain.MyUserDetails;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Service.MyUserDetailsService;
import com.home.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("In filter entry point");
        try{
            String token = getTokenFromHeader(httpServletRequest);
            if(tokenProvider.validateToken(token)&&StringUtils.hasText(token)){
                System.out.println(tokenProvider.getEmailFromJwt(token));

                UserDetails userDetails = userDetailsService.loadUserByUsername(tokenProvider.getEmailFromJwt(token));

                System.out.println("userdetails:"+userDetails);

                UsernamePasswordAuthenticationToken authentication  = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                Object principal = SecurityContextHolder.getContext(). getAuthentication(). getPrincipal();

                String username = ((UserDetails)principal). getUsername();
                username = principal. toString();
                Collection<? extends GrantedAuthority> authorities = ((UserDetails)principal).getAuthorities();
                System.out.println("authorities:"+authorities);

            }
        }catch(Exception e){
            System.out.println(e);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

    private String getTokenFromHeader(HttpServletRequest request){
        System.out.println("In get token method");
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token)&&token.startsWith("Bearer ")){
            System.out.println("token:"+token.substring(7,token.length()));
            return token.substring(7,token.length());
        }
        return null;
    }
}
