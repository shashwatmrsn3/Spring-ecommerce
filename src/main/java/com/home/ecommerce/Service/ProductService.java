package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.Product;
import com.home.ecommerce.Domain.User;
import com.home.ecommerce.Domain.Vendor;
import com.home.ecommerce.Exception.ProductNotFoundException;
import com.home.ecommerce.Repositroy.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product,User user){
        System.out.println(user.getVendor());
        product.setVendor(user.getVendor());
        return productRepository.save(product);
    }

    public Product findProductById(int id){
        Product product  = productRepository.findById(id);

        return product;
    }

    public void deleteById(int id){
         productRepository.deleteById(id);
    }

    public List<Product> getAllProductsByVendor(Vendor vendor){
        return productRepository.getAllByVendor(vendor);
    }
}
