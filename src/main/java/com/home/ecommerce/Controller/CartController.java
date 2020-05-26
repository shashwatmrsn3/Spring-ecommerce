package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.Cart;
import com.home.ecommerce.Domain.CartItem;
import com.home.ecommerce.Domain.Product;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Exception.ProductNotFoundException;
import com.home.ecommerce.Service.CartService;
import com.home.ecommerce.Service.PrincipalService;
import com.home.ecommerce.Service.ProductService;
import com.home.ecommerce.Service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private ValidationErrorService validationErrorService;
    @Autowired
    private CartService cartService;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private ProductService productService;

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody CartItem cartItem, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.validationErrorService(result);
        if(errorMap!=null) return errorMap;
        User user = principalService.getCurrentPrincipal();
        Cart cart = cartService.findCartByUser(user);
        if(cart==null) cart= new Cart();
        Product product = productService.findProductById(cartItem.getProductId());
        cartItem.setProduct(product);

        if(product==null) throw new ProductNotFoundException("Requested product was not found");
        List<CartItem> cartItems = cart.getCartItem();
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        cart.setUser(user);
        List<Integer> productQuantity = new ArrayList<>();
        productQuantity.add(cartItem.getQuantity());
        Cart savedCart = cartService.saveCart(cart);
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);

    }
}
