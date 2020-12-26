package com.shopping.shoppingapplication.service;

import com.shopping.shoppingapplication.domain.dto.ProductCategoryDTO;
import com.shopping.shoppingapplication.exception.category.ProductCategoryNotFoundException;

import java.util.List;

public interface ProductCategoryService {
    Long create(ProductCategoryDTO productCategoryDTO);

    ProductCategoryDTO get(Long id) throws ProductCategoryNotFoundException;

    List<ProductCategoryDTO> getAll();

    void delete(Long id);

    ProductCategoryDTO update(Long id, ProductCategoryDTO productCategoryDTO) throws ProductCategoryNotFoundException;
}
