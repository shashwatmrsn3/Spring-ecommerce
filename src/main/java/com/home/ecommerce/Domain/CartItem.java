package com.home.ecommerce.Domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "please enter a valid product ID")
    private int pId;
    @NotNull(message = "Please enter the required quantity")
    private int quantity;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductId() {
        return pId;
    }

    public void setProductId(int productId) {
        this.pId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }
}
