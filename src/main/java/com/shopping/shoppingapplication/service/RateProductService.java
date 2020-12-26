package com.shopping.shoppingapplication.service;

import com.shopping.shoppingapplication.domain.dto.ProductCommentDTO;
import com.shopping.shoppingapplication.exception.product.ProductNotFoundException;
import com.shopping.shoppingapplication.exception.user.UserNotFoundException;

public interface RateProductService {
    void rateProduct(ProductCommentDTO productCommentDTO) throws UserNotFoundException, ProductNotFoundException;
}
