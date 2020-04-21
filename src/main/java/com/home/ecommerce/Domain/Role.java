package com.home.ecommerce.Domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role;
    @ManyToMany(mappedBy = "roles")
    private List< User > users;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }
}
