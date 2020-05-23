package com.home.ecommerce.Repositroy;

import com.home.ecommerce.Domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {

    Product findById(int id);
}
