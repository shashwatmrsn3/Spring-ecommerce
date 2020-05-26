package com.home.ecommerce.Repositroy;

import com.home.ecommerce.Domain.Cart;
import com.home.ecommerce.Domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface CartReposiroty  extends CrudRepository<Cart,Integer>{

    Cart findByUser(User user);
}
