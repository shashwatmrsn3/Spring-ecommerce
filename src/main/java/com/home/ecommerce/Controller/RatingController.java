package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.*;
import com.home.ecommerce.Exception.ProductNotFoundException;
import com.home.ecommerce.Service.PrincipalService;
import com.home.ecommerce.Service.ProductService;
import com.home.ecommerce.Service.RatingService;
import com.home.ecommerce.Service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
    private ProductService productService;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private ValidationErrorService validationErrorService;
    @Autowired
    private RatingService ratingService;

    @PostMapping("/rate/{pid}")
    public ResponseEntity<?> giveRating(@RequestBody Rating rating, BindingResult result, @PathVariable("pid") int pid){
        boolean isProductReceived=false;
        ResponseEntity<?> errorMap = validationErrorService.validationErrorService(result);
        if(errorMap!=null) return errorMap;
        Product product = productService.findProductById(pid);
        if(product==null) throw new ProductNotFoundException("The requested product was not found");
        try{
            principalService.getCurrentPrincipal();

        }catch(Exception e){
            throw new ProductNotFoundException("You must be logged in to revew");
        }
        User user = principalService.getCurrentPrincipal();
        List<Orders> orders = user.getOrders();
        for(int i=0;i<orders.size();i++){
            List<OrderItem> orderItems = orders.get(i).getOrderItems();

            for(int j=0;j<orderItems.size();j++){
                OrderItem orderItem = orderItems.get(j);
                System.out.println(orders.get(i).getStatus());
                System.out.println("PID="+orderItem.getProduct().getId()+product.getId());
                if(orderItem.getProduct().getId()==product.getId()) {
                    if(orders.get(i).getStatus().equals("COMPLETE"))

                    {
                        isProductReceived = true;
                        System.out.println(orders.get(i).getStatus());
                    }
                }
                System.out.println(isProductReceived);
            }
        }
        if(isProductReceived!=true)  throw new ProductNotFoundException("The user has not ordered the product or the product has not been received yet");
        rating.setProduct(product);
        rating.setUser(user);
        Rating savedRating = ratingService.giveRating(rating);
        return new ResponseEntity<Rating>(savedRating, HttpStatus.OK);

    }
}
