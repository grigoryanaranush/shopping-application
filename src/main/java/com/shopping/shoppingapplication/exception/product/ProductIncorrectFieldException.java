package com.shopping.shoppingapplication.exception.product;

public class ProductIncorrectFieldException extends RuntimeException
{
    public ProductIncorrectFieldException() {
        super("Incorrect value for field");
    }
}
