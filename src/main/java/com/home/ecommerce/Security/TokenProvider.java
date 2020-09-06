package com.home.ecommerce.Security;

import com.home.ecommerce.Domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenProvider {

    public String generateToken(Authentication authentication){
        UserDetails user = (UserDetails)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime()+30_00000);

        String email = user.getUsername();
        Map<String,Object> claims = new HashMap<>();

        claims.put("email",email);

        String jwt = Jwts.builder().setSubject(email).
                addClaims(claims).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(expiryDate).
                signWith(SignatureAlgorithm.HS512,"SECRET_KEY").
                compact();
        return jwt;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey("SECRET_KEY").parseClaimsJws(token);
            System.out.println("token valid");

            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("token invalid");
        return false;
    }

    public String getEmailFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey("SECRET_KEY").parseClaimsJws(token).getBody();
        String email = claims.getSubject();
        return email;
    }
}
