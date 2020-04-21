package com.home.ecommerce.Service;

import com.home.ecommerce.Domain.Role;
import com.home.ecommerce.Repositroy.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleByName(String role){
        return roleRepository.findByRole(role);
    }
}
