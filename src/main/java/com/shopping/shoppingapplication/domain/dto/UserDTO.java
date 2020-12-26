package com.shopping.shoppingapplication.domain.dto;

import com.shopping.shoppingapplication.domain.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private boolean isBlocked;

    private Set<String> roles;
}
