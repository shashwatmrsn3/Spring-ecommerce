package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.Cart;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Repositroy.CartReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartReposiroty cartReposiroty;

    public Cart findCartByUser(User user){
        return cartReposiroty.findByUser(user);
    }

    public Cart saveCart(Cart cart){
        return cartReposiroty.save(cart);
    }
}
