package com.shopping.shoppingapplication.service.impl;

import com.shopping.shoppingapplication.domain.entity.Role;
import com.shopping.shoppingapplication.repository.RoleRepository;
import com.shopping.shoppingapplication.service.RoleService;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
