package com.home.ecommerce.Controller;

import com.home.ecommerce.Domain.*;
import com.home.ecommerce.Exception.ProductNotFoundException;
import com.home.ecommerce.Service.OrderService;
import com.home.ecommerce.Service.PrincipalService;
import com.home.ecommerce.Service.ProductService;
import com.home.ecommerce.Service.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private ValidationErrorService validationErrorService;
    @Autowired
    private PrincipalService principalService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest, BindingResult result) {

        List<Integer> orderIds,quantities;
        orderIds = orderRequest.getOrderIdList();
        quantities = orderRequest.getQuantityList();

        System.out.println("before error map");
//        ResponseEntity<?> errorMap = validationErrorService.validationErrorService(result);
//        if(result!=null) return errorMap;
        System.out.println("after error map");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Orders order = new Orders();
        float amount = 0.0f;
        List<OrderItem> orderItems = new ArrayList<>();
        for(int i=0;i<orderIds.size();i++){

            Product product = productService.findProductById(orderIds.get(i));
            System.out.println("product name"+product.getName());
            if(product==null) throw new ProductNotFoundException("Requested product/s not found");
            if(product.getStock()==0 || product.getStock()<quantities.get(i)) throw  new ProductNotFoundException("Product/s of insuficient stock");
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantities.get(i));

            orderItems.add(orderItem);
            amount = amount + product.getPrice() * (float)quantities.get(i);
            product.setStock(product.getStock()-quantities.get(i));
            productService.updateStockOnProduct(product);
        }
        order.setOrderItems(orderItems);
        order.setAddress(orderRequest.getAddress());
        order.setDate(date);
        order.setStatus("PROCESSING");
        order.setAmount(amount);
        User user = principalService.getCurrentPrincipal();
        order.setUser(user);
        Orders savedOrder = orderService.placeOrder(order);
        return new ResponseEntity<Orders>(savedOrder,HttpStatus.OK);


    }

}
