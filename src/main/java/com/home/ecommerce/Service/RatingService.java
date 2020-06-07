package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.Rating;
import com.home.ecommerce.Repositroy.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public Rating giveRating(Rating rating){
        return ratingRepository.save(rating);
    }
}
