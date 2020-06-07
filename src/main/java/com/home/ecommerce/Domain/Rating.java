package com.home.ecommerce.Domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Range(min = 0,max = 5,message = "Please ener a value between 0 and 5")
    @NotNull
    private int rating;

    private String review;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)

    private Product product;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
