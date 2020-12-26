package com.shopping.shoppingapplication.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPayload {
    private String username;

    private String password;
}
