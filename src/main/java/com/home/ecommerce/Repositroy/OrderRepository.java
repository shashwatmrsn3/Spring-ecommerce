package com.home.ecommerce.Repositroy;


import com.home.ecommerce.Domain.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface OrderRepository extends CrudRepository<Orders,Integer>{


}
