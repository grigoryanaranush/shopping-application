package com.shopping.shoppingapplication.exception.category;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductCategoryNotFoundException extends RuntimeException{
    public ProductCategoryNotFoundException() {
        super("Product Category Not found");
    }
}
