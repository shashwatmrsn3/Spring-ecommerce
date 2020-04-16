package com.home.ecommerce.Domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}
