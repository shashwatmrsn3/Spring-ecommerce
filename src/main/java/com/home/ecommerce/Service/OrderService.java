package com.home.ecommerce.Service;


import com.home.ecommerce.Domain.Orders;
import com.home.ecommerce.Repositroy.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Orders placeOrder(Orders order){
        return orderRepository.save(order);
    }
}
