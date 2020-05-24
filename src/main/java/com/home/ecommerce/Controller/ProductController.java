package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.MyUserDetails;
import com.home.ecommerce.Domain.Product;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Domain.Vendor;
import com.home.ecommerce.Exception.ProductNotFoundException;
import com.home.ecommerce.Exception.UnauthorizedException;
import com.home.ecommerce.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private VendorService vendorService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product, BindingResult result){
        ResponseEntity<?> errorMap = errorService.validationErrorService(result);
        if(errorMap != null) return errorMap;

        User user = principalService.getCurrentPrincipal();
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        Product product = productService.findProductById(id);
        if(product==null) throw new ProductNotFoundException("The given product was not found");
        if(product.getVendor().getVendorAdmin()!=principalService.getCurrentPrincipal()) {
            throw new UnauthorizedException("You are not authorized to preform this operation");
        }

        productService.deleteById(id);
        return new ResponseEntity<>(id,HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        User user = principalService.getCurrentPrincipal();
        Vendor vendor = vendorService.getVendorByUser(user);
        List<Product> products = productService.getAllProductsByVendor(vendor);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
