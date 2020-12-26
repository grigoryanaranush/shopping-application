package com.shopping.shoppingapplication.service;

import com.shopping.shoppingapplication.domain.entity.Role;

public interface RoleService {
    Role findByName(String name);
}
