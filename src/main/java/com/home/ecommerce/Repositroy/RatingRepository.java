package com.home.ecommerce.Repositroy;

import com.home.ecommerce.Domain.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface RatingRepository extends CrudRepository<Rating,Integer> {
    Rating findById(int id);
}
