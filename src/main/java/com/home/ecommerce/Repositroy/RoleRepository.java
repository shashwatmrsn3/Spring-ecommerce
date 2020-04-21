package com.home.ecommerce.Repositroy;

import com.home.ecommerce.Domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {

    Role findByRole(String role);
}
