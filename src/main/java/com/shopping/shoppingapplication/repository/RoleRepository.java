package com.shopping.shoppingapplication.repository;

import com.shopping.shoppingapplication.domain.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}