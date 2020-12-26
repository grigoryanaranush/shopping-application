package com.shopping.shoppingapplication.service;

import com.shopping.shoppingapplication.domain.dto.ProductDTO;
import com.shopping.shoppingapplication.domain.filter.FilterProduct;
import com.shopping.shoppingapplication.exception.category.ProductCategoryNotFoundException;
import com.shopping.shoppingapplication.exception.product.ProductIncorrectFieldException;
import com.shopping.shoppingapplication.exception.product.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    Long create(ProductDTO productDTO) throws ProductIncorrectFieldException;

    ProductDTO get(Long id) throws ProductNotFoundException;

    List<ProductDTO> getAll();

    ProductDTO update(Long id, ProductDTO productDTO) throws ProductNotFoundException, ProductCategoryNotFoundException;

    void delete(Long id);

    List<ProductDTO> filter(FilterProduct filterProduct);
}
