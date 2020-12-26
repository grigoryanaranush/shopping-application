package com.shopping.shoppingapplication.exception.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductCategoryIncorrectFieldException extends RuntimeException{
    public ProductCategoryIncorrectFieldException() {
        super("Incorrect value for field");
    }
}
