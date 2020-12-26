package com.shopping.shoppingapplication.service;

import com.shopping.shoppingapplication.domain.dto.UserDTO;
import com.shopping.shoppingapplication.domain.entity.User;

public interface UserService {
    void blockUser(Long id);

    void unblockUser(Long id);

    Long save(UserDTO user);
}
