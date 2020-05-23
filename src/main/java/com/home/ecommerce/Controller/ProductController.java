package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.MyUserDetails;
import com.home.ecommerce.Domain.Product;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Exception.ProductNotFoundException;
import com.home.ecommerce.Exception.UnauthorizedException;
import com.home.ecommerce.Service.PrincipalService;
import com.home.ecommerce.Service.ProductService;
import com.home.ecommerce.Service.UserService;
import com.home.ecommerce.Service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ValidationErrorService errorService;
    @Autowired
    private PrincipalService principalService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product, BindingResult result){
        ResponseEntity<?> errorMap = errorService.validationErrorService(result);
        if(errorMap != null) return errorMap;
        Object principal = SecurityContextHolder.getContext(). getAuthentication(). getPrincipal();
        String username = ((UserDetails)principal). getUsername();
        System.out.println(username);
        User user = userService.loadUserByUsername(username);
        Product product1 = productService.saveProduct(product,user);
        return  new ResponseEntity<Product>(product1, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product,BindingResult result){
        ResponseEntity<?> errorMap = errorService.validationErrorService(result);
        if(errorMap!=null) return errorMap;
        Product product1 = productService.findProductById(product.getId());
        if(product1==null) throw new ProductNotFoundException("The required product was not found");
        if(product1.getVendor().getVendorAdmin()!=principalService.getCurrentPrincipal()) {
            throw new UnauthorizedException("You are not permitted to preform this operation");
        }
        Product updatedProduct = productService.saveProduct(product,principalService.getCurrentPrincipal());
        return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
    }
}
